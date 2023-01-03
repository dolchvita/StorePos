package com.edu.pos.regist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.edu.pos.model.Vender;
import com.edu.pos.model.VenderModel;

public class WestPanel extends Panel{
	JPanel p_west;
	JPanel p_top;
	JPanel p_south;
	
	//top 패널
	JComboBox box_top;		// 거래처 이름 목록
	JLabel la_top;
	
	//south 패널
	JButton bt_store;
	JButton bt_product;
	
	// 센터
	JTable table;
	JScrollPane scroll;
	VenderModel model;
	
	// 거래처 선택시 pk저장
//	List<Integer> vederIdx=new ArrayList<Integer>();
	int venderIdx;
	
	//버튼 패널에 모두 접근할 수 있게 멤버변수로 지정 (정보 출력)
	ButtonPanel buttonpanel;
	
	public WestPanel(RegistMain registMain) {
		super(registMain);
		
		p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		p_west.setBackground(Color.RED);
		p_west.setPreferredSize(new Dimension(180,420));
	
	
		p_top=new JPanel();
		p_south=new JPanel();
		
		// 디자인
		Dimension d=new Dimension(180,50);
		p_top.setBackground(Color.GRAY);
		p_top.setPreferredSize(d);
		p_south.setBackground(Color.YELLOW);
		p_south.setPreferredSize(d);
		
		
		// top 패널 영역
		la_top=new JLabel("정렬");
		box_top=new JComboBox();
		//디자인
		Font font=new Font("Dotum", Font.BOLD, 15);
		la_top.setFont(font);
		box_top.setPreferredSize(new Dimension(100,30));
		box_top.addItem("전표 NO");

		p_top.add(la_top);
		p_top.add(box_top);
		
		
		// south 패널 영역
		bt_store=new JButton("거래처");
		bt_product=new JButton("상품");
		Dimension d2=new Dimension(80,35);
		bt_store.setPreferredSize(d2);
		bt_product.setPreferredSize(d2);
		
		p_south.add(bt_store);
		p_south.add(bt_product);
		
		
		// 센터영역
		table=new JTable(model=new VenderModel());
		scroll=new JScrollPane(table);
		
		
		p_west.add(scroll);
		p_west.add(p_top, BorderLayout.NORTH);
		p_west.add(p_south, BorderLayout.SOUTH);
		
		add(p_west);
		
		getVenderList();
		
		// 거래처 콤보박스 이벤트
		box_top.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {

					// 변경시 텍스트필드에 출력
					buttonpanel=(ButtonPanel)registMain.panels[registMain.BUTTONPANEL];
					buttonpanel.t_vender.setText((String)e.getItem());
					
					// DAO에게 전달
					venderIdx=registMain.venderDAO.getVenderIdx((String)e.getItem());

					//pk저장
					//vederIdx.add(venderIdx);
				}
				
			}
		});
		
		bt_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 상품 입력할 수 있게 reset
				buttonpanel=(ButtonPanel)registMain.panels[registMain.BUTTONPANEL];
				buttonpanel.reset();
			}
		});

		bt_store.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 이 버튼을 누르면 리셋된 거래처 텍스트필드에 콤보박스에 선택된 거래처 이름 출력
				String vender_name=(String)box_top.getSelectedItem();
				buttonpanel.t_vender.setText(vender_name);
			}
		});
		
	}

	public void printVender() {

		
	}
	
	
	
	// 콤보박스에 거래처 채워넣기
	public void getVenderList() {
		List<Vender> venderList=registMain.venderDAO.selectAll();
		
		for(Vender vender:venderList) {
			box_top.addItem(vender.getVender_name());
		}
		
		model.list=venderList;	
	}
	
	
	
	
}
