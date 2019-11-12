package com.wallet.rpc;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@Configuration
public class TestDatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver_class_name;


    @Bean
    public DataSource dataSource() {
        final DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driver_class_name);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setTimeBetweenEvictionRunsMillis(30000);
        dataSource.setMinEvictableIdleTimeMillis(60000);
        dataSource.setSuspectTimeout(600);
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

}
