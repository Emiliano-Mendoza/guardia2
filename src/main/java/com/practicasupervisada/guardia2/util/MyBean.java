package com.practicasupervisada.guardia2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${spring.jpa.databasePlatform}")
    private String dbPlataform;
    
    @Value("${spring.datasource.driverClassName}")
    private String dbDriveClassName;

}