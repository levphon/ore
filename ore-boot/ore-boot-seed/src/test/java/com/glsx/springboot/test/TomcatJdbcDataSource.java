package com.glsx.springboot.test;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class TomcatJdbcDataSource 
{
    /*@PostConstruct
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource()
    {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }*/
    
   
    
}
