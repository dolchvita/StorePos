package com.edu.pos.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.edu.pos.sub.RemainPage;

public class StockModel extends AbstractTableModel{
	public List<Shop> shopList=new ArrayList<Shop>();
	
	String[] column= {"거래처", "상품명", "소비자가", "바코드", "재고수"};

	RemainPage remainPage;
	
	public StockModel(RemainPage remainPage) {
		this.remainPage=remainPage;
		
		// DAO에서 바로 받을 수 없는 이유 --> DAO 자체가 메인에서 얻은 DTO를 받고 있기 떄문
		// 차라리 메인을 상속받고 거기서 DAO를 가져오자
		shopList=remainPage.subMain.shopDAO.selectCount();
		
	}

	
	public int getRowCount() {
		return shopList.size();
	}
	

	public int getColumnCount() {
		return 5;
	}


	public Object getValueAt(int row, int col) {
		Shop shop=shopList.get(row);
		String value=null;
		
		switch(col) {
		case 0: value=shop.getVender().getVender_name();break;
		case 1: value=shop.getShop_name();break;
		case 2: value=Integer.toString(shop.getPrice());break;
		case 3: value=shop.getCode();break;
		case 4: value=Integer.toString(shop.getCount());break;
		}
		
		return value;
	}
	

	public String getColumnName(int col) {
		return column[col];
	}

}
