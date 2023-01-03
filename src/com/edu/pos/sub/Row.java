package com.edu.pos.sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.edu.pos.model.Shop;

import oracle.net.aso.g;

public class Row extends JPanel{
	Shop shop;
	int index;
	Color color;
	
	public Row(Shop shop, int index, Color color) {
		this.shop=shop;
		this.index=index;	// 생성되는 패널의 순번을 가진다(호출될 때마다 1증가)
		this.color=color;
		setPreferredSize(new Dimension(550, 60));
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		g.setColor(color);
		g.fillRect(0, 0, 550, 70);
		
		g.setColor(Color.BLACK);
		
		Font font=new Font("Dottum", Font.BOLD, 18);
		Font font2=new Font("Dottum", Font.PLAIN, 13);		//기본 글자체
		
		g.drawString("no", 10, 20);
		g.drawString(Integer.toString(index), 10, 45);

		
		g.setColor(Color.BLUE);
		g.setFont(new Font("Dottum", Font.BOLD, 13));
		g.drawString("판매", 50, 20);

		g.setColor(Color.BLACK);
		g.setFont(font2);
		g.drawString("바코드", 130, 20);
		g.drawString(shop.getCode(), 130, 45);

		g.drawString("상품명", 280, 20);
		g.setFont(font);
		g.drawString(shop.getShop_name(), 280, 45);

		
		g.setFont(font2);
		g.drawString("소비자가", 450, 20);
		g.setFont(font);
		g.drawString(Integer.toString(shop.getPrice()), 450, 45);

//		g.setFont(font2);
//		g.drawString("수량", 500, 20);
////		g.drawString("수량", 500, 35);
		
	}
}
