package com.souyibao.web.model;

import java.util.Collection;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopicCategory;

public class KeywordCategorysMenu {
	private Keyword keyword = null;
	private Collection<TopicCategory> categories = null;

	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	public Collection<TopicCategory> getCategories() {
		return categories;
	}
	public void setCategories(Collection<TopicCategory> categories) {
		this.categories = categories;
	}
}
