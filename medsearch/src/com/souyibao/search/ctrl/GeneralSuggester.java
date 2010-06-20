package com.souyibao.search.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;

import com.souyibao.search.SearchResult;
import com.souyibao.search.TopicResult;
import com.souyibao.search.module.ModuleFactory;
import com.souyibao.search.searcher.MedSearcher;
import com.souyibao.search.util.SearchUtil;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.viewer.IDataProvider;
import com.souyibao.shared.viewer.KeywordDataProvider;

public class GeneralSuggester implements ISuggester {

	public static final String[] ALL_MODULES = new String[0];

	public static int MAX_UI_RECORDS_PER_PAGE = 5;
	
	public static int MAX_UI_RECORDS_PER_TOPIC = 50;
	
	/**
	 * 
	 */
	public SearchResult getPossibleDoc(String queryStr, Set<Keyword> keywords,
			String[] modules, Set<Topic> topicsFilter, Map<String, String> categoryFilter) {
		// 1: Query the input data with lucene
		Map<String, List<Document>> hits= MedSearcher.search(queryStr, topicsFilter, keywords);
		if ((hits == null) || (hits.size() == 0)) {
			return null;
		}
		
		List<Document> documentHits = hits.get(ModuleFactory.DOCUMENT_MODULE);
		
		// 2: Get all the hit keyword and weight by the search result.
		List<KeywordDataProvider> keywordsProvider = null;
		try {
			keywordsProvider = SearchUtil.retriveKeywordDataProvider(documentHits);
		} catch (CorruptIndexException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Set<Topic> avaliableTopics = new HashSet<Topic>();
		
		// 3: Calculate the weight by the keyword. (Apply the topic filter).
		Collection<KeywordDataProvider> calKeywordWeight = SearchUtil
				.calKeywordWeight(keywordsProvider, topicsFilter, avaliableTopics);
//		if (calKeywordWeight.isEmpty()) {			
//			if ((topicsFilter != null) && (topicsFilter.size() > 0)) {
//				// if the search result is empty when the topic filter is applied.
//				// Abandon the topic filter
//				// switch to show all the data.
//				calKeywordWeight = SearchUtil.calKeywordWeight(
//						docKeywordWeights, null, null);
//				result.setTopicFilters(null);
//			}
//		}
		
		// 4: Group the keywords by the topic. 
		Set<Keyword> qKeywords = SearchUtil.getKeywordByQryString(queryStr);
		Map<Topic, List<KeywordDataProvider>> groupedKWs = 
				SearchUtil.groupKeywordByTopic(calKeywordWeight, qKeywords);
				
		SearchResult result = new SearchResult();
		
		result.setTopicFilters(topicsFilter);
		result.setAvaliableTopics(avaliableTopics);
		
		// put all the result
		if (groupedKWs != null) {
			for (Topic topic : groupedKWs.keySet()) {
				List<IDataProvider> provider = new ArrayList<IDataProvider>();
				provider.addAll(groupedKWs.get(topic));
				
				result.addSearchResult(topic, new TopicResult(provider));
			}
		}
		
		return result;
	}
}
