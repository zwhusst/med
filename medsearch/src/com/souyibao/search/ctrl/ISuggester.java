package com.souyibao.search.ctrl;

import java.util.Map;
import java.util.Set;

import com.souyibao.search.SearchResult;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.Topic;

public interface ISuggester {
	public SearchResult getPossibleDoc(String queryStr, Set<Keyword> keywords,
			String[] modules, Set<Topic> topicFilter,Map<String, String> categoryFilter);
}
