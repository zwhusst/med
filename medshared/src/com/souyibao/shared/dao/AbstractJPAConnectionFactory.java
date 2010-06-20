package com.souyibao.shared.dao;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAPersistence;

/**
 * Common class for making JPA-based connection factories. Also takes care of
 * setting the proper spring context when loading in an axis environment
 */
public abstract class AbstractJPAConnectionFactory {
	protected EntityManagerFactory factory;

	final static String JPA_FILE = "jpa.config.file";
	final static String JPA_DEFAULT_FILE = "jpa.properties";

	/**
	 * Load the factorry using properties found in a jpa config file
	 * @param entityManagerFactoryName
	 * @throws IOException
	 */
	public AbstractJPAConnectionFactory(String entityManagerFactoryName) {
		Properties map = new Properties();
		String jpaFile = System.getProperty(JPA_FILE);
		if (jpaFile == null)
			jpaFile = JPA_DEFAULT_FILE;
		try {
			map.load(AbstractJPAConnectionFactory.class.getResourceAsStream("/"	+ jpaFile));
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not load jpa properies from file:" + jpaFile);
		}
		initEM(entityManagerFactoryName, map);
	}

	public AbstractJPAConnectionFactory(String entityManagerFactoryName,
			Map<String, Object> properties) {
		initEM(entityManagerFactoryName, properties);
	}

	private void initEM(String entityManagerFactoryName, Map properties) {
		factory = Persistence.createEntityManagerFactory(
				entityManagerFactoryName, properties);

		// the factory created can sometimes be null. Check against that
		if (factory == null)
			throw new RuntimeException("Factory not properly created");
	}

	public Connection getUnderlyingJDBCConnectionFromEntityManager() {
		OpenJPAEntityManager kem = OpenJPAPersistence.cast(factory
				.createEntityManager());
		Connection conn = (Connection) kem.getConnection();
		//conn.close();
		return conn;
	}

	public abstract Object openConnection();

}
