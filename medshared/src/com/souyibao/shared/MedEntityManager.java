package com.souyibao.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.souyibao.shared.dao.IMedDAOConnection;
import com.souyibao.shared.dao.IMedDAOConnectionFactory;
import com.souyibao.shared.dao.JPAMedDaoConnectionFactory;
import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.CategoryPrefSetting;
import com.souyibao.shared.entity.Doctor;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

public class MedEntityManager {
	public static final long ROOT_TOPIC_ID = 0;
	public static final long ROOT_CATEGORY_ID = 0;
	

	private static MedEntityManager instance = new MedEntityManager();
	
	private IMedDAOConnectionFactory m_daoFactory = null;
	
	// id to topic
	private Map<String, Topic> m_idToTopic = new LinkedHashMap<String, Topic>();
	
	// id to category
	private Map<String, TopicCategory> m_idToCategory = new HashMap<String, TopicCategory>();
	
	// child category id to parent category id
	private Map<String, TopicCategory> m_childCateToParent = new HashMap<String, TopicCategory>();
	
	// id to category prefcategory id
	private Map<Topic, TopicCategory> m_topicToPrefCate = new HashMap<Topic, TopicCategory>();
	
	// id to keyword
	private Map<String, Keyword> m_idToKeyword = new HashMap<String, Keyword>();
	
	// keyword name(hashcode) to keyword id
	private Map<Integer, Keyword> m_hashToKeyword = new HashMap<Integer, Keyword>();
	
	// id to Area
	private Map<String, Area> m_idToArea = new LinkedHashMap<String, Area> ();
	
	// id to TopHospital
	private Map<String, TopHospital> m_idToTopHospital = new LinkedHashMap<String, TopHospital>();
	
//	private Map<String, Hospital> m_idToHospital = new LinkedHashMap<String, Hospital>();
	
	// component key(TopicCategory & Area) to TopHospital
	private Map<String, List<TopHospital>> m_filterToHospital = new LinkedHashMap<String, List<TopHospital>>();
		
	private Topic m_rootTopic = null;
	private TopicCategory m_rootCategory = null;
	
	private MedEntityManager() {
		m_daoFactory = new JPAMedDaoConnectionFactory();
		IMedDAOConnection con = m_daoFactory.openConnection();
		
		loadTopics(con);
		loadCategories(con);
		loadPrefCategory(con);
		loadKeywords(con);
		loadAreas(con);
		loadTopHospital(con);
		loadTopDoctor(con);
		
		m_rootTopic = this.getTopicById("" + ROOT_TOPIC_ID);
		m_rootCategory = this.getCategoryById("" + ROOT_CATEGORY_ID);
		
		con.close();
	}

	public static MedEntityManager getInstance() {
		return instance;
	}

	private void loadKeywords(IMedDAOConnection con) {
		List<Keyword> keywords = con.loadAllKeywords();
		
		for (Keyword keyword : keywords) {
			updateKeywrodInfo(keyword);
			
			m_idToKeyword.put("" + keyword.getId(), keyword);

			m_hashToKeyword.put(keyword.getName().hashCode(), keyword);
			
			// put the alias
			Collection<String> aliases = keyword.getAliasCollection();
			if (aliases != null) {
				for (String alias : aliases) {
					m_hashToKeyword.put(alias.hashCode(), keyword);
				}
			}
		}
	}
	
	private void loadAreas(IMedDAOConnection con) {
		List<Area> areas = con.loadAllAreas();

		for (Area area : areas) {
			this.m_idToArea.put("" + area.getId(), area);
		}
	}	
	
	private void loadTopHospital(IMedDAOConnection con) {
		List<TopHospital> hospitals = con.loadAllTopHospitals();

		for (TopHospital tHospital : hospitals) {
			m_idToTopHospital.put("" + tHospital.getId(), tHospital);
			
			// for the hospital
//			Hospital hospital = tHospital.getHospital();
//			m_idToHospital.put("" + hospital.getId(), hospital);
			
			String grpId = getTopHospitalGrpId(tHospital);
			List<TopHospital> grpHospitals = m_filterToHospital.get(grpId);
			
			if (grpHospitals == null) {
				grpHospitals = new ArrayList<TopHospital>();
				m_filterToHospital.put(grpId, grpHospitals);
			}
			grpHospitals.add(tHospital);
			
			// update the categorys
			tHospital.getHospital().updateCategory(m_idToCategory);
		}
	}
	
	private void loadTopDoctor(IMedDAOConnection con) {
		
	}

	public List<TopHospital> getTopHospitalWithFilter(TopicCategory category,
			Area area) {
		String grpId = getTopHospitalGrpId(category, area);
		
		return m_filterToHospital.get(grpId);
	}
	
