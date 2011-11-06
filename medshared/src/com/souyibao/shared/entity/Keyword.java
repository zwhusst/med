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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="keyword")
@NamedQueries( { @NamedQuery(name = Keyword.FIND_BY_ID, query = "select m from Keyword m where m.id=?1", hints = { @QueryHint(name = "openjpa.hint.OptimizeResultCount", value = "1") }),
				 @NamedQuery(name = Keyword.GET_EXPLANATION_BY_ID, query = "select m.explanation from Keyword m where m.id=?1")})
public class Keyword {
	public static final String FIND_BY_ID = "find_by_id"; 
	public static final String GET_EXPLANATION_BY_ID = "get_explanation_by_id";
		
	@Id
	private long id = 0l;
	
	@Basic
	private String name = null;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="topicId", nullable=false, updatable=false)
	private Topic topic = null;
	
	@Basic
	private String explanation = null;
	
	@Basic
	private String categoryIds = null;
	
	@Basic
	private String alias = null;
	
	@Basic
	private String others = null;
	
	private Collection<TopicCategory> categories = null;
	private Collection<String> aliasCollection = null;
	
	@Transient
	private boolean explanationLoaded = false;

	protected Keyword(){}
	
	public Keyword(long id, String name, Topic topic, String categoryIds, String alias) {
		this.id = id;
		this.name = name;
		this.topic = topic;
		this.categoryIds = categoryIds;
		this.alias = alias;
	}
	
	public void updateAlias() {
		String data = this.alias;
		if ((data == null) || ("".equals(data.trim()))) {
			return;
		}
		
		// check the class PipelineUtil, method: createKeywordData
		String[] temp = data.split("\\|");
		for (int i = 0; i < temp.length; i++) {
			this.addAlias(temp[i]);
		}
	}
	
	public void updateCategory(Map<String, TopicCategory> idToCategories) {
		String data = this.categoryIds;
		if ((data == null) || ("".equals(data.trim()))) {
			return;
		} 
		
		// check the class PipelineUtil, method: createKeywordData
		String[] temp = data.split("\\|");
		for (int i = 0; i < temp.length; i++) {
			this.addCategory(idToCategories.get(temp[i]));
		}
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
		
		this.explanationLoaded = true;
	}

	private void addAlias(String alias) {
		if (aliasCollection == null) {
			aliasCollection = new ArrayList<String>();
		}
		
		aliasCollection.add(alias);
	}
	
	private void addCategory(TopicCategory category) {
		if (category == null) {
			return;
		}
		
		if (categories == null) {
			categories = new ArrayList<TopicCategory>();
		}

		this.categories.add(category);
	}
	
	public Collection<String> getAliasCollection() {
		if (this.aliasCollection == null) {
			return null;
		}

		return this.aliasCollection;
	}
	
	public Collection<TopicCategory> getCategories() {
		if (this.categories == null) {
			return null;
		}

		return this.categories;
	}

	public boolean isExplanationLoaded() {
		return explanationLoaded;
	}

	public void setExplanationLoaded(boolean explanationLoaded) {
		this.explanationLoaded = explanationLoaded;
	}

	public String toString() {
		return "";
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public String getAlias() {
		return alias;
	}
	
	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Keyword)) {
			return false;
		}
		
		Keyword t = (Keyword) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Keyword" + this.id).hashCode();
	}
}

