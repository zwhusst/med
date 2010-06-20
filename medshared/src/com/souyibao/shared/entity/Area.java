package com.souyibao.shared.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="area")
public class Area {
	@Id
	private long id = 0;
	
	@Basic
	private String name = null;
	
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Area)) {
			return false;
		}
		
		Area t = (Area) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Area" + this.id).hashCode();
	}

}
