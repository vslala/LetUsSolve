package com.letussolve.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBConnect extends DBConfig {
	protected Connection con;
	protected PreparedStatement stmt;
	protected DBConnect(){
		final String DB_INIT = "Connection to database has been created successfully!";
		try{
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_CONN_STRING, DB_USERNAME, DB_PASSWORD);
			System.out.println(DB_INIT);
		}catch (SQLException e) {
			System.out.println("There was problem connecting to the database");
		} catch (ClassNotFoundException e) {
			System.out.println("Database Connection error! Please Try Again!!");
			e.printStackTrace();
		}catch (Exception e){
			
		}
	}
}
