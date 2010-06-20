package com.souyibao.web.model;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;

public class KeywordExplainModel {
	private String userInputTxt = null;
	private Keyword keyword = null;
	private String[] checkedIds = null;
	private String[] tFilters = null;
	private String[] cFilters = null;
	
	public String getUserInputTxt() {
		return userInputTxt;
	}
	public void setUserInputTxt(String userInputTxt) {
		this.userInputTxt = userInputTxt;
	}
	
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	
	public String[] getCheckedIds() {
		return checkedIds;
	}
	public void setCheckedIds(String[] checkedIds) {
		this.checkedIds = checkedIds;
	}
	
	public String[] getTFilters() {
		return tFilters;
	}
	public void setTFilters(String[] filters) {
		tFilters = filters;
	}
	public String[] getCFilters() {
		return cFilters;
	}
	public void setCFilters(String[] filters) {
		cFilters = filters;
	}
	
	public Collection<String> getFullUrlText() {
		Collection<String> result = new ArrayList<String>();
		
		if ((this.userInputTxt != null) && (userInputTxt.length() != 0)) {
			result.add(userInputTxt);			
		}
		
		if (this.checkedIds != null) {
			for (int i = 0; i < checkedIds.length; i++) {
				Keyword keyword = MedEntityManager.getInstance().getKeywordById(checkedIds[i]);
				
				if (keyword != null) {
					result.add(keyword.getName());
				}
			}
		}
		
		if (this.keyword != null) {
			result.add(keyword.getName());
		}
		
		return result;
	}
	
	public String getFullUrl() {
		StringBuffer result = new StringBuffer();// url = "k=" + this.keyword.getId();
		
		// checked keywords
		if (this.checkedIds != null) {
			for (int i = 0; i < checkedIds.length; i++) {
				result.append("&k=" + checkedIds[i]);
			}
		}
		
		// user input
		if ((this.userInputTxt != null) && (userInputTxt.length() != 0)) {
			try {
				result.append("&q=").append(URLEncoder.encode(userInputTxt, "UTF-8"));
			} catch (Exception e) {				
			}
		}
		
		// category filters
		if (this.cFilters != null) {
			for (int i = 0; i < cFilters.length; i++) {
				result.append("&cFilter=").append(cFilters[i]);
			}
		}
		
		// topic filters
		if (this.tFilters != null) {
			for (int i = 0; i < tFilters.length; i++) {
				result.append("&tFilter=").append(tFilters[i]);
			}
		}
		
		// current explanation keywords
		if (this.keyword != null) {
			result.append("&k=" + this.keyword.getId());
		}
		
		
		return result.toString();
	}
	
	public String getFocusUrl() {
		StringBuffer result = new StringBuffer();// url = "k=" + this.keyword.getId();
//	
//		// user input
//		if (userInputTxt != null) {
//			try {
//				result.append("&q=").append(URLEncoder.encode(userInputTxt, "UTF-8"));
//			} catch (Exception e) {				
//			}
//		}
//		
		// category filters
		if (this.cFilters != null) {
			for (int i = 0; i < cFilters.length; i++) {
				result.append("&cFilter=").append(cFilters[i]);
			}
		}
		
		// topic filters
		if (this.tFilters != null) {
			for (int i = 0; i < tFilters.length; i++) {
				result.append("&tFilter=").append(tFilters[i]);
			}
		}
		
		// current explanation keywords
		if (this.keyword != null) {
			result.append("&k=" + this.keyword.getId());
		}
		
		
		return result.toString();
	}
	
	public String getAlias() {
		if (this.keyword == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		
		if (keyword.getAliasCollection() != null) {
			result.append("(");
			for (String alias : keyword.getAliasCollection()) {
				if (result.length() > 1) {
					result.append(",");
				}
				result.append(alias);
			}
			result.append(")");
		}
		
		return result.toString();
	}
	
	public String getExplanation() {
		return MedEntityManager.getInstance().getKeywordExplanation(keyword);
	}
}
