package com.souyibao.search.ctrl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.souyibao.search.SearchResult;
import com.souyibao.search.TopicResult;
import com.souyibao.search.util.SearchUtil;
import com.souyibao.shared.MedProperties;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
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
			Set<Keyword> keywords, Set<Topic> topicFilters,
			Map<Topic, TopicCategory> categoryFilters)  {

		// find the search result
		GeneralSuggester suggester = new GeneralSuggester();
		SearchResult result = null;
		try {
			result = suggester.getPossibleDoc(queryStr, keywords,
					topicFilters, categoryFilters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (result == null) {
			return null;
		}
		

		int maxDisplayNum = 0;
		if ((topicFilters == null)|| (topicFilters.size() == 0)) {
			maxDisplayNum = MedProperties.getInstance().getRecordNum4All(
					MedProperties.DEF_MAX_RECORD_NUM_4_ALL);
		} else {		
			// should only one topic as the filter parameter
			maxDisplayNum = MedProperties.getInstance().getRecordNumPerTopic(
					MedProperties.DEF_MAX_RECRD_NUM_PER_TOPIC);
		}

		Map<Topic, TopicResult> topicToKWs = result.getTopicResults();
		if (topicToKWs == null) {
			return result;
		}
		
		for (Topic topic : categoryFilters.keySet()) {
			TopicResult tResult = topicToKWs.get(topic);
			if ((tResult == null) || (tResult.getData().isEmpty())) {
				continue;
			}

			TopicCategory category = categoryFilters.get(topic);
			List<IDataProvider> filterKws = SearchUtil.filterKWWithCategory(
					tResult.getData(), category);
			tResult.setData(filterKws);
			tResult.setCategory(category);
		}

		truncateResult(result, maxDisplayNum);
		
		return result;
	}
	
	public static void truncateResult(SearchResult result, int maxDisplayNum) {
		for (TopicResult tResult : result.getTopicResults().values()) {
			if (tResult == null) continue;
			int size = tResult.getData().size(); 
			
			if ( size > maxDisplayNum) {
				tResult.setExeceedMaxResult(true);
				tResult.getData().subList(maxDisplayNum, size).clear();
			}
		}
	}
}