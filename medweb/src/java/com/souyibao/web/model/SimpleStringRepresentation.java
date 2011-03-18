package com.souyibao.web.model;

public class SimpleStringRepresentation {
	private String content = null;
	private RepresentationMeta meta = null;
	
	public SimpleStringRepresentation(String content) {
		this.content = content;
	}
	
	public SimpleStringRepresentation(RepresentationMeta meta, String content) {
		this.meta = meta;
		this.content = content;
	}

	public RepresentationMeta getMeta() {
		return meta;
	}

	public void setMeta(RepresentationMeta meta) {
		this.meta = meta;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
