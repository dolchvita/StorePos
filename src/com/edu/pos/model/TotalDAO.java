package com.edu.pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.pos.util.DBManager;

public class TotalDAO {
	
	DBManager dbManager=DBManager.getInstance();
	
	
	public int insert(Total total) {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		 String sql="insert into total(total_idx, name, totalcash)";
		 sql+=" values(seq_total.nextval,?,?)";
		 
		 System.out.println("실행되었나요?");
		 
		 try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, total.getName());
			pstmt.setInt(2, total.getTotalcash());
		
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	
	// 모든 컬럼 가져오기
	public List selectAll() {
		List list=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select * from total order by total_idx asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			list=new ArrayList();
			
			while(rs.next()) {
				
				Total total=new Total();
				total.setTotal_idx(rs.getInt("total_idx"));
				total.setName(rs.getString("name"));
				total.setTotalcash(rs.getInt("totalcash"));
				
				list.add(total);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}	
		return list;
	}
	
	
	
	// 레코드 모두 지우기
	public int delete() {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="delete from total";
		
		try {
			pstmt=con.prepareStatement(sql);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	
}
