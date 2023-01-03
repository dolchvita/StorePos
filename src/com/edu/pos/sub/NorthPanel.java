package com.edu.pos.sub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.edu.pos.login.LoginMain;
import com.edu.pos.model.Shop;
import com.edu.pos.util.ImageManager;

public class NorthPanel extends Panel{
	JPanel p_icon;
	
	JPanel p_space;
	JPanel p_count;
	
	JLabel la_count;
	JLabel la_num;
	JLabel icon_power;		// 전원 아이콘
	
	JLabel la_pos;
	
	int cnt=0;
	String state="판매";
	Color titlecolor=Color.BLUE;
	
	public NorthPanel(PosPage posPage) {
		super(posPage);
		
		p_icon=new JPanel();
		p_icon.setBackground(Color.WHITE);
		p_icon.setPreferredSize(new Dimension(230,80));
		setLayout(new BorderLayout());
		
		p_space=new JPanel() {
			protected void paintComponent(Graphics g) {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, 400, 80);
				g.setColor(Color.CYAN);
				g.fillOval(30, 10, 350, 60);
				
				g.setColor(Color.BLACK);
				g.setFont(new Font("Dottum", Font.BOLD, 23));
				g.drawString(state, 260, 48);
				
				g.setColor(titlecolor);
				g.drawString("DOLCHVITA", 76, 50);
				
				g.setFont(new Font("Dottum", Font.BOLD, 15));
				g.setColor(Color.ORANGE);
				g.drawString("소매상인SM", 160, 27);
				
				
			}
		};
		p_space.setBackground(Color.YELLOW);
		p_space.setPreferredSize(new Dimension(400,80));
		
		la_pos=new JLabel(" 판 매 ");
		la_pos.setPreferredSize(new Dimension(200,60));
		la_pos.setFont(new Font("Dotum", Font.PLAIN, 16));
		LineBorder line=new LineBorder(Color.BLUE);
		la_pos.setHorizontalAlignment(JLabel.CENTER);
		la_pos.setBorder(line);
		
	//	p_space.add(la_pos);
		
		
		p_count=new JPanel();
		p_count.setBackground(Color.PINK);
		p_count.setPreferredSize(new Dimension(150,80));
		
		// 아이콘 이미지 반환받기 (String util)
		ImageManager imageManager=new ImageManager();
		ImageIcon icon=imageManager.getIcon("res/image/on.png", 60, 60);
		icon_power=new JLabel(icon);
		p_icon.add(icon_power);
		
		Dimension d=new Dimension(40,40);
	
		//p_count 영역
		la_count=new JLabel("현 재고량    :  ");
		la_num=new JLabel("0");
		la_count.setPreferredSize(new Dimension(145,65));
		la_count.setFont(new Font("Dottum", Font.BOLD, 22));
		la_num.setFont(new Font("Dottum", Font.BOLD, 22));
		
		
		p_count.add(la_count);
		p_count.add(la_num);
		
		
		// 화면 부착
		add(p_space, BorderLayout.WEST);
		add(p_icon, BorderLayout.EAST);
		add(p_count);
		
		setPreferredSize(new Dimension(830,80));
		setBackground(Color.RED);
		
		icon_power.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("전원버튼");
				int result=JOptionPane.showConfirmDialog(posPage.subMain, "나가시겠습니까?");
				if(result==JOptionPane.OK_OPTION) {
					
					posPage.subMain.setVisible(false);
					new LoginMain();
				}
			}
		});

	}

	// 재고수 반환
	public int getShopCount(String code) {
		int cnt=0;
		Shop shop=posPage.subMain.shopDAO.count(code);
		cnt=shop.getCount();
		return cnt;
	}
	
	// 위에서 얻은 수를 화면에 출력
	public void printCount(String code) {
		int cnt=getShopCount(code);
		la_num.setText(Integer.toString(cnt));
	}

	
	
}
