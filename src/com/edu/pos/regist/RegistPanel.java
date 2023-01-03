package com.edu.pos.regist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;	

	public class RegistPanel extends Panel{
		JPanel p_brand;
		JPanel p_date;
		JPanel p_string;
		JPanel p_detail;
		
		JLabel la_title;
		
		int yy;
		int mm;
		int dd;
		
		
	public RegistPanel(RegistMain registMain) {
		super(registMain);
		
		p_brand=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				
				g2.setColor(Color.PINK);
				g2.fillRect(0, 0, 180, 130);
				
				Class myClass=this.getClass();
				// 패키지에 있는 이미지 경로 가져오기
				URL url=myClass.getClassLoader().getResource("res/image/r.png");				
				try {
					Image image = ImageIO.read(url);
					g2.drawImage(image, 42, 18, 100, 100, null);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		p_brand.setBackground(Color.PINK);
		p_brand.setPreferredSize(new Dimension(180,130));

		p_date=new JPanel();
		p_string=new JPanel();
		p_detail=new JPanel();
		la_title=new JLabel("구매자료입력");
		
		Dimension d=new Dimension(260,130);
		p_date.setPreferredSize(d);
		p_string.setPreferredSize(d);
		p_detail.setPreferredSize(d);
		p_date.setBackground(Color.PINK);
		p_string.setBackground(Color.PINK);
		p_detail.setBackground(Color.PINK);
	
		la_title.setFont(new Font("Dottum", Font.BOLD, 30));
		la_title.setPreferredSize(new Dimension(200,130));
		p_string.add(la_title);
		
		add(p_brand);
		add(p_date);
		add(p_string);
		add(p_detail);
		
		getDate();
		designDatePanel();
		designDetailPanel();
		
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(980,140));
		
	}
	
	
	public void getDate() {
		Calendar currentObj=Calendar.getInstance();
		yy=currentObj.get(Calendar.YEAR);
		mm=currentObj.get(Calendar.MONTH);
		dd=currentObj.get(Calendar.DATE);
		
		System.out.println(yy+" 년도 "+(mm+1)+" 월 "+dd+" 일");		
	}

	public void designDatePanel() {
		JLabel la_date=new JLabel("일자");
		JLabel la_loc=new JLabel("지점");
		JLabel la_loc2=new JLabel("전표");
		
		Font font=new Font("Dotum", Font.PLAIN, 16);
		la_date.setFont(font);
		la_loc.setFont(font);
		la_loc2.setFont(font);

		// 글자가운데
		la_date.setHorizontalAlignment(JLabel.CENTER);
		la_loc.setHorizontalAlignment(JLabel.CENTER);
		la_loc2.setHorizontalAlignment(JLabel.CENTER);
		// border처리
		LineBorder line=new LineBorder(Color.green);
		la_date.setBorder(line);
		la_loc.setBorder(line);
		la_loc2.setBorder(line);
		
		Dimension d=new Dimension(60,33);
		la_date.setPreferredSize(d);
		la_loc.setPreferredSize(d);
		la_loc2.setPreferredSize(d);
		
		JTextField t_date=new JTextField(yy+"-"+(mm+1)+"-"+dd);		//현재 날짜 출력
		t_date.setForeground(Color.MAGENTA);		//글자색상
		JComboBox box1=new JComboBox();
		box1.addItem("중앙 이대점");
		
		JComboBox box2=new JComboBox();
		JComboBox box3=new JComboBox();
		box2.addItem("구매");
		box2.addItem("판매");
		box3.addItem("1");
		
		Font font2=new Font("Dotum", Font.BOLD, 18);
		box1.setFont(font2);
		box2.setFont(font2);
		box3.setFont(font2);
		
		Dimension d2=new Dimension(170,33);
		t_date.setPreferredSize(d2);
		box1.setPreferredSize(d2);

		Dimension d3=new Dimension(83,33);
		box2.setPreferredSize(d3);
		box3.setPreferredSize(d3);
		
		p_date.add(la_date);
		p_date.add(t_date);
		p_date.add(la_loc);
		p_date.add(box1);
		p_date.add(la_loc2);
		p_date.add(box2);
		p_date.add(box3);
	}
	
	public void designDetailPanel() {
		JLabel la_date=new JLabel("담당자");
		JLabel la_loc=new JLabel("전 화");
		JLabel la_loc2=new JLabel("마진율");
		
		Font font=new Font("Dotum", Font.PLAIN, 16);
		la_date.setFont(font);
		la_loc.setFont(font);
		la_loc2.setFont(font);

		// 글자가운데
		la_date.setHorizontalAlignment(JLabel.CENTER);
		la_loc.setHorizontalAlignment(JLabel.CENTER);
		la_loc2.setHorizontalAlignment(JLabel.CENTER);
		// border처리
		LineBorder line=new LineBorder(Color.green);
		la_date.setBorder(line);
		la_loc.setBorder(line);
		la_loc2.setBorder(line);
		
		Dimension d=new Dimension(60,33);
		la_date.setPreferredSize(d);
		la_loc.setPreferredSize(d);
		la_loc2.setPreferredSize(d);
		
		
		JTextField t_date=new JTextField(" 류예서 ");		//현재 날짜 출력
		JTextField t_phone=new JTextField(" 010 - 1111 - 9999 ");		//현재 날짜 출력
		t_date.setForeground(Color.GRAY);		//글자색상
		
		JComboBox box1=new JComboBox();
		box1.addItem(" 120%");
		
		Font font2=new Font("Dotum", Font.BOLD, 12);
		t_phone.setFont(font2);
		box1.setFont(font2);

		
		Dimension d2=new Dimension(170,33);
		t_date.setPreferredSize(d2);
		t_phone.setPreferredSize(d2);
		box1.setPreferredSize(d2);
		
		p_detail.add(la_date);
		p_detail.add(t_date);
		p_detail.add(la_loc);
		p_detail.add(t_phone);
		p_detail.add(la_loc2);
		p_detail.add(box1);
	}
	
	
}
