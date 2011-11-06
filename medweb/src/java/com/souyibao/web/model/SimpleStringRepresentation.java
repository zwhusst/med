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
package com.souyibao.web.model;

public class SimpleStringRepresentation {
	private String content = null;
	private RepresentationMeta meta = null;
	
	public SimpleStringRepresentation(String content) {
		this.content = content;
	}
	
	public SimpleStringRepresentation(RepresentationMeta meta, String content) {
		this.meta = meta;
		this.content = content;
	}

	public RepresentationMeta getMeta() {
		return meta;
	}

	public void setMeta(RepresentationMeta meta) {
		this.meta = meta;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
