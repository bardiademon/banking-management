package com.projectuni.bankingmanagement.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.projectuni.bankingmanagement.model.repository")
@PropertySource("classpath:application.properties")
@ComponentScan({"com.projectuni.bankingmanagement" ,
        "com.projectuni.bankingmanagement.controller" ,
        "com.projectuni.bankingmanagement.model.service" ,
        "com.projectuni.bankingmanagement.model.repository"})
public class SpringConfig
{
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driver-class-name";
    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    private static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "spring.jpa.properties.hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "package.toscan";
    private static final String ENABLE_LAZY_LOAD_NO_TRANS = "hibernate.enable_lazy_load_no_trans";

    private final Environment env;

    private static ApplicationContext applicationContext;

    private static EntityManager entityManager;

    @Inject
    public SpringConfig(final Environment environment)
    {
        this.env = environment;
    }

    public static void config()
    {
        applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    }

    @Bean
    public DataSource dataSource()
    {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUsername(env.getRequiredProperty(SPRING_DATASOURCE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(SPRING_DATASOURCE_PASSWORD));
        dataSource.setUrl(env.getRequiredProperty(SPRING_DATASOURCE_URL));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
        entityManagerFactoryBean.setJpaProperties(hibProperties());
        return entityManagerFactoryBean;
    }

    private Properties hibProperties()
    {
        final Properties settings = new Properties();
        settings.put(org.hibernate.cfg.Environment.SHOW_SQL , env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        settings.put(org.hibernate.cfg.Environment.CURRENT_SESSION_CONTEXT_CLASS , "thread");
        settings.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO , "update");
        settings.put(org.hibernate.cfg.Environment.ENABLE_LAZY_LOAD_NO_TRANS , env.getProperty(ENABLE_LAZY_LOAD_NO_TRANS));
        settings.put("hibernate.dialect" , env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        settings.put(PROPERTY_NAME_HIBERNATE_DIALECT , env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        return settings;
    }

    @Bean
    public JpaTransactionManager transactionManager()
    {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        final EntityManagerFactory entityManagerFactory = transactionManager.getEntityManagerFactory();
        if (entityManagerFactory != null) entityManager = entityManagerFactory.createEntityManager();

        return transactionManager;
    }

    public static <T> T newInstance(Class<T> tClass)
    {
        return applicationContext.getBean(tClass);
    }

    public static <T> void destroyInstance(Class<T> tClass)
    {
        final ConfigurableBeanFactory autowireCapableBeanFactory = (ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        final String className = tClass.getSimpleName();
        autowireCapableBeanFactory.destroyBean(className , tClass);
    }

    public static EntityManager getEntityManager()
    {
        return entityManager;
    }
}
