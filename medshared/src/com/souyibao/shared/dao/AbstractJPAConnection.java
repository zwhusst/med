package com.souyibao.shared.dao;

import javax.persistence.EntityManager;

/**
 * Common class for JPA-based connection
 */
public class AbstractJPAConnection {

	protected EntityManager entityManager;

	public AbstractJPAConnection(EntityManager createEntityManager) {
		entityManager = createEntityManager;
	}

	public void close() {
	    // commit();
	}
	
	public void checkTransactionIsActive() {
		if (!entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().begin();	
		}
	}

	public void commit() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().commit();
		}
	}

}
