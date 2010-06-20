package com.souyibao.shared.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document")
public class Document {
	@Id
	private long id = 0l;
	
	@Basic
	private String name = null;
	
	@Basic
	private String content = null;

	protected Document(){}
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Document)) {
			return false;
		}
		
		Document t = (Document) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Document" + this.id).hashCode();
	}
	
}
