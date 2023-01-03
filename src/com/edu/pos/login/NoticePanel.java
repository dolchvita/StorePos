package com.edu.pos.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class NoticePanel extends JPanel{
	JPanel p_north, p_south, p_center;
	
	
	public NoticePanel() {
		p_north=new JPanel();
		p_south=new JPanel();
		p_center=new JPanel();
		
		p_north.setPreferredSize(new Dimension(600,150));	//북쪽
		p_south.setPreferredSize(new Dimension(600,100));	//남쪽
		
		p_north.setBackground(Color.BLUE);
		p_south.setBackground(Color.ORANGE);
		p_center.setBackground(Color.PINK);
		
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(600,680));
		
		add(p_north, BorderLayout.NORTH);
		add(p_south, BorderLayout.SOUTH);
		add(p_center);
		
	
	}
	
	
	
}
