package com.letussolve.dao;

public class DBConfig {
	
	protected static final String DB_DRIVER;
	protected static final String DB_HOST;
	protected static final String DB_USERNAME;
	protected static final String DB_PASSWORD;
	protected static final String DB_CONN_STRING;
	protected static final String DB_NAME;
	
	static {
		DB_DRIVER = "com.mysql.jdbc.Driver";
		DB_HOST = "jdbc:mysql://localhost:3306/";
		DB_NAME = "letussolve";
		DB_USERNAME = "root";
		DB_PASSWORD = "";
		DB_CONN_STRING = DB_HOST + DB_NAME;
	}
	
 }
