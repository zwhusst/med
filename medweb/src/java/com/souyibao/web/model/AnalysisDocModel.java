package com.souyibao.web.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnalysisDocModel {
	private String docId = null;
	private String docName = null;
	private String docContent = null;

	private List<KeywordToDocCount> keywordCounts = null;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocContent() {
		return docContent;
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public Collection<KeywordToDocCount> getKeywordCounts() {
		return keywordCounts;
	}

	public void setKeywordCounts(List<KeywordToDocCount> keywordCounts) {
		this.keywordCounts = keywordCounts;
		Collections.sort(this.keywordCounts, new KeycountComparator());
	}
	
	static class KeycountComparator implements Comparator<KeywordToDocCount> {

		@Override
		public int compare(KeywordToDocCount o1, KeywordToDocCount o2) {
			if (o1.getCount() > o2.getCount()) {
				return -1;
			}

			if (o1.getCount() < o2.getCount()) {
				return 1;
			}

			return 0;
		}
	}
}
