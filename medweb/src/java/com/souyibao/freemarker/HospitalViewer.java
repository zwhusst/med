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
