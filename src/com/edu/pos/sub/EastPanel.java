package com.edu.pos.sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.edu.pos.model.Shop;
import com.edu.pos.model.Total;
import com.edu.pos.model.TotalDAO;

public class EastPanel extends Panel implements ActionListener{
	JTextField t_code;
	JPanel p_num;
	JPanel p_form;
	JPanel p_push;
	
	JButton[] btn=new JButton[12];
	String[] btName= {"7","8","9","4","5","6","1","2","3",
			"0","00","000"};

	JButton[] btn2=new JButton[4];
	String[] btName2= {"현금","취소","정산","판매"};
	
	// p_form영역
	JTextField t_money;
	JTextField t_cash;
	JTextField t_balance;
	JLabel la_money;
	JLabel la_cash;
	JLabel la_balance;
	JButton bt_minus;
	
	Row row;
	int index=1;
	Shop shop;

	String read;

	String str;		//텍스트필드에 입력된 값
	JTextField t_field;
	
	int sumSal;		//바코드 입력시 계산되는 값(t_money)
	String money_result;	// 계산액에 표시되는 값
	
	// 바코드에 찍힌 모든 row에 담긴 DTO처리하기 위한 배열
	ArrayList<Shop> rowList=new ArrayList<Shop>();
	
	//TotalDAO totalDAO;
	TotalPage totalPage;
	