	public List<TopHospital> getTopHospitalWithFilter(String categoryId,
			String areaId) {
		String grpId = getTopHospitalGrpId(categoryId, areaId);

		return m_filterToHospital.get(grpId);
	}
	
	private String getTopHospitalGrpId(TopHospital tHospital) {
		return getTopHospitalGrpId(tHospital.getCategory(), tHospital.getArea());
	}
		
	private String getTopHospitalGrpId(TopicCategory category, Area area) {
		return getTopHospitalGrpId("" + category.getId(), "" + area.getId());
	}
	
	private String getTopHospitalGrpId(String categoryId,
			String areaId) {
		StringBuffer result = new StringBuffer();
		
		result.append("category_").append(categoryId);
		result.append("_area_").append(areaId);
		
		return result.toString();
	}
	
	private void updateKeywrodInfo(Keyword keyword) {
		keyword.updateAlias();
		keyword.updateCategory(this.m_idToCategory);
	}
	
	private void loadTopics(IMedDAOConnection con) {
		List<Topic> topics = con.loadAllTopics();
		
		for (Topic topic: topics) {
			m_idToTopic.put("" + topic.getId(), topic);
		}
	}
	
	private void loadCategories(IMedDAOConnection con) {
		List<TopicCategory> categories = con.loadAllCategory();
		
		for (TopicCategory category : categories) {
			m_idToCategory.put("" + category.getId(), category);
			if (category.getParent() != null) {
				m_childCateToParent.put("" + category.getId(), category.getParent());
			}
			
//			category.updateTopic(m_idToTopic);
		}
	}
	
	private void loadPrefCategory(IMedDAOConnection con) {
		List<CategoryPrefSetting> prefs = con.loadAllCategoryPrefs();
		
		for (CategoryPrefSetting pref : prefs) {
			m_topicToPrefCate.put(pref.getTopic(), pref.getCategory());
		}
	}
	
	public Topic getTopicById(String id) {
		return m_idToTopic.get(id);
	}
	
	public TopicCategory getCategoryById(String id) {
		return m_idToCategory.get(id);
	}
	
	public Collection<Topic> getAllTopics() {
		return m_idToTopic.values();
	}
		
	public Collection<TopicCategory> getAllCategories() {
		return m_idToCategory.values();
	}
	
