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