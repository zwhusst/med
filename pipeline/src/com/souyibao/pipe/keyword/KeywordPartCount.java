package com.souyibao.pipe.keyword;

public class KeywordPartCount {
	private String part = null;
	private short count = 0;
	private float idf = 0.0f;
	
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public short getCount() {
		return count;
	}
	public void setCount(short count) {
		this.count = count;
	}
	public float getIdf() {
		return idf;
	}
	public void setIdf(float idf) {
		this.idf = idf;
	}
	
	
}
