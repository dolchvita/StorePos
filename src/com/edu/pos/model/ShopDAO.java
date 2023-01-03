package com.edu.pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.pos.util.DBManager;

public class ShopDAO {
	DBManager dbManager=DBManager.getInstance();
	
	// 등록하기
	public int insert(Shop shop) {
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		int result=0;
		
		String sql="insert into shop(shop_idx, vender_idx, shop_name, price, code)";
		sql+=" values(seq_shop.nextval, ?,?,?,?)";
		
		try {
			// 이걸 빼먹었네
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, shop.getVender().getVender_idx());
			pstmt.setString(2, shop.getShop_name());
			pstmt.setInt(3, shop.getPrice());
			pstmt.setString(4, shop.getCode());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	// 모든 항목 조회하기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		List<Shop> list=new ArrayList<Shop>();
		//조인 테이블 가져오기

		String sql="select shop_idx, vender_name, shop_name, price, code, regdate";
		sql+=" from vender v, shop s";
		sql+=" where v.vender_idx=s.vender_idx";
		sql+=" order by vender_name desc, shop_name asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Shop shop=new Shop();
				
				Vender vender=new Vender();
				shop.setVender(vender);
				
				shop.setShop_idx(rs.getInt("shop_idx"));
				vender.setVender_name(rs.getString("vender_name"));
				shop.setShop_name(rs.getString("shop_name"));
				shop.setPrice(rs.getInt("price"));
				shop.setCode(rs.getString("code"));
				shop.setRegdate(rs.getString("regdate"));
				
				list.add(shop);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		
		return list;
	}
	
	
	
	// 한 건 가져오기 --> 조인하자 어차피 브랜드명 필요해
	// vender를 포함하는 shop을 생성해서 반환하자 -- 그게 훨씬 편할 것임
	public Shop select(int shop_idx) {
		Shop shop=null;	// 무조건 초기값 명시하기
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select v.vender_idx, vender_name, shop_idx, shop_name, price, code, regdate";
		sql+=" from vender v, shop s";
		sql+=" where v.vender_idx=s.vender_idx and shop_idx=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, shop_idx);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				shop=new Shop();
				Vender vender=new Vender();
				shop.setVender(vender);

				vender.setVender_idx(rs.getInt("vender_idx"));
				vender.setVender_name(rs.getString("vender_name"));
				shop.setShop_idx(rs.getInt("shop_idx"));
				shop.setShop_name(rs.getString("shop_name"));
				shop.setPrice(rs.getInt("price"));
				shop.setCode(rs.getString("code"));
				shop.setRegdate(rs.getString("regdate"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return shop;
	}
	
	
	
	// 검색기능을 위한 조회문 (OverLoading)
	public Shop select(String code) {
		Shop shop=null;	// 무조건 초기값 명시하기
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select v.vender_idx, vender_name, shop_idx, shop_name, price, code, regdate";
		sql+=" from vender v, shop s";
		sql+=" where v.vender_idx=s.vender_idx and code=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, code);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				shop=new Shop();
				Vender vender=new Vender();
				shop.setVender(vender);

				vender.setVender_idx(rs.getInt("vender_idx"));
				vender.setVender_name(rs.getString("vender_name"));
				shop.setShop_idx(rs.getInt("shop_idx"));
				shop.setShop_name(rs.getString("shop_name"));
				shop.setPrice(rs.getInt("price"));
				shop.setCode(rs.getString("code"));
				shop.setRegdate(rs.getString("regdate"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return shop;
	}
	
	
	// 재고페이지에 보여줄 테이블
	public List selectCount() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		List<Shop> list=new ArrayList<Shop>();
		//조인 테이블 가져오기

		StringBuilder sb=new StringBuilder();
		sb.append("select vender_name, shop_name ,price, code, count(shop_name) as cnt");
		sb.append(" from vender v left outer join shop s");
		sb.append(" on v.vender_idx =s.vender_idx");
		sb.append(" group by vender_name, shop_name, price, code");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Shop shop=new Shop();
				
				Vender vender=new Vender();
				shop.setVender(vender);
				
				vender.setVender_name(rs.getString("vender_name"));
				shop.setShop_name(rs.getString("shop_name"));
				shop.setPrice(rs.getInt("price"));
				shop.setCode(rs.getString("code"));
				shop.setCount(rs.getInt("cnt"));
				
				list.add(shop);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return list;
	}
	
	// 오더바이로 보여줌
	public List orderBy() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		List<Shop> list=new ArrayList<Shop>();
		//조인 테이블 가져오기
		
		StringBuilder sb=new StringBuilder();
		sb.append("select vender_name, shop_name ,price, code, count(shop_name) as cnt");
		sb.append(" from vender v left outer join shop s");
		sb.append(" on v.vender_idx =s.vender_idx");
		sb.append(" group by vender_name, shop_name, price, code");
		sb.append(" order by cnt asc");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Shop shop=new Shop();
				
				Vender vender=new Vender();
				shop.setVender(vender);
				
				vender.setVender_name(rs.getString("vender_name"));
				shop.setShop_name(rs.getString("shop_name"));
				shop.setPrice(rs.getInt("price"));
				shop.setCode(rs.getString("code"));
				shop.setCount(rs.getInt("cnt"));
				
				list.add(shop);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return list;
	}
	
	
	// 해당 상품 개수 조회하기
	public Shop count(String code) {
		Shop shop=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		con=dbManager.getConnection();
		
		String sql="select count(*) as cnt from shop where code=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, code);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				shop=new Shop();
				shop.setCount(rs.getInt("cnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(rs, pstmt);
		}
		return shop;
	}
	
	
	// 한 건 수정하기
	public int update(Shop shop) {
		// 1-2) 전달받은 DTO를 대상으로 쿼리문 실행
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="update shop set shop_name=?, price=?, code=? where shop_idx=?";
		
		try {
			pstmt=con.prepareStatement(sql);
		
			pstmt.setString(1, shop.getShop_name());
			pstmt.setInt(2, shop.getPrice());
			pstmt.setString(3, shop.getCode());
			pstmt.setInt(4, shop.getShop_idx());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	
	// 레코드 한 건 삭제하기
	public int delete(int shop_idx) {
		int result=0;
		Connection con=null;
		PreparedStatement pstmt=null;
		con=dbManager.getConnection();
		
		String sql="delete from shop where shop_idx=?";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, shop_idx);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		
		return result;
	}

	
}
