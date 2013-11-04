package com.souyibao.shared.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntityCRUDDao {
	private AbstractJPAConnectionFactory jpaFactory = null;
	private static EntityCRUDDao m_dao = new EntityCRUDDao();
	
	private EntityCRUDDao(){
		jpaFactory = new AbstractJPAConnectionFactory("cn.com.ttdong.entity"){
			@Override
			public Object openConnection() {
				return factory.createEntityManager();
			}			
		};
	}
	
	public static EntityCRUDDao getInstance() {
		return m_dao;
	}
	
	public void save(Object object) throws Exception {
		EntityManager em = (EntityManager)jpaFactory.openConnection();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.persist(object);
			em.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			em.close();
		}
		
	}
	
	public void update(Object object) throws Exception {
		EntityManager em = (EntityManager)jpaFactory.openConnection();
		EntityTransaction transaction = em.getTransaction();

		try {
			transaction.begin();
			em.merge(object);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
