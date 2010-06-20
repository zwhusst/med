package com.souyibao.search.util;

import com.souyibao.search.SelectOption;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

public class Util {
	public static SelectOption formatCategoryFilterVal(Topic topic, TopicCategory category) {
		return new SelectOption(topic.getId() + "-" + category.getId(), category.getName());
	}
	
	public static SelectOption formatCategoryFilterVal(Topic topic, Area area) {
		return new SelectOption(topic.getId() + "-" + area.getId(), area.getName());
	}
	
	public static SelectOption formatCategoryFilterVal(String tId, String cId, String name) {
		return new SelectOption(tId + "-" + cId, name);
	}
	
	public static String getCategoryFilterId(String tId, String cId) {
		return  tId + "-" + cId;
	}
}
