package com.souyibao.shared;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.viewer.KeywordDataProvider;

public class DocToKeywordManager {
	private static DocToKeywordManager instance = new DocToKeywordManager();
	private Map<String, List<KeywordDataProvider>> doc2Keywords = new HashMap<String, List<KeywordDataProvider>>();
	
	// This variable just used for analysis
	// keywordid <--> idf count
	private Map<Keyword, Integer> IDFMap = null;
	
	private DocToKeywordManager() {
		loadData();
	}

	public static DocToKeywordManager getInstance() {
		return instance;
	}
	
	private void loadData() {
		URL resourceURL = DocToKeywordManager.class.getClassLoader().getResource(
				"doc2keyword");
		
		DataInputStream inStream = null;
		try {
			inStream = new DataInputStream(resourceURL.openStream());
		} catch (IOException e) {
			throw new RuntimeException("Can't load the file with path : " + resourceURL.toString());
		}
		
		String data = null;
		try {
			while((data = inStream.readUTF()) != null) {
				if (data == null) {
					continue;					
				}
				
				int i = data.indexOf("\0");
				String docId = data.substring(0, i).trim();				
				KeywordDataProvider doc2Key = parstDataLine(data);
				if (doc2Key == null) {
					continue;
				}
				List<KeywordDataProvider> docs = doc2Keywords.get(docId);

				if (docs == null) {
					docs = new ArrayList<KeywordDataProvider>();
					doc2Keywords.put(docId, docs);
				}
				docs.add(doc2Key);
			}
		} catch (EOFException e) {
			System.out.println("reach to the end of the file");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
			}
		}
	}
	
	public List<KeywordDataProvider> getDocKeywords(String docId) {
		return doc2Keywords.get(docId);
	}

	

//	Format is : {docId}\0{keywordId}\0{weight}
	private KeywordDataProvider parstDataLine(String line) {
		if (line == null) {
			return null;
		}
		
		KeywordDataProvider doc2Key = new KeywordDataProvider();
		int i = line.indexOf("\0");
//		doc2Key.setDocId(line.substring(0, i));
		
		int j = line.indexOf("\0", i + 1);
		float value = Float.parseFloat(line.substring(j + 1).trim());
		
		String id = line.substring(i + 1, j).trim();
		Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
		if (keyword == null) {
			return null;
		}
		
		doc2Key.setKeyword(keyword);
		doc2Key.setWeight(value);
//		doc2Key.setNotice("1".equals(notice));
		
		return doc2Key;
	}
	
	public Map<Keyword, Integer> retrieveIDFMap() {
		if (IDFMap != null) {
			return IDFMap;
		}
		
		// still hasn't been initialize
		if (doc2Keywords.isEmpty()) {
			return null;
		}
		
		IDFMap = new HashMap<Keyword, Integer> ();
		Set<String> keys = doc2Keywords.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String docId = iterator.next();

			List<KeywordDataProvider> weights = doc2Keywords.get(docId);
			for (Iterator<KeywordDataProvider> weightIte = weights.iterator(); weightIte
					.hasNext();) {
				KeywordDataProvider weight = weightIte.next();

				Integer count = IDFMap.get(weight.getKeyword());
				if (count == null) {
					IDFMap.put(weight.getKeyword(), 1);
				} else {
					IDFMap.put(weight.getKeyword(), count + 1);
				}
			}
		}
		
		return IDFMap;
	}
	
	public static void main(String[] args) throws Exception {
		DocToKeywordManager aa = DocToKeywordManager.getInstance();
		Map map = aa.doc2Keywords;
		System.out.println(map.size());
	}
}

