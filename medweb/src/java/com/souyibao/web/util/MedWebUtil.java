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
package com.souyibao.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.souyibao.freemarker.DiagnoseGuide;
import com.souyibao.freemarker.HospitalViewer;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

public class MedWebUtil {
	
	public static Collection<DiagnoseGuide> extractDiagnoseGuides(
			Collection<String> topicScopes, Collection<Keyword> keywords) {
		if (keywords != null) {
			
			Collection<DiagnoseGuide> diagnoseGuides = new ArrayList<DiagnoseGuide>();
			
			for (Keyword keyword : keywords) {
				if (topicScopes.contains("" + keyword.getTopic().getId())) {
					DiagnoseGuide guide = new DiagnoseGuide(keyword);

					diagnoseGuides.add(guide);
				}
			}
			return diagnoseGuides;
		}

		return null;

	}

	
	public static List<HospitalViewer> getFilterHospital(String categoryId, String areaId) {
		List<TopHospital> topHospitals = MedEntityManager.getInstance()
				.getTopHospitalWithFilter(categoryId, areaId);
		
		if (topHospitals ==  null) {
			return null;
		}
		
		List<HospitalViewer> result = new ArrayList<HospitalViewer>();
		for (TopHospital tHospital: topHospitals) {
			HospitalViewer viewer = new HospitalViewer();
			viewer.setTophospital(tHospital);
			
			result.add(viewer);
		}
		
		return result;
	}
	
	/**
	 * parse the category filter value
	 * topicid --> categoryId
	 * @param categoryFilterVal
	 * @return
	 */
	private static Map<Topic, TopicCategory> parseCategoryFilterVal(
			String[] categoryFilterVal) {
		if ((categoryFilterVal == null) || (categoryFilterVal.length == 0)) {
			return null;
		}
		
		Map<Topic, TopicCategory> result = new HashMap<Topic, TopicCategory>();
		for (int i = 0; i < categoryFilterVal.length; i++) {
			int idx = categoryFilterVal[i].indexOf('-');
			if (idx > 0) {
				String topicId = categoryFilterVal[i].substring(0, idx);
				String categoryId = categoryFilterVal[i].substring(idx + 1);

				Topic topic = MedEntityManager.getInstance().getTopicById(topicId);
				
				TopicCategory category = MedEntityManager.getInstance().getCategoryById(categoryId);
				
				if ((category == null) || (topic == null)) {
					continue;
				}
				
				// if it isn't one root category, 
				if (category.belongToTopic(topic)) {
					result.put(topic, category);
				} 			
			}	
		}
		
		return result;
	}

	/**
	 * Supplement preference category to the category filter
	 * 
	 * @param prefCategoryMap
	 */
	public static Map<Topic, TopicCategory> supplementPrefCategory(
			String[] categoryFilters) {	
		Map<Topic, TopicCategory> result = parseCategoryFilterVal(categoryFilters);
		result = (result == null) ? new HashMap<Topic, TopicCategory>() : result;		
		Collection<Topic> allTopic = MedEntityManager.getInstance().getAllTopics();
		
		for (Topic topic : allTopic) {
			if (result.get(topic) == null) {
				TopicCategory prefCategory = MedEntityManager.getInstance().getPrefCateByTopic(topic);
				if (prefCategory != null) {
					result.put(topic, prefCategory);
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		String test = "54-44";
		
		int idx = test.indexOf("-");
		System.out.println(test.substring(0, idx));
		System.out.println(test.substring(idx + 1));
	}
}
