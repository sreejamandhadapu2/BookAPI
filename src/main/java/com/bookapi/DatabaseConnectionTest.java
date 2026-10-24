package com.bookapi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bookapi.config.DatabaseConfig;

public class DatabaseConnectionTest {
	public static void main(String args[]) {
		String query = "select * from Books";
		try(Connection connection = DatabaseConfig.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query)){
		while(rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String author = rs.getString("author");
			System.out.printf("ID: %d | Title: %s | Author: %s ", 
                    id, title, author);
}
		}
		catch(SQLException e){
			System.err.println("Failed to fetch books from the database!");
            e.printStackTrace();
		}
			
		}
}
