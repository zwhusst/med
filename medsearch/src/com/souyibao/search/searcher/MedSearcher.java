package com.souyibao.search.searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.souyibao.log.MedLogUtil;
import com.souyibao.search.module.Module;
import com.souyibao.search.module.ModuleFactory;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;

public class MedSearcher {

	private static final Log LOG = LogFactory
			.getLog("com.souyibao.search.searcher.MedSearcher");

	public static Query getQuery(String module, String queryString, Set<Keyword> keywords) {
		BooleanQuery query = new BooleanQuery();
		new BasicQueryFilter().filter(module, queryString, keywords, query);

		return query;
	}
	
	private static Set<Module> getAvaliableModule(Set<Topic> topics) {
		Set<Module> result = new HashSet<Module>();
		Collection<Module> modules = ModuleFactory.getInstance().getModules();
		if (topics == null) {
			result.addAll(modules);
			return result;
		}
		
		for (Module module : modules) {
			for (Topic topic : topics) {
				if (module.avaliableForTopic(""+topic.getId())) {
					result.add(module);
					continue;
				}
			}
			
		}
		
		return result;
	}

	public static Map<String, List<Document>> search(String queryString,
			Set<Topic> topicsFilter, Set<Keyword> keywords) {
		Set<Module> modules = getAvaliableModule(topicsFilter);

		Map<String, List<Document>> result = new HashMap<String, List<Document>>();

		for (Module module : modules) {
			IndexSearcher searcher = ModuleFactory.getInstance().getSearcher(
					module.getName());

			Query query = getQuery(module.getName(), queryString, keywords);
			MedLogUtil.logInfo("query: " + query);
			try {
				result.put(module.getName(), getDocuments(searcher
						.search(query)));
			} catch (IOException e) {
				LOG.fatal("Query " + queryString + " for module : " + module);
				throw new RuntimeException("Query " + queryString
						+ " for module : " + module);
			}

		}
		return result;
	}
	
	private static List<Document> getDocuments(Hits hits) throws IOException {
		List<Document> docList = new ArrayList<Document>();
		if (hits == null) {
			return docList;
		}
		for (int i = 0; i < hits.length(); i++) {
			Document doc = hits.doc(i);
			docList.add(doc);
		}
		
		return docList;
	}
	
	private static List<Document> getDocuments(IndexSearcher searcher,
			TopDocs topDoc) throws IOException {
		List<Document> docList = new ArrayList<Document>();
		if (topDoc == null) {
			return docList;
		}

		ScoreDoc[] docs = topDoc.scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			Document doc = searcher.doc(docs[i].doc);
			docList.add(doc);
		}

		return docList;
	}
}
