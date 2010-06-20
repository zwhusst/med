package com.souyibao.pipe.keyword;


public class TFModel {
	private String docId = null;
	private String keywordId = null;
	private float weight = 0.0f;
//	private String isNotice = null;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
//
//	public String getIsNotice() {
//		return isNotice;
//	}
//
//	public void setIsNotice(String isNotice) {
//		this.isNotice = isNotice;
//	}
}
