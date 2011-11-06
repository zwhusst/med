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
