package com.bookapi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
	
	private static final String url = "url";
	private static final String user = "user";
	private static final String password = "pass";
	
	

public static Connection getConnection() throws SQLException {

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException(
            "MySQL JDBC driver is not available to Tomcat.", e);
    }

    return DriverManager.getConnection(
        url, user, password);
}


}
