package com.paymenthub.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.*")
@EnableTransactionManagement
public class SpringJPAConfiguration {
	
	@Bean
	public DataSource getDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setUsername("princekumart");
		dataSource.setPassword("Pkt@260599");
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		return dataSource;
	}
	
	@Bean("entityManagerFactory")
	LocalContainerEntityManagerFactoryBean createEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		
		//1. Setting DataSource object
		factory.setDataSource(getDataSource());
				
		//2. Providing package information of Entity classes	
		factory.setPackagesToScan("com.*");
				
		//3. Providing Hibernate Property to Enitity Manager
		factory.setJpaProperties(hiberateProperties());
				
		//4.Passing predefined hibernate Adaptor object to Entity Manager
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		factory.setJpaVendorAdapter(adapter);
		
		return factory;
	}
	
	@Bean("transactionManager")
	public PlatformTransactionManager createTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(createEntityManagerFactory().getObject());
		return transactionManager;	
	}
	
	Properties hiberateProperties() {
		Properties property = new Properties();
		property.setProperty("hibernate.hbm2ddl.auto", "update");
		property.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		property.setProperty("hibernate.show_sql", "true");
		property.setProperty("hibernate.format_sql", "true");
		
		return property;
	}
}