	public boolean isRightChildCate(TopicCategory child, TopicCategory parent) {
		TopicCategory temp = child.getParent();
		
		while (temp != null) {
			if (temp.equals(parent)) {
				return true;
			}
			
			temp = temp.getParent(); 
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param parentCategoryId
	 * @return
	 */
	public Collection<TopicCategory> getChildCategories(TopicCategory parent) {
		Collection<TopicCategory> allCategories = getAllCategories();

		Collection<TopicCategory> result = new ArrayList<TopicCategory>();
		for (Iterator<TopicCategory> iterator = allCategories.iterator(); iterator
				.hasNext();) {
			TopicCategory category = iterator.next();

			if (parent.equals(category.getParent())) {
				result.add(category);
			}
		}

		return result;
	}
	
	/**
	 * get all the sibling categories, the return result always are limited in one topic
	 * @param categoryId
	 * @return
	 */
	public Collection<TopicCategory> getSiblingCategories(TopicCategory category) {
		Collection<TopicCategory> result = new ArrayList<TopicCategory>();
		if (ROOT_CATEGORY_ID == category.getId()) {
			// only one root can be found
			result.add(getCategoryById("" + ROOT_CATEGORY_ID));

			return result;
		}

		TopicCategory pCategory = category.getParent();
		
		if (ROOT_CATEGORY_ID == pCategory.getId()) {
			return this.getTopCateByTopic(pCategory.getFirstTopics());
		}

		Collection<TopicCategory> allCategories = getAllCategories();
		for (Iterator<TopicCategory> iterator = allCategories.iterator(); iterator
				.hasNext();) {
			category = iterator.next();

			if (pCategory.equals(category.getParent())) {
				result.add(category);
			}
		}

		return result;
	}
	
	/**
	 * Retrieve the categories
	 * The category is limited to root category(id is 0)
	 * @param topicId
	 * @return
	 */
	public Collection<TopicCategory> getTopCateByTopic(Topic topic) {
		Collection<TopicCategory> allCategories = getAllCategories();

		Collection<TopicCategory> result = new ArrayList<TopicCategory>();
		for (Iterator<TopicCategory> iterator = allCategories.iterator(); iterator
				.hasNext();) {
			TopicCategory category = iterator.next();
			
			if (category.getId() == ROOT_CATEGORY_ID) {
				continue;
			}

			if ((ROOT_CATEGORY_ID == category.getParent().getId())
					&& category.belongToTopic(topic)) {
				result.add(category);
			}
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	public TopicCategory getRootCategory() {
		return this.m_rootCategory;
	}
	
	public Topic getRootTopic() {
		return this.m_rootTopic;
	}
	
	/**
	 * 
	 * @param topic
	 * @return
	 */
	public TopicCategory getPrefCateByTopic(Topic topic) {
		TopicCategory result = m_topicToPrefCate.get(topic);
				
		return (result == null)? getRootCategory() : result;
	}
	
	public Collection<TopicCategory> getAllPrefCates() {
		return m_topicToPrefCate.values();
	}
	
	public Collection<Keyword> getAllKeywords() {
		return m_idToKeyword.values();
	}
	
	public Collection<Area> getAllAreas() {
		return m_idToArea.values();
	}
	
	public Area getAreaById(String id) {
		return m_idToArea.get(id);
	}

	public Keyword getKeywordById(String id) {
		return m_idToKeyword.get(id);		
	}
	
	public Doctor getDoctorById(String id) {
		IMedDAOConnection con = m_daoFactory.openConnection();
		
		Doctor doctor = con.loadDoctor(Long.parseLong(id));
		con.close();
		
		return doctor;
	}
	
	public Keyword getKeywordByName(String name) {
		if (name == null) {
			return null;
		}

		return m_hashToKeyword.get(name.hashCode());
	}
	
	/**
	 * filter one collection of KeywordWeight according to one specified category
	 * @param keywords
	 * @param categoryId
	 * @return
	 */
	public List<Keyword> filterKeywordByCategory(
			List<Keyword> keywords, TopicCategory category) {
		if (ROOT_CATEGORY_ID == category.getId()) {
			return keywords;
		}
		
		List<Keyword> result = new ArrayList<Keyword>();

		for (Keyword keyword : result) {
			if (isRightFilterKeyword(keyword, category)) {
				result.add(keyword);
			}
		}

		return result;
	}
	
	public Set<Topic> getTopicsWithIds(String[] ids) {
		if ((ids == null) || (ids.length == 0)) {
			return null;
		}
		
		Set<Topic> result = new HashSet<Topic>();
		for (int i = 0; i < ids.length; i++) {
			Topic t = this.getTopicById(ids[i]);
			if (t != null)
				result.add(t);
		}
		
		return result;
	}

	public Set<TopicCategory> getCategoriesWithIds(String[] ids) {
		if ((ids == null) || (ids.length == 0)) {
			return null;
		}
		
		Set<TopicCategory> result = new HashSet<TopicCategory>();
		for (int i = 0; i < ids.length; i++) {
			TopicCategory t = this.getCategoryById(ids[i]);
			if (t != null)
				result.add(t);
		}
		
		return result;
	}
	
	public Set<Keyword> getKeysWithIds(String[] ids) {
		if ((ids == null) || (ids.length == 0)) {
			return null;
		}
		
		Set<Keyword> result = new HashSet<Keyword>();
		for (int i = 0; i < ids.length; i++) {
			Keyword t = this.getKeywordById(ids[i]);
			if (t != null)
				result.add(t);
		}
		
		return result;
	}
	
	/**
	 * Return the keyword explanation.
	 * 
	 * @param keyword
	 * @return
	 */
	public String getKeywordExplanation(Keyword keyword){		
		if (keyword.isExplanationLoaded()) {
			return keyword.getExplanation();
		}
				
		synchronized (keyword) {
			if (keyword.isExplanationLoaded()) {
				return keyword.getExplanation();
			}
			
			IMedDAOConnection con = m_daoFactory.openConnection();
			String explantion = con.getKeywordDes(keyword.getId());
			keyword.setExplanation(explantion);
			con.close();
		}
		
		
		return keyword.getExplanation();
	}

	public boolean isRightFilterKeyword(Keyword keyword, TopicCategory category) {
		Collection<TopicCategory> categories = keyword.getCategories();
		if ((categories == null) || (categories.isEmpty())) {
			return false;
		}
		
		if (categories.contains(category)) {
			return true;
		}
		
		for (TopicCategory child : categories) {			
			boolean result = MedEntityManager.getInstance().isRightChildCate(
					child, category);
			if (result) {
				return true;
			}
		}

		return false;
	}
	
	public Map<String, TopicCategory> getIdToCategories() {
		return this.m_idToCategory;
	}
	
	public List<?> query(String clause, Object[] paras, int firstResult,
			int maxResult) {
		IMedDAOConnection  con = this.m_daoFactory.openConnection();
		List<?> data = con.query(clause, paras, firstResult, maxResult);
		con.close();
		
		return data;
	}
}
