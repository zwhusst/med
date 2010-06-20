package com.souyibao.shared.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class TopicCategory {
	@Id
	private long id = 0l;
	
	@Basic
	private String name = null;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="parentId", nullable=true, updatable=false)
	private TopicCategory parent = null;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Topic> topics = null;
	
	@Basic
	private String enabled = "N";

	protected TopicCategory(){}

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

	public TopicCategory getParent() {
		return parent;
	}

	public void setParent(TopicCategory parent) {
		this.parent = parent;
	}

	public String getEnabled() {
		return enabled;
	}
	
	public boolean isEnabled() {
		return enabled.equalsIgnoreCase("Y");
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public Collection<Topic> getTopics() {
		return this.topics;
	}

	public Topic getFirstTopics() {
		if (this.topics == null) {
			return null;
		}
		
		return this.topics.iterator().next();
	}

//	public Set<Doctor> getDoctors() {
//		return doctors;
//	}
//
//	public void setDoctors(Set<Doctor> doctors) {
//		this.doctors = doctors;
//	}

	public boolean belongToTopic(Topic topic) {
		if (this.topics == null) {
			return false;
		}
		
		return this.topics.contains(topic);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof TopicCategory)) {
			return false;
		}
		
		TopicCategory t = (TopicCategory) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("TopicCategory" + this.id).hashCode();
	}
}
