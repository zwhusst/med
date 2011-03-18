package com.souyibao.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.souyibao.web.model.RepresentationMeta;
import com.souyibao.web.model.SimpleStringRepresentation;

public class CacheManager {
	private static Logger logger = Logger.getLogger(CacheManager.class);

	// file name for index
	private static String CACHE_IDX_FILE = "_index";
	
	// application idx --> cache index, in here, it is the url to uuid(url)
	private Map<String, RepresentationMeta> _cacheIndexes = 
		new ConcurrentHashMap<String, RepresentationMeta>();
		
	private File _cacheFolder = null;
	private File _idxFile = null;
	
	// it will never abort any task because of the unbound queue
	// (actually the max queue number is Interger max value)
	private ExecutorService _writeExecutor = null;
	
	private ExecutorService _readExecutor = null;
	
	// executor to write meta data to index file
	private ExecutorService _indexExecutor = null;
	
	public CacheManager(File cacheFolder) throws IOException {
		this._cacheFolder = cacheFolder;
		this._idxFile = new File(_cacheFolder, CACHE_IDX_FILE);
		
		readIndex(_idxFile);
		
		// create the write executor
		this._writeExecutor = new ThreadPoolExecutor(3, 4, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());
		this._readExecutor = Executors.newCachedThreadPool();
		this._indexExecutor = Executors.newFixedThreadPool(1);
		
	}
	
	public CacheManager(String cacheFolder) throws IOException {
		this(new File(cacheFolder));
	}

	public void readIndex(File idxFile) throws IOException {
		if (idxFile.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(idxFile));
			String lineTxt = reader.readLine();
			while (lineTxt != null) {
				String[] data = lineTxt.split("\t");
				//source, file name, media type, encoding
				RepresentationMeta meta = new RepresentationMeta(data[2], data[3]);
				meta.setSource(data[0]);
				meta.setId(data[1]);
				_cacheIndexes.put(data[0], meta);
				
				lineTxt = reader.readLine();
			}
			
			reader.close();
		} else {
			idxFile.createNewFile();
		}
	}
	
	public void cacheData(String source, SimpleStringRepresentation representation) {
		// put the content to file with the filename
		_writeExecutor.submit(new WriteTask(this._cacheFolder,
				this._cacheIndexes, source, representation, this._idxFile,
				this._indexExecutor));
	}
	
	public SimpleStringRepresentation readData(String source) {
		RepresentationMeta meta = this._cacheIndexes.get(source);
		if (meta == null) {
			return null;
		}
		
		File file = new File(this._cacheFolder, meta.getId());
		if (!file.exists()) {
			// it looks this file is removed accidently.
			return null;
		}
		
		Future<String> result = _readExecutor
				.submit(new ReadTask(this._cacheFolder, meta));
		
		try {
			SimpleStringRepresentation representation = new SimpleStringRepresentation(
					meta, result.get());
			return representation;
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}
	
	public void shutdown() {
		this._indexExecutor.shutdown();
		this._readExecutor.shutdown();
		this._writeExecutor.shutdown();
	}
	
	/**
	 * write meta data to index file
	 *
	 */
	static class WriteIndexTask implements Runnable{
		private File idxFile = null;
		private RepresentationMeta meta = null;
		public WriteIndexTask(File idxFile, RepresentationMeta meta) {
			this.idxFile = idxFile;
			this.meta = meta;
		}
		
		@Override
		public void run() {
			StringBuffer metaContent = new StringBuffer();
			//source, file name, media type, encoding
			metaContent.append(meta.getSource()).append("\t");
			metaContent.append(meta.getId()).append("\t");
			metaContent.append(meta.getMediaType()).append("\t");
			metaContent.append(meta.getEncoding()).append("\n");
			
			try {
				FileOutputStream fs = new FileOutputStream(this.idxFile, true);
				fs.write(metaContent.toString().getBytes("UTF-8"));
				fs.flush();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}		
	}

	
	/**
	 * Read the content according the content id.
	 *
	 */
	static class ReadTask implements Callable<String>{
		private File folder = null;
		private RepresentationMeta meta = null;
		
		public ReadTask(File folder, RepresentationMeta meta) {
			this.folder = folder;
			this.meta = meta;
		}
		
		@Override
		public String call() throws Exception {
			InputStream is = null;
			synchronized (meta.getId()) {
				is = new FileInputStream(new File(folder, meta.getId()));
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));

			String line;
			StringWriter writer = new StringWriter();

			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.write("\n");
			}
			reader.close();

			return writer.toString();
		}		
	}
	
	/**
	 *  write the content to file and return the meta data for this content
	 * 
	 */
	static class WriteTask implements Callable<RepresentationMeta> {
		private File folder = null;
		private Map<String, RepresentationMeta> metas = null;
		private String source = null;
		private SimpleStringRepresentation representation = null;
		private File idxFile = null;
		private ExecutorService idxExecutor = null;
		
		public WriteTask(File folder,
				Map<String, RepresentationMeta> metas, String source,
				SimpleStringRepresentation representation, 
				File idxFile, ExecutorService idxExecutor) {
			this.folder = folder;
			this.source = source;
			this.metas = metas;
			this.representation = representation;
			this.idxFile = idxFile;
			this.idxExecutor = idxExecutor;
		}

		@Override
		public RepresentationMeta call() throws Exception {
			String fileName = UUID.randomUUID().toString();
			try {
				synchronized (fileName) {
					if (metas.get(source) != null) {
						return null;
					}

					representation.getMeta().setSource(source);
					representation.getMeta().setId(fileName);
					metas.put(source, representation.getMeta());

					String enconding = representation.getMeta().getEncoding();
					Writer writer = new OutputStreamWriter(
							new FileOutputStream(new File(folder, fileName)),
							enconding);
					writer.write(representation.getContent());
					writer.close();
				}
				
				// output the meta to index file
				//source, file name, media type, encoding
				idxExecutor.execute(new WriteIndexTask(this.idxFile, representation.getMeta()));

			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}
			
			return representation.getMeta();
		}
	}

}
