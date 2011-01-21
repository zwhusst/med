package com.souyibao.freemarker;

import java.util.ArrayList;
import java.util.Collection;

import com.souyibao.restlet.SearchRestlet;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.util.MedUtil;

public class KeywordDetailsDataModel {
	private Keyword keyword = null;
	private String userQuery = null;
	private Collection<Keyword> querykeywords = null;
	private String topicid = null;
	private String categoryid = null;
	private String explanation = null;
	
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	public String getUserQuery() {
		return userQuery;
	}
	public void setUserQuery(String userQuery) {
		this.userQuery = userQuery;
	}
	public Collection<Keyword> getQuerykeywords() {
		return querykeywords;
	}
	public void setQuerykeywords(Collection<Keyword> querykeywords) {
		this.querykeywords = querykeywords;
	}
	public String getTopicid() {
		return topicid;
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getKeywordAlias() {		
		String alias = MedUtil.joinString(this.keyword.getAliasCollection(), ",");
		if (alias == null) {
			return "";
		}
		
		return "(" + alias + ")";
	}
	
	/**
	 * This url include all parameters for the query
	 * @return
	 */
	public String getSearchAllURL() {
		Collection<String> keywordids = new ArrayList<String>();
		if (querykeywords != null) {
			for (Keyword keyword : querykeywords) {
				keywordids.add(""+keyword.getId());				
			}
		}
		keywordids.add(""+keyword.getId());
		
		return SearchRestlet.getQueryURL(topicid, categoryid, keywordids, userQuery);
	}	
	
	/** 
	 * Only query for the explanation keyword
	 * @return
	 */
	public String getSimpleSearchURL() {
		Collection<String> keywordids = new ArrayList<String>();
		keywordids.add(""+keyword.getId());

		return SearchRestlet.getQueryURL(topicid, categoryid, keywordids, null);
	}
}
