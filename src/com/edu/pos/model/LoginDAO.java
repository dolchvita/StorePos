package com.edu.pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.edu.pos.util.DBManager;
import com.edu.pos.util.StringUtil;

public class LoginDAO {

	DBManager dbManager=DBManager.getInstance();
	StringUtil stringUtil; 
	
	// 로그인 조건문
	public Login select(String id, String pass) {
		Login login=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select * from login where id=? and pass=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2,pass);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				login=new Login();
				
				login.setLogin_idx(rs.getInt("login_idx"));
				login.setId(rs.getString("id"));
				login.setPass(rs.getString("pass"));
				login.setRegdate(rs.getString("regdate"));
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return login;
	}
	
	
	
	public int update(String id, String pass) {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="update login set id=?, pass=? where login_idx=1";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			dbManager.release(pstmt);
		}
		
		return result;
	}
	
	
	
	
}
