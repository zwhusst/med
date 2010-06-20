package com.souyibao.shared.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="topdoctor")
public class TopDoctor {
	@Id	
	private long id = 0l;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="doctorId", nullable=true, updatable=false)	
	private Doctor doctor = null;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="categoryId", nullable=false, updatable=false)	
	private TopicCategory category = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
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
		
		if (!(obj instanceof TopDoctor)) {
			return false;
		}
		
		TopDoctor t = (TopDoctor) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("TopDoctor_" + this.id).hashCode();
	}
}
