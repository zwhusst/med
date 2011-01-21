package com.souyibao.freemarker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.souyibao.shared.MedProperties;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.util.MedUtil;
import com.souyibao.web.util.MedWebUtil;

public class GuideInfoDataModel {
	private String queryKeywordId = null;
	private String queryCategoryId = null;
	
	private Collection<Keyword> diagnoseKeywords = null;
	private Collection<Area> areas = null;
	
	private String areaId = null;
	private String guideType = null;
	
	private Collection<DoctorViewer> doctors = null;
	private Collection<HospitalViewer> hospitals = null;
	
	public String getQueryKeywordId() {
		return queryKeywordId;
	}
	public void setQueryKeywordId(String queryKeywordId) {
		this.queryKeywordId = queryKeywordId;
	}
	public String getQueryCategoryId() {
		return queryCategoryId;
	}
	public void setQueryCategoryId(String queryCategoryId) {
		this.queryCategoryId = queryCategoryId;
	}	
	public Collection<Keyword> getDiagnoseKeywords() {
		return diagnoseKeywords;
	}
	public void setDiagnoseKeywords(Collection<Keyword> diagnoseKeywords) {
		this.diagnoseKeywords = diagnoseKeywords;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getGuideType() {
		return guideType;
	}
	public void setGuideType(String guideType) {
		this.guideType = guideType;
	}
	public Collection<DoctorViewer> getDoctors() {
		return doctors;
	}
	public void setDoctors(Collection<DoctorViewer> doctors) {
		this.doctors = doctors;
	}
	public Collection<HospitalViewer> getHospitals() {
		return hospitals;
	}
	public void setHospitals(Collection<HospitalViewer> hospitals) {
		this.hospitals = hospitals;
	}
	
	public Collection<Area> getAreas() {
		return areas;
	}
	public void setAreas(Collection<Area> areas) {
		this.areas = areas;
	}
	public String getDiagnoseKeywordIds() {
		List<String> topicScopes = MedProperties.getInstance()
				.getTopics4Guide();
		
		if (diagnoseKeywords != null) {
			Collection<String> ids = new ArrayList<String>();
			
			for (Keyword keyword : diagnoseKeywords) {
				if (topicScopes.contains(""+keyword.getTopic().getId())) {
					ids.add(""+keyword.getId());
				}
			}
			
			return MedUtil.joinString(ids, ",");
		}
		
		return null;
	}
	// keywords are for diagnose guide info
	public Collection<DiagnoseGuide> getDiagnoseGuides() {
		List<String> topicScopes = MedProperties.getInstance()
				.getTopics4Guide();
		
		return MedWebUtil.extractDiagnoseGuides(topicScopes, diagnoseKeywords);
	}
	
}
