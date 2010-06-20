package com.souyibao.web.model;

import java.util.Collection;

public class SessionData {
	private Collection<String> cFilters = null;
	private Collection<String> tFilters = null;
	private Collection<String> keywords = null;
	private Collection<String> handlers = null;
	
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
	public Collection<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(Collection<String> keywords) {
		this.keywords = keywords;
	}
	public Collection<String> getHandlers() {
		return handlers;
	}
	public void setHandlers(Collection<String> handlers) {
		this.handlers = handlers;
	}
}