	public EastPanel(PosPage posPage) {
		super(posPage);
		//totalDAO=new TotalDAO();
		
		t_code=new JTextField();
		p_num=new JPanel();
		p_form=new JPanel();
		p_push=new JPanel();
		
		p_num.setPreferredSize(new Dimension(230,170));
		Dimension d=new Dimension(230,160);
		p_form.setPreferredSize(d);
		p_push.setPreferredSize(d);
		
		t_code.setPreferredSize(new Dimension(230,50));
		Font font=new Font("Dotum", Font.BOLD, 16);
		t_code.setFont(font);
		
		//p_num 영역
		p_num.setLayout(new GridLayout(4,3));
		for(int i=0; i<btn.length; i++) {
			btn[i]=new JButton(btName[i]);
			
			p_num.add(btn[i]);
			
			// 이벤트 연결
			btn[i].addActionListener(this);
		}
		
		p_push.setLayout(new GridLayout(2, 2));
		
		for(int i=0; i<btn2.length; i++) {
			btn2[i]=new JButton(btName2[i]);
			btn2[i].setBackground(Color.PINK);
			btn2[i].setFont(font);
			
			btn2[i].addActionListener(this);
			
			p_push.add(btn2[i]);
		}
		
		//p_form 영역		
		Dimension d_la=new Dimension(50,33);
		la_money=new JLabel("계산액");
		la_cash=new JLabel("받은돈");
		la_balance=new JLabel("거스름");
		la_money.setPreferredSize(d_la);
		la_cash.setPreferredSize(d_la);
		la_balance.setPreferredSize(d_la);
		
		t_money=new JTextField();		
		t_cash=new JTextField();
		t_balance=new JTextField();
		bt_minus=new JButton("계산하기");
		
		Dimension d_t=new Dimension(130,28);
		t_money.setPreferredSize(d_t);
		t_cash.setPreferredSize(d_t);
		t_balance.setPreferredSize(d_t);

		p_form.add(la_money);
		p_form.add(t_money);
		p_form.add(la_cash);
		p_form.add(t_cash);
		p_form.add(la_balance);
		p_form.add(t_balance);
		p_form.add(bt_minus);
		
		
		// 화면 부착
		add(t_code);
		add(p_num);
		add(p_form);
		add(p_push);
		
		setPreferredSize(new Dimension(230,680));
		setBackground(Color.YELLOW);
		
		t_code.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					System.out.println("엔터");
					
					int total=posPage.northPanel.getShopCount(t_code.getText());
					
					if(checkCount()<total) {
						
						createRow();
						
						posPage.container.updateUI();
						t_code.setText("");
						sumPrice();
						
					}else {
						JOptionPane.showMessageDialog(posPage, "재고가 없습니다");
						t_code.setText("");
					}
					
				}
			}
		});
		
		bt_minus.addActionListener(this);
		
		t_cash.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_cash.setText("");
			}
		});
		
	}// 생성자
	
	
	
	public int checkCount() {
		int count=0;
		for(int i=0; i<rowList.size(); i++){
			Shop shop=rowList.get(i);
			
			if(shop.getCode().equals(t_code.getText())) {
				count++;
			}
		}
		return count;
	}
	
	
	
	// 엔터치면 자동 생성되는 패널
	public void createRow() {

		// 검색된 DTO 반환 --> 멤버변수로 저장
		shop=posPage.subMain.shopDAO.select(t_code.getText());

		rowList.add(shop);
		
		// row 줄 바꾸기
		Color color=null;
		if(index%2==0) {
			color=Color.CYAN;
		}else {
			color=Color.WHITE;
		}

		// 그냥 생성하지 말고 매개변수를 받아서 처리하자 
		row=new Row(shop, index, color);
		posPage.container.add(row);
		
		posPage.container.repaint();
		index++;
		
		// 바코드값 넘겨주기
		posPage.northPanel.printCount(t_code.getText());
		
	}

	
	// 가격 더하기
	public void sumPrice() {
		int sal=shop.getPrice();
		sumSal=sumSal+sal;

		t_money.setText(Integer.toString(sumSal));
	}
	
	
	// 잔액 계산하기
	public void minusPrice(){
		String money=t_money.getText();
		String cash=t_cash.getText();
		int result=Integer.parseInt(cash)-Integer.parseInt(money);
		if(result<0) {
			JOptionPane.showMessageDialog(posPage, "금액이 부목합니다");
		}else {
			t_balance.setText(Integer.toString(result));			
		}
	}


	//텍스트 필드에 값 누적하는 공통 함수
	public void printNum(String str, JTextField t_field) {
		
		money_result=t_field.getText();
		read=money_result+str;		//텍스트 누적
		
		t_field.setText(read);
	}
	
	
	// 텍스트필드 입력값과 거래에 찍힌 내역까지 지우는 메서드
	public void reset() {
		t_code.setText("");
		t_money.setText("");
		t_cash.setText("");
		t_balance.setText("");
		
		rowList.clear();		// 리스트 초기화
		sumSal=0;		// 계산액 초기화
		
		posPage.container.removeAll();
		posPage.container.updateUI();
		posPage.northPanel.la_num.setText("0");	// 재고량 초기화
	}
	
	
	
	// db에 있는 DTO를 삭제하고, 판매금액을 보관한다
	public void sale(Shop shop) {
		// 1 지금 나타난 DTO 가져오기;
		System.out.println(shop.getShop_idx()+" 지금 보고 있는 DTO");
		int result=posPage.subMain.shopDAO.delete(shop.getShop_idx());
		
		// 계산된 값 저장하기
		Total total=new Total();
		total.setName(shop.getShop_name());
		total.setTotalcash(shop.getPrice());
		
		int result2=posPage.subMain.totalDAO.insert(total);	
	}
	
	

	public void actionPerformed(ActionEvent e) {
		// 텍스트필드에 입력된 값 가져오기
		str=e.getActionCommand();

		for(int i=0; i<btn.length; i++) {
			if(e.getSource().equals(btn[i])){
				printNum(str, t_cash);
			}
		}
		
		if(e.getSource().equals(bt_minus)){	// 계산하기
			minusPrice();
			
		}else if(e.getSource().equals(btn2[0])) {	//현금
			int ok=JOptionPane.showConfirmDialog(posPage.subMain, "판매를 진행하시겠습니까?");
			
			if(ok==JOptionPane.OK_OPTION) {
				for(int i=0; i<rowList.size();i++) {
					
					// row에 담아둔 DTO들 꺼내어 건네주기
					Shop shop=rowList.get(i);
					sale(shop);	
				}
				JOptionPane.showMessageDialog(posPage.subMain,"판매가 완료되었습니다");
				reset();
			}
			
		}else if(e.getSource().equals(btn2[1])) {	//취소
			int cancel=JOptionPane.showConfirmDialog(posPage.subMain, "작성 중인 내용을 취소하시겠습니까?");
			if(cancel==JOptionPane.OK_OPTION) {
				reset();
			}
			
		}else if(e.getSource().equals(btn2[2])) {	//정산
			int cancel=JOptionPane.showConfirmDialog(posPage.subMain, "판매를 중지하겠습니까?");
			if(cancel==JOptionPane.OK_OPTION) {
				posPage.northPanel.state="중지";
				posPage.northPanel.titlecolor=Color.RED;
				
				posPage.northPanel.updateUI();
				totalPage=new TotalPage(this);		
			}
			
		}else if(e.getSource().equals(btn2[3])) {	//판매
			int cancel=JOptionPane.showConfirmDialog(posPage.subMain, "판매를 진행하시겠습니까?");
			if(cancel==JOptionPane.OK_OPTION) {
				posPage.northPanel.state="판매";
				posPage.northPanel.titlecolor=Color.BLUE;
				posPage.northPanel.updateUI();
			}
		}
	}

	
	
	
}
