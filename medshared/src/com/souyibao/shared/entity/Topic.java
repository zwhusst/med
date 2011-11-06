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

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name="topic")
@NamedQueries( { @NamedQuery(name = Topic.FIND_T_BY_ID, query = "select m from Topic m where m.id=?1", hints = { @QueryHint(name = "openjpa.hint.OptimizeResultCount", value = "1") })})
public class Topic {
	public static final String FIND_T_BY_ID = "find_t_by_id";
	
	@Id
	private long id = 0l;
	
	@Basic
	private String name = null;
	
	@Basic
	private String briefprefix = null;
	
	@Basic
	private short sequence = 0;
	
	@Basic
	private String enabled = "N"; 
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "topic_category", 
			joinColumns = @JoinColumn(name = "topic_ID", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "category_ID", referencedColumnName = "id"))
	private Set<TopicCategory> categories;
	
	protected Topic(){}
	
	public Topic(long id, String name, String briefprefix) {
		this.id = id;
		this.name = name;
		this.briefprefix = briefprefix;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBriefprefix() {
		return briefprefix;
	}

	public void setBriefprefix(String briefprefix) {
		this.briefprefix = briefprefix;
	}

	public short getSequence() {
		return sequence;
	}

	public void setSequence(short sequence) {
		this.sequence = sequence;
	}

	public boolean isEnabled() {
		return enabled.equalsIgnoreCase("Y");
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public Set<TopicCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<TopicCategory> categories) {
		this.categories = categories;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Topic)) {
			return false;
		}
		
		Topic t = (Topic) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Topic_" + this.id).hashCode();
	}

}
