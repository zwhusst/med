package com.souyibao.shared.dao;

import java.util.List;

import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.CategoryPrefSetting;
import com.souyibao.shared.entity.Doctor;
import com.souyibao.shared.entity.Document;
import com.souyibao.shared.entity.Hospital;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

public interface IMedDAOConnection {
	public void commit();

	public void close();
	
	public Keyword loadKeyword(long id);
	
	public String getKeywordExplanation(long id);
	
	public Topic loadTopic(long id);

	public List<TopicCategory> loadAllCategory();
	
	public List<CategoryPrefSetting> loadAllCategoryPrefs();

	public List<Topic> loadAllTopics();
	
	public List<Document> loadAllDocuments();

	public String getKeywordDes(long id);
	
	public List<Object> executeSQL(String sql);
	
	public List<Area> loadAllAreas();
	
	public List<Keyword> loadAllKeywords();
	
	public List<TopHospital> loadAllTopHospitals();
	
	public List<Hospital> getHospitalWithArea(Area area);
	
	public List<Doctor> loadDoctorsByArea(long areaId);
	
	public List<Doctor> loadAllDoctors();
	
	public Doctor loadDoctor(long id);
	
	public List<?> query(String clause, Object[] paras, int firstResult, int maxResult);
}
