package com.ooluk.mdm.core.test;

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

import com.ooluk.mdm.core.hibernate.CustomPostgreSQLDialect;

/**
 * Common Spring configuration for testing.
 *  
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Configuration
@ComponentScan(basePackages = "com.ooluk.mdm.core.test")
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
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(ds);
		bean.setPackagesToScan("com.ooluk.mdm.core.*");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.default_schema", "mdm");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.dialect", CustomPostgreSQLDialect.class);
		hibernateProperties.put("hibernate.search.default.directory_provider", "filesystem");
		hibernateProperties.put("hibernate.search.default.indexBase", "D:/TEMP/fts");
		bean.setHibernateProperties(hibernateProperties);
		return bean;
	}
	
	@Bean 
	@Autowired
	public PlatformTransactionManager transactionManager(SessionFactory sf) {
		HibernateTransactionManager tm = new HibernateTransactionManager();
		tm.setSessionFactory(sf);
		return tm;
	}
}