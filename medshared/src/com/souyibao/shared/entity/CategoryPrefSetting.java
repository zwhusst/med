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
	
	public CategoryPrefSetting(ResultSet resultSet) throws SQLException {
//		this.id = resultSet.getString("id");
//		this.topicId = resultSet.getString("topicid");
//		this.prefCategoryId = resultSet.getString("prefcategoryid");
	}
	
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
