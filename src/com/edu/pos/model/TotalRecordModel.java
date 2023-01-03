package com.edu.pos.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.edu.pos.sub.RecordPage;

public class TotalRecordModel extends AbstractTableModel{
	public List<TotalRecord> totalRecordList=new ArrayList<TotalRecord>();
	
	String[] column= {"날짜", "총 판매수", "총 판매액"};

	RecordPage recordPage;
	
	public TotalRecordModel(RecordPage recordPage) {
		this.recordPage=recordPage;
		
		totalRecordList=recordPage.subMain.totalRecordDAO.selectAll();
	}

	
	public int getRowCount() {
		return totalRecordList.size();
	}
	

	public int getColumnCount() {
		return 3;
	}


	public Object getValueAt(int row, int col) {
		TotalRecord record=totalRecordList.get(row);
		String value=null;
		
		switch(col) {
		case 0: value=record.getRegdate();break;
		case 1: value=Integer.toString(record.getTotalnum());break;
		case 2: value=Integer.toString(record.getTotalprice());break;
		}
		
		return value;
	}
	

	public String getColumnName(int col) {
		return column[col];
	}

}
