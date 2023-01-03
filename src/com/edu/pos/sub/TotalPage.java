package com.edu.pos.sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.edu.pos.model.TotalModel;
import com.edu.pos.model.TotalRecord;

public class TotalPage extends JFrame{

	JTable table;
	JScrollPane scroll;
	JTextField t_total;
	JButton bt_total;
	JButton bt_record;
	
	TotalModel model;
	
	// 화면의 보이기 요소 제어
	boolean flag=true;
	
	int total; 		//정산누르면 총 담기게 되는 값
	int row;			// 정산 테이블의 레코드 수
	
	// 얘가 탄생되는 곳을 일단 생성자에서 받아보자 --> 메인에 접근하기 위함 --> recordDAO가져오려고
	EastPanel eastPanel;
	
	public TotalPage(EastPanel eastPanel) {
		this.eastPanel=eastPanel;
		setTitle("2022-12-30 Dolchvita 정산내역");
		
		setLayout(new FlowLayout());	
		
		table=new JTable(model=new TotalModel());
		scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(380,350));
		
		t_total=new JTextField();
		t_total.setPreferredSize(new Dimension(300,55));
		t_total.setFont(new Font("Verdana", Font.BOLD, 30));
		bt_total=new JButton("정산하기");
		bt_record=new JButton("마감");
		bt_record.setBackground(Color.MAGENTA);
		
		add(scroll);
		add(t_total);
		add(bt_total);
		add(bt_record);
		
		setVisible(flag);
		setSize(400,500);
		setLocationRelativeTo(null);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				flag=!flag;
			}
		});
		
		bt_total.addActionListener(new ActionListener() {		// 정산하기
			public void actionPerformed(ActionEvent e) {
				sumPrice();
			}
		});
		
		bt_record.addActionListener(new ActionListener() {		//마감
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(TotalPage.this, "마감 하시겠습니까?")==JOptionPane.OK_OPTION){
					createTotalRecord();
					deletTotal();
				}
			}
		});
	}
	
	
	public void createTotalRecord() {
		TotalRecord record=new TotalRecord();
		record.setTotalnum(row);
		record.setTotalprice(total);

		int result=eastPanel.posPage.subMain.totalRecordDAO.insert(record);
		if(result>0) {
			JOptionPane.showMessageDialog(TotalPage.this, "마감되었습니다");
		}
	}
	
	
	public void deletTotal() {
		
		int result=eastPanel.posPage.subMain.totalDAO.delete();
		if(result>0){
			JOptionPane.showMessageDialog(TotalPage.this, "오늘도 수고하셨습니다");
			table.updateUI();
		}
	}
	
	
	public void sumPrice() {
		row=table.getRowCount();
		
		for(int i=0; i<row; i++) {
			String price=(String)table.getValueAt(i, 2);
			int cash=Integer.parseInt(price);
		
			total=total+cash;
		}
		t_total.setText(Integer.toString(total));
	}
	
}
