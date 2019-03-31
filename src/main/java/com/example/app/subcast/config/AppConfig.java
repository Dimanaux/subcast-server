package com.example.app.subcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@EnableWebMvc
@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public DataSource configureDataSource() {
        return new DriverManagerDataSource(
                "jdbc:postgresql://localhost:5432/subcast",
                "postgres",
                "postgres"
        );
    }
}