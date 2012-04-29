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
	private String outerSite = null;
	
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
	
	public String getOuterSite() {
		return outerSite;
	}
	public void setOuterSite(String outerSite) {
		this.outerSite = outerSite;
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
		
		return SearchRestlet.getQueryURL(topicid, categoryid, keywordids, userQuery, outerSite);
	}	
	
	/** 
	 * Only query for the explanation keyword
	 * @return
	 */
	public String getSimpleSearchURL() {
		Collection<String> keywordids = new ArrayList<String>();
		keywordids.add(""+keyword.getId());

		return SearchRestlet.getQueryURL(topicid, categoryid, keywordids, null, outerSite);
	}
}
