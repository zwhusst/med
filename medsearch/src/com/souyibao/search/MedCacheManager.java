package com.souyibao.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.souyibao.search.util.Util;
import com.souyibao.shared.MedEntityManager;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

/**
 * This class is just to provide some cache mechanism
 * The purpose is to avoid some duplicate calculation
 * 
 * @author IBM
 *
 */
public class MedCacheManager {
	private static MedCacheManager instance = new MedCacheManager();
	private Map<Topic, Collection<SelectOption>> m_categoriesMap = 
		new HashMap<Topic, Collection<SelectOption>>();
	private Map<Topic, Collection<SelectOption>> m_enableCateMap = 
		new HashMap<Topic, Collection<SelectOption>>();
	
	private String m_areaDropHtml = null;
	private MedCacheManager() {
	}

	public static MedCacheManager getInstance() {
		return instance;
	}
	
	/**
	 * Return the enabled category web tree.
	 * @param topicId
	 * @return
	 */
	public Collection<SelectOption> getEnbTopicCategoryWebTree(Topic topic) {
		Collection<SelectOption> result = m_enableCateMap.get(topic);
		if (result != null) {
			return result;
		}

		Collection<SelectOption> allOptions = getTopicCategoryWebTree(topic);
		result = new ArrayList<SelectOption>();
		for (Iterator<SelectOption> iterator = allOptions.iterator(); iterator
				.hasNext();) {
			SelectOption option = iterator.next();
			if (option.isEnabled()) {
				result.add(option);
			}
		}
		
		m_enableCateMap.put(topic, result);
		return result;
	}
	
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	private Collection<SelectOption> getTopicCategoryWebTree(Topic topic) {
		Collection<SelectOption> result = m_categoriesMap.get(topic);
		if (result != null) {
			return result;
		}
		
		result = new ArrayList<SelectOption>();
		m_categoriesMap.put(topic, result);

		Collection<TopicCategory> topicCategories = MedEntityManager.getInstance()
				.getTopCateByTopic(topic);

		short level = 0;
		Collection<TopicCategory> children = null;

		for (Iterator<TopicCategory> iterator = topicCategories.iterator(); iterator
				.hasNext();) {
			TopicCategory category = iterator.next();
			if (category.getId() == 0) {
				continue;
			}
			SelectOption option = Util.formatCategoryFilterVal(topic, category);
			option.setLevel(level);
			option.setEnabled(category.isEnabled());
			result.add(option);
			// for the children
			children = MedEntityManager.getInstance().getChildCategories(
					category);
			if ((children != null) && (!children.isEmpty())) {
				calCategoryTree(topic, children, result, (short)(level + 1));
			}
		}

		return result;
	}
	
	private Collection<SelectOption> getAreaWebTree(Topic topic) {
		Collection<Area> areas = MedEntityManager.getInstance().getAllAreas();
		
		Collection<SelectOption> result = new ArrayList<SelectOption>();
		for (Area area : areas) {
			if (area.getId() == 0) {
				continue;
			}
			SelectOption option = Util.formatCategoryFilterVal(topic, area);
			option.setLevel((short)0);
			option.setEnabled(true);
			result.add(option);
		}
		
		return result;
	}

	private void calCategoryTree(Topic topic, Collection<TopicCategory> children,
			Collection<SelectOption> result, short level) {
		if ((children != null) && (!children.isEmpty())) {
			for (Iterator<TopicCategory> iterator = children.iterator(); iterator
					.hasNext();) {
				TopicCategory category = iterator.next();
				if(category.getId() == 0) {
					continue;
				}
				SelectOption option = Util
						.formatCategoryFilterVal(topic, category);
				option.setLevel(level);
				option.setEnabled(category.isEnabled());
				result.add(option);
				
				Collection<TopicCategory> curChildren = MedEntityManager
						.getInstance().getChildCategories(category);

				if ((curChildren == null) || (curChildren.isEmpty())) {
				} else {
					calCategoryTree(topic, curChildren, result, (short) (level + 1));
				}
			}
		}
	}
}
