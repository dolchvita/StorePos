package com.edu.pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.pos.util.DBManager;

public class TotalRecordDAO {
	DBManager dbManager=DBManager.getInstance();
	
	
	public int insert(TotalRecord record) {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="insert into totalrecord(totalrecord_idx, totalnum, totalprice)";
		sql+=" values(seq_totalrecord.nextval, ?,?)";
		
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, record.getTotalnum());
			pstmt.setInt(2, record.getTotalprice());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);			
		}
		return result;
	}
	
	
	
	public List selectAll() {
		List<TotalRecord> list=new ArrayList<TotalRecord>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select * from totalrecord order by totalrecord_idx asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TotalRecord record=new TotalRecord();
				
				record.setTotalrecord_idx(rs.getInt("totalrecord_idx"));
				record.setRegdate(rs.getString("regdate"));
				record.setTotalnum(rs.getInt("totalnum"));
				record.setTotalprice(rs.getInt("totalprice"));
				
				list.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return list;
	}
	
	
}
