package com.hepsiburada.etl.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

//    public HikariDataSource hikariDataSource() {
//        HikariConfig dataSourceConfig = new HikariConfig();
//        dataSourceConfig.setDriverClassName("org.postgresql.Driver");
//        dataSourceConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/data-db");
//        dataSourceConfig.setUsername("postgres");
//        dataSourceConfig.setPassword("123456");
//        dataSourceConfig.setAutoCommit(true);
//        return new HikariDataSource(dataSourceConfig);
//    }

}
