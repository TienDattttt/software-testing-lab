package com.example.shopvnregisterswing.db;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DbConfig {

    @Value("${app.db.url}")
    private String url;

    @Value("${app.db.user}")
    private String user;

    @Value("${app.db.password}")
    private String password;

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
