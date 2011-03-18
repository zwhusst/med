package com.souyibao.web.model;

public class RepresentationMeta {

	private String id = null;
	private String source = null;
	private String mediaType = null;
	private String encoding = null;

	public RepresentationMeta(String mediaType,
			String encoding) {
		this.mediaType = mediaType;
		this.encoding = encoding;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
}
