package com.souyibao.web.model;

import java.util.ArrayList;
import java.util.Collection;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;

public class ExSessionData {
	private Collection<String> queryStrings = null;
	private Collection<String> dInputParas = null;
	private Collection<String> cFilters = null;
	private Collection<String> tFilters = null;
	private Collection<String> keywordParas = null;
	private String googleSearchData = null;
	private String displaySearchData = null;
	
	public Collection<String> getQueryStrings() {
		return queryStrings;
	}
	public void setQueryStrings(Collection<String> queryStrings) {
		this.queryStrings = queryStrings;
	}
	public Collection<String> getDInputParas() {
		return dInputParas;
	}
	public void setDInputParas(Collection<String> inputParas) {
		dInputParas = inputParas;
	}
	public Collection<String> getCFilters() {
		return cFilters;
	}
	public void setCFilters(Collection<String> filters) {
		cFilters = filters;
	}
	public Collection<String> getTFilters() {
		return tFilters;
	}
	public void setTFilters(Collection<String> filters) {
		tFilters = filters;
	}
	public Collection<String> getKeywordParas() {
		return keywordParas;
	}
	public void setKeywordParas(Collection<String> keywordParas) {
		this.keywordParas = keywordParas;
	}
	public Collection<Keyword> getKeywords() {
		Collection<Keyword> result = new ArrayList<Keyword>();
		if (this.keywordParas != null) {
			for (String id : this.keywordParas) {
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
				if (keyword != null) {
					result.add(keyword);
				}
			}
		}
		
		return result;
	}
	
	public String getGoogleSearchData() {
		StringBuffer strBuf = new StringBuffer();
		if (queryStrings != null) {
			for (String s : queryStrings) {
				strBuf.append(s).append(" ");
			}
		}
		
		if (this.keywordParas != null) {
			for (String id : this.keywordParas) {
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
				if (keyword != null) {
					strBuf.append(keyword.getName()).append(" ");
				}
			}
		}
		return strBuf.toString();
	}
	public String getDisplaySearchData() {
		StringBuffer strBuf = new StringBuffer();
		if (queryStrings != null) {
			for (String s : queryStrings) {
				strBuf.append(s).append("&nbsp;");
			}
		}
		
		if (this.keywordParas != null) {
			for (String id : this.keywordParas) {
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(id);
				if (keyword != null) {
					strBuf.append(keyword.getName()).append("&nbsp;");
				}
			}
		}
		return strBuf.toString();
	}
}
