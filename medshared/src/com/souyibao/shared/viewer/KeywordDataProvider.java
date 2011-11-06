/** 
* Copyright (c) 2011-2013  上海宜豪健康信息咨询有限公司 版权所有 
* Shanghai eHealth Technology Company. All rights reserved. 

* This software is the confidential and proprietary 
* information of Shanghai eHealth Technology Company. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with Shanghai eHealth Technology Company. 
*/
package com.souyibao.shared.viewer;

import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.util.MedUtil;


public class KeywordDataProvider implements IDataProvider{

	private Keyword keyword = null;
	private String alias = null;
	
	// this weight comes from file: doc2keyword
	private float weight = (float) 0.0;
	
	private float totalWeight = (float)0.0;

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
		totalWeight += addedWeight;
	}
	
	public void updateTotalWeight(float baseWeight) {
		this.totalWeight = baseWeight * this.weight; 
	}
	
	public float getTotalWeight() {
		return this.totalWeight;
	}

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

	/**
	 * shallow clone
	 */	
	public KeywordDataProvider newObj(){
		KeywordDataProvider newObj = new KeywordDataProvider();
		newObj.keyword = this.keyword;
		newObj.alias = this.alias;
		newObj.totalWeight = this.totalWeight;
		newObj.weight = this.weight;
		
		return newObj;
	}
	
}

