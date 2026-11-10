package com.sa.ui;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
 * db configuration. 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
    @Bean(name = "db")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource1() {
     return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate1(@Qualifier("db") DataSource ds) {
     return new JdbcTemplate(ds);
    }

    // hashes user passwords (user.password holds a 60-char BCrypt hash)
    @Bean
    public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
    }

}
