package com.ooluk.mdm.data.test;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.ooluk.mdm.data.hibernate.CustomPostgreSQLDialect;

/**
 * Common Spring configuration for testing.
 *  
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Configuration
@ComponentScan(basePackages = "com.ooluk.mdm.data.test")
public class TestConfig {
	
    @Autowired
    private ApplicationContext ctx;
	
	@Bean
	public DataSource dataSource() {
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setUrl("jdbc:postgresql://localhost:5432/test");
		ds.setUser("postgres");
		ds.setPassword("password");
		return ds;
	}

	@Bean
	@Autowired	 
	public JdbcTemplate jdbc(DataSource ds) {
		return new JdbcTemplate(ds);
	}
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource ds) {		
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(ds);
		sf.setPackagesToScan("com.ooluk.mdm.data.*");
		sf.setHibernateProperties(hibernateProperties());
		return sf;
	}
	
	
	@Bean 
	@Autowired
	public PlatformTransactionManager transactionManager(SessionFactory sf) {
		HibernateTransactionManager tm = new HibernateTransactionManager();
		tm.setSessionFactory(sf);
		return tm;
	}
	
	/*
	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds);
		em.setPackagesToScan("com.ooluk.mdm.core.*");
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(hibernateProperties());
		return em;
	}
	
	@Bean 
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	      JpaTransactionManager transactionManager = new JpaTransactionManager();
	      transactionManager.setEntityManagerFactory(emf);
	      return transactionManager;
	}
	*/
	
	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.default_schema", "mdm");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.dialect", CustomPostgreSQLDialect.class);
		hibernateProperties.put("hibernate.search.default.directory_provider", "filesystem");
		hibernateProperties.put("hibernate.search.default.indexBase", "D:/TEMP/fts");
		return hibernateProperties;		
	}
}