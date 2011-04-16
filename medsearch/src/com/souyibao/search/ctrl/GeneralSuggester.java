package com.souyibao.search.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.index.CorruptIndexException;

import com.souyibao.search.SearchResult;
import com.souyibao.search.TopicResult;
import com.souyibao.search.searcher.SearchManagerFactory;
import com.souyibao.search.util.SearchUtil;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.shared.viewer.IDataProvider;
import com.souyibao.shared.viewer.KeywordDataProvider;

public class GeneralSuggester{

	public static final String _INDEX_PATH = System.getProperties().getProperty("document_module_idx");
	
	/**
	 * @throws Exception 
	 * 
	 */
	public SearchResult getPossibleDoc(String queryStr, Set<Keyword> queryKeywords,
			Set<Topic> topicsFilter, Map<Topic, TopicCategory> categoryFilter)
			throws Exception {
		// 1: Query the input data with lucene		
		Map<String, Float> scoreDocValues = SearchManagerFactory.search(_INDEX_PATH, queryStr, queryKeywords);
		if ((scoreDocValues == null) || (scoreDocValues.size() == 0)) {
			return null;
		}
				
		// 2: Get all the hit keyword and weight by the search result.
		List<KeywordDataProvider> keywordsProvider = null;
		try {
			keywordsProvider = SearchUtil.retriveKeywordDataProvider(scoreDocValues);
		} catch (CorruptIndexException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 3: Calculate the weight by the keyword. (Apply the topic filter).
		Collection<KeywordDataProvider> calKeywordWeight = SearchUtil
				.calKeywordWeight(keywordsProvider, topicsFilter);
		
		SearchResult result = new SearchResult();
				 
		Set<Keyword> qKeywords = SearchUtil.getKeywordByQryString(queryStr);
		result.setKeyworsFromQuery(qKeywords);
		
		Set<Keyword> topKeywords = new HashSet<Keyword>();
		if (queryKeywords != null) {
			topKeywords.addAll(queryKeywords);
		}
		// 4: Group the keywords by the topic.
		Map<Topic, List<KeywordDataProvider>> groupedKWs = 
				SearchUtil.groupKeywordByTopic(calKeywordWeight, topKeywords);
						
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
