package com.edu.pos.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TotalModel extends AbstractTableModel{
	// DTO가 담긴 ArrayList를 멤버로 가지고 있어야 한다
	TotalDAO totalDAO=new TotalDAO();
	ArrayList<Total> totalList=new ArrayList<Total>();
	
	String[] column= {"no","상품명", "가격"};
	
	public TotalModel() {
		totalList=(ArrayList)totalDAO.selectAll();
		
	}
	
	public int getRowCount() {
		return totalList.size();
	}

	public int getColumnCount() {
		return 3;
	}

	
	public Object getValueAt(int row, int col) {
		Total total=totalList.get(row);
		String value=null;
		
		switch(col) {
			case 0: value=Integer.toString(total.getTotal_idx());break;
			case 1: value=total.getName();break;
			case 2: value=Integer.toString(total.getTotalcash());break;
		}
		
		return value;
	}

	
	
	public String getColumnName(int col) {		
		return column[col];
	}
	
}
