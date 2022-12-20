package com.farmu.MsResizeImage.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfig {
    
    @Bean(name = "dsResizeImage")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceOne() {
        return DataSourceBuilder.create().build();
    }
    
    @Primary
    @Bean(name = "JdbcTemplateResizeImage")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dsResizeImage") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
    
}