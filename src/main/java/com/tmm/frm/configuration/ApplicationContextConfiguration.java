package com.tmm.frm.configuration;

import java.beans.PropertyVetoException;

import javax.persistence.EntityManagerFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.tmm.frm.tagging.TransactionTaggingEngine;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ, proxyTargetClass = true)
@ComponentScan({"com.tmm.frm.service", "com.tmm.frm.helper","com.tmm.frm.security.dao", "com.tmm.frm.core.dao", "com.tmm.frm.security"})
@PropertySource("classpath:META-INF/spring/database.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationContextConfiguration {

	@Autowired Environment env;

	@Bean public PlatformTransactionManager txManager() throws PropertyVetoException {
		JpaTransactionManager bean = new JpaTransactionManager();
		bean.setEntityManagerFactory(entityManagerFactory());
		return bean;
	}

	@Bean public EntityManagerFactory entityManagerFactory() throws PropertyVetoException {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan("com.tmm.frm.domain", "com.tmm.frm.security");
		emf.setPersistenceProvider(new HibernatePersistenceProvider());
		emf.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");
		//Search settings
		emf.getJpaPropertyMap().put("hibernate.search.default.directory_provider", "filesystem");
		emf.getJpaPropertyMap().put("hibernate.search.default.indexBase", "lucene");
		emf.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		emf.afterPropertiesSet();
		emf.setJpaDialect(new HibernateJpaDialect());
		return emf.getObject();
	}
	
	@Bean public BasicDataSource dataSource() throws PropertyVetoException {	
		BasicDataSource bean = new BasicDataSource();
		bean.setDriverClassName("com.mysql.jdbc.Driver");
		bean.setUrl(env.getProperty("database.url"));
		bean.setUsername(env.getProperty("database.username"));
		bean.setPassword(env.getProperty("database.password"));
		return bean;
	}

	@Bean public ValidatorFactory validatorFactory() {
		ValidatorFactory bean = Validation.buildDefaultValidatorFactory();
		return bean;
	}

	@Bean public Validator validator() {
		Validator bean = validatorFactory().getValidator();
		return bean;
	}
	
	@Bean TransactionTaggingEngine transactionTaggingEngine(){
		return new TransactionTaggingEngine();
	}
}
