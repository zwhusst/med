package com.souyibao.shared.viewer;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.util.MedUtil;


public class KeywordDataProvider implements IDataProvider{

//	private String docId = null;
	private Keyword keyword = null;
	private String alias = null;
	
	private float weight = (float) 0.0;

//	private boolean isNotice = false;

	public KeywordDataProvider() {
	}
	
	public KeywordDataProvider(Keyword keyword) {
		this.keyword = keyword;
		this.alias = MedUtil.getKeywordAliasText(this.keyword);
	}
	
	public Keyword getKeyword() {
		return keyword;
	}
		
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
		this.alias = MedUtil.getKeywordAliasText(this.keyword);
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void addWeight(float addedWeight) {
		weight += addedWeight;
	}
	
	

//	public boolean isNotice() {
//		return isNotice;
//	}
//
//	public void setNotice(boolean isNotice) {
//		this.isNotice = isNotice;
//	}

	public String toString() {
		return " keywordid: " + this.keyword + " weight: " + this.weight;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public String getId() {
		return "" + this.keyword.getId();
	}

	@Override
	public String getName() {
		return this.keyword.getName();
	}

	@Override
	public String getTopicId() {
		return "" + this.keyword.getTopic().getId();
	}

	@Override
	public boolean isEmptyAlias() {
		if ((alias == null) || (alias.isEmpty())) {
			return true;
		}
		
		return false;
	}
}

