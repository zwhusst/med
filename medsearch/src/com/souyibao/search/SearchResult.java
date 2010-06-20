package com.souyibao.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.util.MedUtil;

public class SearchResult {
	private Map<Topic, TopicResult> m_searchResult = new HashMap<Topic, TopicResult>();
	private Set<Keyword> m_queryKeywords = null;
	// query keyword ids, will be outpt to search pane
	// these ids are got with request.getParameterValues("k");
	private String[] paneKeywordIds = null;
	private List<Topic> m_avaliableTopics = null;
	private Set<Topic> m_topicFilters = null;
	
	// topic id to category/area id
	private Map<String, String> m_categoryFilters = new HashMap<String, String>();

	public SearchResult() {
	}

	public Map<Topic, TopicResult> getData() {
		return m_searchResult;
	}
	
	public TopicResult getDataByTopic(Topic topic) {
		return this.m_searchResult.get(topic);
	}

	public void setSearchResult(Map<Topic,TopicResult> result) {
		this.m_searchResult = result;
	}
	
	public void addSearchResult(Topic topic, TopicResult topicResult) {
		topicResult.setTopic(topic);
		m_searchResult.put(topic, topicResult);
	}

	public List<Topic> getAvaliableTopics() {
		return m_avaliableTopics;
	}

	public void setAvaliableTopics(Set<Topic> avaliableTopics) {
		List sortTopics = new ArrayList<Topic>();
		sortTopics.addAll(avaliableTopics);
		MedUtil.sortTopics(sortTopics);
		
		this.m_avaliableTopics = sortTopics;
	}

	public void addAvaliableTopic(Topic topic) {
		if (m_avaliableTopics == null) {
			m_avaliableTopics = new ArrayList<Topic>();
		}

		m_avaliableTopics.add(topic);
	}

	public Set<Topic> getTopicFilters() {
		return m_topicFilters;
	}

	public void setTopicFilters(Set<Topic> topicFilters) {
		this.m_topicFilters = topicFilters;
	}

	public Set<Keyword> getQueryKeywords() {
		return m_queryKeywords;
	}

	public void setQueryKeywords(Set<Keyword> queryKeywords) {
		this.m_queryKeywords = queryKeywords;
	}

	public Map<String, String> getCategoryFilters() {
		return m_categoryFilters;
	}

	public void setCategoryFilters(Map<String, String> filters) {
		m_categoryFilters = filters;
	}

	public Set<Keyword> getPaneKeywords() {
		Set<Keyword> result = new HashSet<Keyword>();
		if (paneKeywordIds != null) {
			for (int i = 0; i < paneKeywordIds.length; i++) {
				String id = paneKeywordIds[i];
				Keyword keyword = MedEntityManager.getInstance()
						.getKeywordById(id);
				if (keyword != null) {
					result.add(keyword);
				}
			}
		}

		return result;
	}

	public void setPaneKeywordIds(String[] queryIds) {
		this.paneKeywordIds = queryIds;
	}
	
	public String getTopData() {
		StringBuffer result = new StringBuffer();
		if (m_queryKeywords != null) {			
			for (Keyword keyword : m_queryKeywords) {
				if ((keyword.getTopic().getId() == 1) || (keyword.getTopic().getId() == 4)) {
					if (result.length() == 0) {
						result.append(keyword.getId());
					} else {
						result.append("|" + keyword.getId());
					}
				}
			}
		}
		
		return result.toString();
	}
}
