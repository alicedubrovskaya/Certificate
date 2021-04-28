package com.epam.esm.repository.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm.repository"})
public class RepositoryConfiguration {

    @Bean
    public DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/certificate_db?serverTimezone=Europe/Minsk");
        config.setUsername("root");
        config.setPassword("pass");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setPoolName("springHikariCP");
        return new HikariDataSource(config);
    }

//    @Bean
//    DataSource createDataSourceH2() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl("jdbc:h2:mem:certificate_db;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
//        config.setDriverClassName("org.h2.Driver");
//        config.setMaximumPoolSize(10);
//        config.setUsername("sa");
//        config.setPassword("");
//        return new HikariDataSource(config);
//
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("org.h2.Driver");
//        driverManagerDataSource.setUrl("jdbc:h2:mem:certificate_db");
//        driverManagerDataSource.setUsername("sa");
//        driverManagerDataSource.setPassword("");
//        return driverManagerDataSource;
//    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(createDataSource());
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(createDataSource());
        return transactionManager;
    }
}
