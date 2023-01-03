package com.edu.pos.sub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.edu.pos.login.LoginMain;
import com.edu.pos.model.TotalRecordDAO;
import com.edu.pos.model.ShopDAO;
import com.edu.pos.model.TotalDAO;
import com.edu.pos.regist.RegistMain;

public class SubMain extends JFrame {
	JMenuBar menuBar;
	JMenu menu_pos;
	JMenuItem m_regist;
	JMenuItem m_pos;
	
	JPanel p_west;
	JPanel p_center;
	
	SubLabel[] labelList=new SubLabel[6];
	String[] labelName={
		"자료입력", "재고관리", "포스용",
		"보고서", "고객관리", "시스템관리"};
	
//	SubLabel subLabel;
	
	
	JTable table;
	JScrollPane scroll;
	
	// 전환되어질 페이지 중 첫 메인 패널
	JPanel[] pages=new JPanel[3];
	
	public static final int POSPAGE=0;
	public static final int REMAINPAGE=1;
	public static final int RECORDPAGE=2;
	
	//** RegistMain에서 입력된 테이블을 RemainPage로 가져와야 하기 때문에 한번 품어볼께
	RegistMain registMain;
	LoginMain loginMian;
	
	/* 지금 포스페이지에서 이사옴  --> 얘가 재고페이지에도 연결되어ㅑ 하기 때문*/
	public ShopDAO shopDAO;
	public TotalRecordDAO totalRecordDAO;
	TotalDAO totalDAO;
	// t생성자에서 new 해주면 public 으로 갈 필요 없음!
	
	public SubMain() {
		//상단 메뉴바
		createMenuBar();
		shopDAO=new ShopDAO();
		totalDAO=new TotalDAO();
		totalRecordDAO=new TotalRecordDAO();
		
		setTitle("중앙정보처리 Ryuyeseo.1 이대점 - DOLCHVITA (본사)");
		// 포스 페이지에서 고정되어 있는 메뉴 패널
		p_west=new JPanel();
		p_west.setBackground(Color.GRAY);
		p_west.setPreferredSize(new Dimension(165,750));
		
		p_center=new JPanel();
		
		//pages[SUBPAGE]=new SubPage(this);
		pages[POSPAGE]=new PosPage(this);
		pages[REMAINPAGE]=new RemainPage(this);
		pages[RECORDPAGE]=new RecordPage(this);
		
		for(int i=0; i<pages.length; i++) {
			p_center.add(pages[i]);
		}
		
		
		createMenuLabel();
		overRoll(1);
		
		//화면 부착
		add(p_west, BorderLayout.WEST);
		add(p_center);
		
		setSize(1000,750);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		showHide(REMAINPAGE);
		
		
		m_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registMain=new RegistMain();
			}
		});
		m_pos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHide(POSPAGE);
			}
		});
	}

	
	
	
	public void overRoll(int n) {
		for(int i=0; i<labelList.length; i++) {
			if(i==n) {
				labelList[i].setBackground(Color.YELLOW);
				labelList[i].setOpaque(true);
			}else {
				labelList[i].setBackground(Color.WHITE);
				labelList[i].setOpaque(true);
			}
		}
	}
	
	
	
	public void createMenuLabel() {
		
		for(int i=0; i<labelName.length; i++) {
			
			labelList[i]=new SubLabel(this, labelName[i], i);
			p_west.add(labelList[i]);
		}
	}
	
	
	public void createMenuBar() {
		menuBar=new JMenuBar();
		//첫 번째 메뉴
		menu_pos=new JMenu("자료입력");
		m_regist=new JMenuItem("구매자료");
		menu_pos.add(m_regist);
		m_pos=new JMenuItem("포스판매");
		menu_pos.add(m_pos);
		
		// 메뉴바 조립
		menuBar.add(menu_pos);
		menuBar.add(new JMenu("기초자료"));
		menuBar.add(new JMenu("고객관리"));
		menuBar.add(new JMenu("포스용출력물"));
		menuBar.add(new JMenu("구매판매용"));
		menuBar.add(new JMenu("사무실"));
		menuBar.add(new JMenu("보고서"));
		menuBar.add(new JMenu("재고관리"));
		menuBar.add(new JMenu("자료관리"));
		menuBar.add(new JMenu("보조업무"));
		menuBar.add(new JMenu("시스템관리"));
		setJMenuBar(menuBar);
		
	}
	
	
	public void showHide(int n) {
		for(int i=0; i<pages.length; i++) {
			if(i==n) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
	}

	
	public static void main(String[] args) {
		new SubMain();
	}
}
