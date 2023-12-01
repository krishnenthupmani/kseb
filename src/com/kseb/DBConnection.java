package com.kseb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	final String driver="com.mysql.cj.jdbc.Driver";
	final String url="jdbc:mysql://localhost:3306/kseb";
	final String user="root";
	final String password="mysql";
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pst2 = null;
	ResultSet rs2 = null;
	
	public Connection getConnection(){
		try{
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return con;
		
	}
}
