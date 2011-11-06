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
