package com.loginapp.DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {
	
	
	public static Connection dbconnect() {
		Connection con= null;
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/apple","root","sameer@123");
			
			 
			 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return con;
	}

	public static void main(String[] args) {
		DB_connection.dbconnect();

	}

}
