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
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="hospital")
public class Hospital {
	@Id
	private long id = 0l;
	
	@Basic
	private String name = null;

	@Basic
	private String assetcharacter = null;
	
	@Basic
	private String grade = null;
	
	@Basic
	private String address = null;
	
	@Basic
	private String telephone = null;
	
	@Basic
	private String description = null;

	@ManyToOne(optional=false)
	@JoinColumn(name="areaId", nullable=false, updatable=false)
	private Area area = null;
	
	@Basic
	private String website = null;
	
	@Basic
	private String trait = null;

	private List<TopicCategory> categories = null;
	
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getTrait() {
		return trait;
	}

	public void setTrait(String trait) {
		this.trait = trait;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAssetcharacter() {
		return assetcharacter;
	}

	public void setAssetcharacter(String assetcharacter) {
		this.assetcharacter = assetcharacter;
	}
 
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<TopicCategory> getCategories() {
		return categories;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setCategories(List<TopicCategory> categories) {
		this.categories = categories;
	}
	
	public void updateCategory(Map<String, TopicCategory> idToCategories) {
		String data = this.trait;
		if ((data == null) || ("".equals(data.trim()))) {
			return;
		} 
		
		// check the class PipelineUtil, method: createKeywordData
		String[] temp = data.split("\\|");
		for (int i = 0; i < temp.length; i++) {
			this.addCategory(idToCategories.get(temp[i]));
		}
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Hospital)) {
			return false;
		}
		
		Hospital t = (Hospital) obj;
		return (t.getId() == this.id);
	}

	@Override
	public int hashCode() {
		return ("Hospital" + this.id).hashCode();
	}
}
