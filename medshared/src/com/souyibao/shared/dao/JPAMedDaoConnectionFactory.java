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

import java.util.Map;

/**
 * Factory for JPA-based task persistence
 */
public class JPAMedDaoConnectionFactory extends AbstractJPAConnectionFactory
		implements IMedDAOConnectionFactory {

	public JPAMedDaoConnectionFactory(Map<String, Object> properties) {
		super("cn.com.ttdong.entity", properties);
	}

	public JPAMedDaoConnectionFactory() {
		super("cn.com.ttdong.entity");
	}

	public IMedDAOConnection openConnection() {
		return new JPAMedDaoConnection(factory.createEntityManager());
	}
}