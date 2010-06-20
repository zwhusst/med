package com.souyibao.web.model;

import com.souyibao.shared.entity.Keyword;

public class KeywordToDocCount {
	private String docId = null;
	
	private Keyword keyword = null;
	
	private int count = 0;

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	
}