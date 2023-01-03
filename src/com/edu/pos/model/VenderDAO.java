package com.edu.pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.pos.util.DBManager;

public class VenderDAO {

	DBManager dbManager=DBManager.getInstance();
	ArrayList<Vender> list=new ArrayList<Vender>();
	
	
	

	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select * from vender order by vender_idx asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			// DTO 담기
			while(rs.next()) {
				Vender vender=new Vender();
				
				vender.setVender_idx(rs.getInt("vender_idx"));
				vender.setVender_name(rs.getString("vender_name"));
				
				list.add(vender);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return list;	
	}
	
	
	
	//vender 한 건 가져오기
	public Vender select(int vende_idx) {
		Vender vender=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select * from vender where vender_idx=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, vende_idx);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				vender.setVender_idx(rs.getInt("vender_idx"));
				vender.setVender_name(rs.getString("vender_name"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return vender;
	}
	
	
	
	//카테고리 이름으로 pk값 반환받기
	public int getVenderIdx(String venderName) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		int vender_idx=0;
		
		String sql="select vender_idx from vender";
		sql+=" where vender_name=?";
		
		
		try {
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, venderName);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				vender_idx=rs.getInt("vender_idx");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		
		return vender_idx;
	}
	
	
	
}
