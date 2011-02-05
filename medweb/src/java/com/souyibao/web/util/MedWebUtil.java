package com.souyibao.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.souyibao.freemarker.DiagnoseGuide;
import com.souyibao.freemarker.HospitalViewer;
import com.souyibao.search.SelectOption;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Hospital;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;
import com.souyibao.shared.util.MedUtil;
import com.souyibao.shared.viewer.KeywordDataProvider;
import com.souyibao.web.model.ExSessionData;
import com.souyibao.web.model.KeywordToDocCount;
import com.souyibao.web.model.SessionData;

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

	public static Collection<TopicCategory> getDistinctNameCate(Keyword keyword) {
		Collection<TopicCategory> categories = keyword.getCategories();
		
		Map<String, TopicCategory> result = new HashMap<String, TopicCategory>();
		for (TopicCategory category : categories){
			if (result.get(category.getName()) == null) {
				result.put(category.getName(), category);
			}
		}
		
		return result.values();
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
	
	public static String getKeywrodBrief(Keyword keyword) {
		Collection<TopicCategory> categories = keyword.getCategories();
		
		if ((categories == null) || (categories.isEmpty())) {
			return "";
		} 
		
		StringBuffer categoryInfo = new StringBuffer();		
		for (TopicCategory category : categories) {
			if (categoryInfo.length() == 0) {
				categoryInfo.append(category.getName());
			} else {
				categoryInfo.append(",");
				categoryInfo.append(category.getName());
			}
		}
		
		StringBuffer result = new StringBuffer();
		if (categoryInfo.length() > 0) {
			String categoryPrefix = keyword.getTopic().getBriefprefix();
			result.append(categoryPrefix).append(categoryInfo.toString());
		}
		
		return result.toString();
	}
	
	public static String getKeywordText(Keyword keyword) {
		Collection<String> aliases = keyword.getAliasCollection();
		if ((aliases == null) || (aliases.isEmpty())) {
			return keyword.getName();
		}

		boolean started = true;
		StringBuffer data = new StringBuffer();
		data.append(keyword.getName()).append("(");
		for (Iterator<String> iterator = aliases.iterator(); iterator.hasNext();) {
			if (started) {
				data.append(iterator.next());
				started = false;
			} else if (!started) {
				data.append("," + iterator.next());
			}
		}
		data.append(")");

		return data.toString();
	}
	
	public static String getKeywordExplanation(Keyword keyword) {
		return MedEntityManager.getInstance().getKeywordExplanation(keyword);
	}

	public static List<KeywordToDocCount> adapterKeywordWeightToCount(
			org.apache.lucene.document.Document doc,
			List<KeywordDataProvider> keywordWeights) {
		List<KeywordToDocCount> result = new ArrayList<KeywordToDocCount>();

		for (Iterator<KeywordDataProvider> iterator = keywordWeights.iterator(); iterator
				.hasNext();) {
			KeywordDataProvider keywordWeight = iterator.next();
			Keyword keyword = keywordWeight.getKeyword();

			int count = MedUtil.calKeywordMatchcount(doc, keyword);

			KeywordToDocCount keywordCount = new KeywordToDocCount();
			keywordCount.setKeyword(keyword);
			keywordCount.setCount(count);

			result.add(keywordCount);
		}

		return result;
	}
	
	/**
	 * format the topic category to like "4-3345344"
	 * @param category
	 * @return
	 */
	public static SelectOption formatCategoryFilterVal(Topic topic, TopicCategory category) {
		return new SelectOption(topic.getId() + "-" + category.getId(), category.getName());
	}
	
	public static SelectOption formatCategoryFilterVal(Topic topic, Area area) {
		return new SelectOption(topic.getId() + "-" + area.getId(), area.getName());
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

	
	public static Collection<SelectOption> getTopicCategoryTree(Topic topic) {
		Collection<SelectOption> result = new ArrayList<SelectOption>();
		
		Collection<TopicCategory> topicCategories = MedEntityManager.getInstance()
				.getTopCateByTopic(topic);
		Collection<TopicCategory> children = null;
		
		for (Iterator<TopicCategory> iterator = topicCategories.iterator(); iterator
				.hasNext();) {
			TopicCategory cagory = iterator.next();
			if (cagory.isEnabled()) {
				SelectOption option = MedWebUtil.formatCategoryFilterVal(topic, cagory);
				
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
