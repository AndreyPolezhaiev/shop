package com.polezhaiev.shop.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.polezhaiev.shop")
public class AppConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(
                environment.getProperty("spring.datasource.driverClassName"));
        basicDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        basicDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        basicDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return basicDataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());

        Properties properties = new Properties();
        properties.setProperty("show-sql", environment.getProperty("spring.jpa.show-sql"));
        properties.setProperty("hibernate.hbm2ddl.auto",
                environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.setPackagesToScan("com.polezhaiev.shop.model");

        return localSessionFactoryBean;
    }
}
