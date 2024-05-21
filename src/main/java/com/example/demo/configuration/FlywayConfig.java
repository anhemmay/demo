package com.example.demo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class FlywayConfig {

    @Value("${spring.flyway.locations}")
    private String[] flywayLocations;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Bean
    public Flyway flyway(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .locations(flywayLocations)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load();
        flyway.migrate(); // migrate if newest version exists

        log.warn("migrating");
        return flyway;
    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(dataSourceUrl);
        driverManagerDataSource.setUsername(dataSourceUsername);
        driverManagerDataSource.setPassword(dataSourcePassword);
        return driverManagerDataSource;
    }
}
