package com.souyibao.shared.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tophospital")
public class TopHospital {
	@Id	
	private long id = 0l;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="hospitalId", nullable=false, updatable=false)	
	private Hospital hospital = null;
	
	@Basic
	private short sequence = 0;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="categoryId", nullable=false, updatable=false)	
	private TopicCategory category = null;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="areaId", nullable=false, updatable=false)	
	private Area area = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public short getSequence() {
		return sequence;
	}

	public void setSequence(short sequence) {
		this.sequence = sequence;
	}

	public TopicCategory getCategory() {
		return category;
	}

	public void setCategory(TopicCategory category) {
		this.category = category;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof TopHospital)) {
			return false;
		}
		
		TopHospital t = (TopHospital) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("TopHospital" + this.id).hashCode();
	}
}
