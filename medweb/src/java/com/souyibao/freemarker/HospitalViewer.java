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
package com.souyibao.freemarker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.TopicCategory;

public class HospitalViewer {
	private TopHospital th = null;
	
	public TopHospital getTophospital() {
		return th;
	}
	public void setTophospital(TopHospital th) {
		this.th = th;
	}
	
	public String getWebsite(){
		return th.getHospital().getWebsite();
	}
	
	public long getTId() {
		return th.getId();
	}
	public String getName() {
		return th.getHospital().getName();
	}
	public String getGrade() {
		return th.getHospital().getGrade();
	}
	public Area getArea() {
		return th.getHospital().getArea();
	}
	public Collection<TopicCategory> getCategories() {
		Map<String, TopicCategory> result = new HashMap<String, TopicCategory>();
		
		if (th.getHospital().getCategories() != null) {
			for (TopicCategory c : th.getHospital().getCategories()) {
				result.put(c.getName(), c);
			}
		}
		
		return result.values();
	}

	public String getDescription() {
		return th.getHospital().getDescription();
	}
	public String getAddress() {
		return th.getHospital().getAddress();
	}
	public String getTelephone() {
		return th.getHospital().getTelephone();
	}
	public long getId() {
		return th.getHospital().getId();
	}
}
