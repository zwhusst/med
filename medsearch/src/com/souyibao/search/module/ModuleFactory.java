package com.souyibao.search.module;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.souyibao.search.util.PropertyPlaceholderConfigurer;

public class ModuleFactory {

	// document module
	public static final String DOCUMENT_MODULE = "document";
	
	private static final Log LOG = LogFactory
			.getLog("com.med.module.ModuleFactory");
	
	private static ModuleFactory _instance = new ModuleFactory();
	private Map<String, Module> modules = new HashMap<String, Module>();
	private Map<String, IndexSearcher> searchers = new HashMap<String, IndexSearcher>();

	private ModuleFactory() {
		loadConfiguration();

		// create all the searcher for all the module		
		initializeSearcher();
	}

	public static ModuleFactory getInstance() {
		return _instance;
	}

	public Module getModule(String moduleName) {
		return modules.get(moduleName);
	}
	
	public Collection<Module> getModules() {
		return modules.values();
	}
	
	public ModuleField getModuleField(String moduleName, String fieldName) {
		Module module = getModule(moduleName);

		if (module != null) {
			return module.getField(fieldName);
		}

		return null;
	}

	private void initializeSearcher() {
		String key = null;

		Set<String> keys = modules.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			key = iterator.next();
			Module module = modules.get(key);
			IndexSearcher searcher = createSearcher(module);
			searchers.put(key, searcher);
		}
	}

	private IndexSearcher createSearcher(Module module) {
		Collection<String> indexs = module.getIndexs();
		if (indexs.isEmpty()) {
			return null;
		}

		int i = 0;
		IndexReader[] readers = new IndexReader[indexs.size()];

		try {
			for (Iterator<String> iterator = indexs.iterator(); iterator
					.hasNext();) {
				String path = iterator.next();
				readers[i++] = IndexReader.open(getRAMDirectory(path));
			}

			return new IndexSearcher(new MultiReader(readers));
		} catch (CorruptIndexException e) {
			LOG.fatal("failed to reader index : " + e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOG.fatal("failed to reader index : " + e);
			throw new RuntimeException(e);
		}
	}
	

	public IndexSearcher getSearcher(String module) {
		IndexSearcher searcher = searchers.get(module);
		
		if (searcher == null) {
			LOG.warn(" can't retrive the searcher for : " + module);
		}

		return searcher;
	}

	private Directory getRAMDirectory(String path) {
		try {
			Directory directory = new RAMDirectory(
					PropertyPlaceholderConfigurer.parseValue(System
							.getProperties(), path));
			return directory;
		} catch (IOException e) {
			LOG.fatal("error to open index : " + path);
			throw new RuntimeException(e);
		}
	}

	public void closeAllSearchers() {
		Collection<IndexSearcher> values = searchers.values();
		for (Iterator<IndexSearcher> iterator = values.iterator(); iterator
				.hasNext();) {
			IndexSearcher searcher = iterator.next();
			IndexReader reader = searcher.getIndexReader();
			Directory directory = reader.directory();

			// close all these objects
			try {
				searcher.close();
				reader.close();
				directory.close();
			} catch (IOException e) {
				LOG.fatal("error to close searcher : " + e);
				continue;
			}
		}

		this.searchers.clear();
	}

	private void loadConfiguration() {
		URL resourceURL = ModuleFactory.class.getClassLoader().getResource(
				"module-conf.xml");

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(resourceURL.toString());

			if (doc == null) {
				throw new RuntimeException(resourceURL.toString()
						+ " not found");
			}

			Element root = doc.getDocumentElement();
			if (!"modules".equals(root.getTagName()))
				LOG.fatal("bad conf file: top-level element not <modules>");

			NodeList moduleNodeList = root.getChildNodes();
			for (int i = 0; i < moduleNodeList.getLength(); i++) {
				Node moduleNode = moduleNodeList.item(i);

				if (!(moduleNode instanceof Element))
					continue;

				Element moduleEle = (Element) moduleNode;
				if (!"module".equals(moduleEle.getTagName()))
					LOG.fatal("bad conf file: element not <module>");

				// get the module name
				String moduleName = moduleEle.getAttribute("name");
				Module module = new Module(moduleName, moduleEle);
				modules.put(moduleName, module);
			}
		} catch (Exception e) {
			LOG.fatal("error parsing conf file: " + e);
			throw new RuntimeException(e);
		}
	}
}
