package com.souyibao.shared.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.souyibao.shared.entity.Area;
import com.souyibao.shared.entity.CategoryPrefSetting;
import com.souyibao.shared.entity.Doctor;
import com.souyibao.shared.entity.Document;
import com.souyibao.shared.entity.Hospital;
import com.souyibao.shared.entity.Keyword;
import com.souyibao.shared.entity.TopHospital;
import com.souyibao.shared.entity.Topic;
import com.souyibao.shared.entity.TopicCategory;

/**
 * Persistence for task using JPA.
 */
public class JPAMedDaoConnection extends AbstractJPAConnection implements
		IMedDAOConnection {

	public JPAMedDaoConnection(EntityManager createEntityManager) {
		super(createEntityManager);
	}

	@Override
	public String getKeywordDes(long id) {
		Query query = entityManager
				.createNamedQuery(Keyword.GET_EXPLANATION_BY_ID);
		query.setParameter(1, id);

		return (String) query.getSingleResult();
	}

	@Override
	public List<TopicCategory> loadAllCategory() {
		String jql = "select m from TopicCategory m";

		Query query = entityManager.createQuery(jql);
		
		return (List<TopicCategory>) query.getResultList();
	}
	
	@Override
	public List<Keyword> loadAllKeywords() {
		String jql = "select NEW com.souyibao.shared.entity.Keyword(m.id, m.name, m.topic, m.categoryIds, m.alias) from Keyword m";
		Query query = entityManager.createQuery(jql);
		
		return (List<Keyword>) query.getResultList();
	}
	
	@Override
	public List<Topic> loadAllTopics() {
		String jql = "select m from Topic m order by m.sequence";
		Query query = entityManager.createQuery(jql);
		
		return (List<Topic>) query.getResultList();
	}

	@Override
	public Keyword loadKeyword(long id) {
		Query query = entityManager.createNamedQuery(Keyword.FIND_BY_ID);
		query.setParameter(1, id);

		return (Keyword) query.getSingleResult();
	}

	@Override
	public Topic loadTopic(long id) {
		Query query = entityManager.createNamedQuery(Topic.FIND_T_BY_ID);
		query.setParameter(1, id);

		return (Topic) query.getSingleResult();
	}

	@Override
	public List<CategoryPrefSetting> loadAllCategoryPrefs() {
		String jql = "select m from CategoryPrefSetting m";

		Query query = entityManager.createQuery(jql);

		return (List<CategoryPrefSetting>) query.getResultList();
	}
	
	// only for the internal used.
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public String getKeywordExplanation(long id) {
		String jql = "select m.explanation from Keyword m where id=?1";
		
		Query query = entityManager.createQuery(jql);
		query.setParameter(1, id);
		
		String result = (String)query.getSingleResult();
		return (result == null) ? "" : result;
		
	}

	@Override
	public List<Document> loadAllDocuments() {
		String jql = "select m from Document m";

		Query query = entityManager.createQuery(jql);

		return (List<Document>) query.getResultList();
	}

	@Override
	public List<Object> executeSQL(String sql) {
		String jql = "select m from Document m";

		Query query = entityManager.createNativeQuery(sql);

		return query.getResultList();
	}

	@Override
	public List<TopHospital> loadAllTopHospitals() {
		String jql = "select m from TopHospital m order by m.sequence";

		Query query = entityManager.createQuery(jql);

		return query.getResultList();
	}

	@Override
	public List<Area> loadAllAreas() {
		String jql = "select m from Area m order by m.id";

		Query query = entityManager.createQuery(jql);

		return query.getResultList();
	}

	@Override
	public List<Hospital> getHospitalWithArea(Area area) {
		String jql = "select m from Hospital m where m.area.id=?1";
		
		Query query = entityManager.createQuery(jql);
		query.setParameter(1, area.getId());
		
		return query.getResultList();
	}

	@Override
	public List<Doctor> loadAllDoctors() {
		String jql = "select m from Doctor m";
		
		Query query = entityManager.createQuery(jql);
		
		return query.getResultList();
	}
	
	public List<Doctor> loadDoctorsByArea(long areaId) {
		String jql = "select m from Doctor m where m.area.id=?1";
		
		Query query = entityManager.createQuery(jql);
		query.setParameter(1, areaId);
		
		return query.getResultList();
	}

	@Override
	public Doctor loadDoctor(long id) {
		String jql = "select m from Doctor m where m.id=?1";
		
		Query query = entityManager.createQuery(jql);
		query.setParameter(1, id);
		
		return (Doctor)query.getSingleResult();
	}
	
	public List<?> query(String clause, Object[] paras, int firstResult,
			int maxResult) {
		Query query = entityManager.createQuery(clause);
		
		if (firstResult >= 0) {
			query.setFirstResult(firstResult);	
		}
		
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		
		if (paras != null) {
			for (int i = 1; i <= paras.length; i++) {
				query.setParameter(i, paras[i-1]);
			}
		}
		
		return query.getResultList();
	}
}
