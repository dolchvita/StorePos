package com.edu.pos.regist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.edu.pos.model.Shop;
import com.edu.pos.model.ShopDAO;
import com.edu.pos.model.ShopModel;
import com.edu.pos.model.VenderDAO;
import com.edu.pos.sub.RemainPage;

// 상품을 입력할 페이지
public class RegistMain extends JFrame{

	//커스텀 패널들
	Panel[] panels=new Panel[3];
	
	JTable table;
	JScrollPane scroll;
	ShopModel model;
	
	VenderDAO venderDAO=new VenderDAO();
	public ShopDAO shopDAO=new ShopDAO();
	
	public static final int REGISTPANEL=0;
	public static final int BUTTONPANEL=1;
	public static final int WESTPANEL=2;
	
	// 테이블 선택하면 해당 idx의 shop이 담긴다!
	Shop shop;
	
	public RegistMain() {
		
		panels[REGISTPANEL]=new RegistPanel(this);
		panels[BUTTONPANEL]=new ButtonPanel(this);
		panels[WESTPANEL]=new WestPanel(this);
		
		table=new JTable(model=new ShopModel(this));
		scroll=new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		
		// 커스텀 패널들 부착
		add(panels[0], BorderLayout.NORTH);
		add(panels[1], BorderLayout.SOUTH);
		add(panels[2],  BorderLayout.WEST);
		add(scroll);
		
		setTitle("전표 입력");
		setSize(1000,750);
		setVisible(true);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 1-3) 선택한 테이블 row에 No 컬럼 값 가져오기 --> select 한건 가져오기 위해 값 넘겨줄 것
				String shop_idx=(String)table.getValueAt(table.getSelectedRow(), 0);
				
				ButtonPanel btPanel=(ButtonPanel)panels[BUTTONPANEL];
				btPanel.getDetail(Integer.parseInt(shop_idx));
				
				int row=table.getSelectedRow();
			}
		});
	}
	
	public void showLight() {
		for(int i=0; i<table.getRowCount(); i++) {
			
			if(table.isBackgroundSet()) {
				table.setBackground(Color.PINK);
			}
		}
		
	}
	
	//shop 테이블 조회
	public void getList() {
		model.shopList=shopDAO.selectAll();
		table.updateUI();
	}

	public static void main(String[] args) {
		new RegistMain();
	}
	
}
