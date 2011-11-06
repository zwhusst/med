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
package com.souyibao.shared.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="categoryprefsetting")
public class CategoryPrefSetting {
	@Id
	private long id = 0l;

	@ManyToOne(optional=false)
	@JoinColumn(name="topicId", nullable=false, updatable=false)
	private Topic topic = null;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="prefCategoryId", nullable=false, updatable=false)
	private TopicCategory category = null;
	
	protected CategoryPrefSetting(){}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public TopicCategory getCategory() {
		return category;
	}

	public void setCategory(TopicCategory category) {
		this.category = category;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof CategoryPrefSetting)) {
			return false;
		}
		
		CategoryPrefSetting t = (CategoryPrefSetting) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("CategoryPrefSetting" + this.id).hashCode();
	}
}
