package com.edu.pos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	private static DBManager instance;
	private Connection connection;
	
	private DBManager() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
	
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="javase";
			String pass="1234";
			connection=DriverManager.getConnection(url, user, pass);
			
			if(connection!=null) {
				System.out.println("오라클 접속");
			}
					
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static DBManager getInstance() {
		if(instance==null) {
			instance=new DBManager();
		}
		return instance;
	}
	
	
	public Connection getConnection() {
		return connection;
	}
	
	
	public void release(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void release(ResultSet rs, PreparedStatement pstmt) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DBManager instance=DBManager.getInstance();
		
		
	}
	
	
}
