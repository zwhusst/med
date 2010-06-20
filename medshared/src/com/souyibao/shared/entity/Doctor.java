package com.souyibao.shared.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
	private long id = 0;

	@Basic
	private String name = null;

	@Basic
	private String grade = null;

	@Lob @Basic(fetch=FetchType.LAZY)
	private String traits = null;

	@Lob @Basic(fetch=FetchType.LAZY)
	private String profile = null;

	@Lob @Basic(fetch=FetchType.LAZY)
	private String other = null;
	
	@Basic
	private String hospitalName = null;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="areaId", nullable=true, updatable=false)
	private Area area = null;
	
	@Basic
	private String alias = null;
	
	@Basic
	private String department = null;

	@Basic
	private String url = null;
	
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTraits() {
		return traits;
	}

	public void setTraits(String traits) {
		this.traits = traits;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Doctor)) {
			return false;
		}
		
		Doctor t = (Doctor) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Doctor_" + this.id).hashCode();
	}
}
