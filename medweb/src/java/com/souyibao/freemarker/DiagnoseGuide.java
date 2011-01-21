package com.souyibao.freemarker;

import java.util.Collection;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopicCategory;

public class DiagnoseGuide {
	private Keyword keyword = null;

	public DiagnoseGuide(Keyword keyword) {
		this.keyword = keyword;
	}
	
	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	
	public Collection<TopicCategory> getCategories() {
		return this.keyword.getCategories();
	}
}
