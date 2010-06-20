package com.souyibao.search.ctrl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.souyibao.search.SearchResult;
import com.souyibao.search.TopicResult;
import com.souyibao.search.util.SearchUtil;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.viewer.IDataProvider;

public class Controller {

	/**
	 * search by the query string and the categoryFilter
	 * 
	 * @param queryStr
	 * @param categoryFilter
	 * @return
	 */
	public static SearchResult getSearchResult(String queryStr,
			String[] keywordIds, String[] topicIds,
			Map<String, String> categoryFilters) {
		// for the input parameter
		Set<Keyword> keywords = MedEntityManager.getInstance().getKeysWithIds(
				keywordIds);
		Set<Topic> topicFilters = MedEntityManager.getInstance()
				.getTopicsWithIds(topicIds);

		// find the search result
		ISuggester suggester = new GeneralSuggester();
		SearchResult result = suggester.getPossibleDoc(queryStr, keywords,
				GeneralSuggester.ALL_MODULES, topicFilters, categoryFilters);

		// keywords from the query data.
		Set<Keyword> queryKeywords = SearchUtil.getKeywordByQryData(queryStr,
				keywords);
		result.setQueryKeywords(queryKeywords);
		result.setPaneKeywordIds(keywordIds);

		int maxDisplayNum = 0;
		if ((topicIds == null)|| (topicIds.length == 0)) {
			maxDisplayNum = GeneralSuggester.MAX_UI_RECORDS_PER_PAGE + 1;		
		} else {		
			// should only one topic as the filter parameter
			maxDisplayNum = GeneralSuggester.MAX_UI_RECORDS_PER_TOPIC;
		}
		
		// update the category filter to search result
		result.setCategoryFilters(categoryFilters);

		Map<Topic, TopicResult> topicToKWs = result.getData();
		for (String topicId : categoryFilters.keySet()) {
			Topic topic = MedEntityManager.getInstance().getTopicById(topicId);
			TopicResult tResult = topicToKWs.get(topic);
			if ((tResult == null) || (tResult.getResult().isEmpty())) {
				continue;
			}

			String categoryId = categoryFilters.get(topicId);
			List<IDataProvider> filterKws = SearchUtil.filterKWWithCategory(
					tResult.getResult(), categoryId);
			tResult.setResult(filterKws);
			tResult.setFilterId(categoryId);
		}

		truncateResult(result, maxDisplayNum + 1);
		
		return result;
	}
	
	public static void truncateResult(SearchResult result, int maxDisplayNum) {
		for (Topic topic : result.getAvaliableTopics()) {
			TopicResult tResult = result.getDataByTopic(topic);
			if (tResult == null) continue;
			int size = tResult.getResult().size(); 
			
			if ( size > maxDisplayNum) {
				tResult.setExeceedMaxResult(true);
				tResult.getResult().subList(maxDisplayNum - 1 , size).clear();
			}
		}
	}
}