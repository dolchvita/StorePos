package com.edu.pos.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.edu.pos.regist.RegistMain;

public class ShopModel extends AbstractTableModel{
	public List<Shop> shopList=new ArrayList<Shop>();
	
	String[] column= {"No", "거래처", "상품명", "소비자가", "바코드", "일자"};

	RegistMain registMain;
	
	public ShopModel(RegistMain registMain) {
		this.registMain=registMain;
		
		// DAO에서 바로 받을 수 없는 이유 --> DAO 자체가 메인에서 얻은 DTO를 받고 있기 떄문
		// 차라리 메인을 상속받고 거기서 DAO를 가져오자
		shopList=registMain.shopDAO.selectAll();
		
	}

	
	public int getRowCount() {

		return shopList.size();
	}
	

	public int getColumnCount() {
		return 6;
	}


	public Object getValueAt(int row, int col) {
		Shop shop=shopList.get(row);
		String value=null;
		
		switch(col) {
		case 0: value=Integer.toString(shop.getShop_idx());break;
		case 1: value=shop.getVender().getVender_name();break;
		case 2: value=shop.getShop_name();break;
		case 3: value=Integer.toString(shop.getPrice());break;
		case 4: value=shop.getCode();break;
		case 5: value=shop.getRegdate();break;
		
		}
		
		return value;
	}
	

	public String getColumnName(int col) {
		return column[col];
	}

}
