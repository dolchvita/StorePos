package com.edu.pos.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class VenderModel extends AbstractTableModel{
	String[] column= {"No", "거래처"};
	public List<Vender> list=new ArrayList<Vender>();
	
	
	// list에 담겨진 DTO의 개수만큼 (DAO 가져와서 직접 대입하자)

	public int getRowCount() {
		return list.size();
	}


	public int getColumnCount() {
		return 2;
	}


	public Object getValueAt(int row, int col) {
		Vender vender=list.get(row);
		String value=null;
		
		switch(col) {
		case 0: value=Integer.toString(vender.getVender_idx());break;
		case 1: value=vender.getVender_name();break;
		
		}
		
		return value;
	}

	

	public String getColumnName(int col) {
		return column[col];
	}
	
}
