/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
package com.souyibao.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;

public class SearchResult {
	private Map<Topic, TopicResult> topicResults = new HashMap<Topic, TopicResult>();
	
	// The keywords from user input query.
	private Collection<Keyword> keyworsFromQuery = null;

	public Map<Topic, TopicResult> getTopicResults() {
		return topicResults;
	}
	
	public TopicResult getTopicResultByTopic(Topic topic) {
		return this.topicResults.get(topic);
	}

	public void setTopicResults(Map<Topic,TopicResult> result) {
		this.topicResults = result;
	}
	
	public void addSearchResult(Topic topic, TopicResult topicResult) {
		topicResult.setTopic(topic);
		topicResults.put(topic, topicResult);
	}

	public Collection<Keyword> getKeyworsFromQuery() {
		return keyworsFromQuery;
	}

	public void setKeyworsFromQuery(Collection<Keyword> keyworsFromQuery) {
		this.keyworsFromQuery = keyworsFromQuery;
	}

	
	
//	public void setAvaliableTopics(Set<Topic> avaliableTopics) {
//		List sortTopics = new ArrayList<Topic>();
//		sortTopics.addAll(avaliableTopics);
//		MedUtil.sortTopics(sortTopics);
//		
//		this.m_avaliableTopics = sortTopics;
//	}
//
//	public void addAvaliableTopic(Topic topic) {
//		if (m_avaliableTopics == null) {
//			m_avaliableTopics = new ArrayList<Topic>();
//		}
//
//		m_avaliableTopics.add(topic);
//	}
//
//	public Set<Keyword> getPaneKeywords() {
//		Set<Keyword> result = new HashSet<Keyword>();
//		if (paneKeywordIds != null) {
//			for (int i = 0; i < paneKeywordIds.length; i++) {
//				String id = paneKeywordIds[i];
//				Keyword keyword = MedEntityManager.getInstance()
//						.getKeywordById(id);
//				if (keyword != null) {
//					result.add(keyword);
//				}
//			}
//		}
//
//		return result;
//	}
//
//	public void setPaneKeywordIds(String[] queryIds) {
//		this.paneKeywordIds = queryIds;
//	}
//	
//	public String getTopData() {
//		StringBuffer result = new StringBuffer();
//		if (m_queryKeywords != null) {			
//			for (Keyword keyword : m_queryKeywords) {
//				if ((keyword.getTopic().getId() == 1) || (keyword.getTopic().getId() == 4)) {
//					if (result.length() == 0) {
//						result.append(keyword.getId());
//					} else {
//						result.append("|" + keyword.getId());
//					}
//				}
//			}
//		}
//		
//		return result.toString();
//	}
}
