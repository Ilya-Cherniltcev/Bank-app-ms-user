package com.org.bankmsuser.configuration;


import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


@TestConfiguration
public class TestLiquibaseConfig {

    @Bean
    @Primary
    public SpringLiquibase liquibase() {
        return new SpringLiquibase() {
            @Override
            public void afterPropertiesSet() {
                // Mock: nothing to do))
            }
        };
    }
}