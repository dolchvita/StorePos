package com.edu.pos.sub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.edu.pos.model.ShopDAO;

public class PosPage extends JPanel{
	NorthPanel northPanel;
	EastPanel eastPanel;
	
	// Row 패널들이 부착될 컨테이너
	JPanel container;
	SubMain subMain;
	
	public PosPage(SubMain subMain) {
		this.subMain=subMain;
		
		northPanel=new NorthPanel(this);
		eastPanel=new EastPanel(this);
		
		container=new JPanel();
		container.setPreferredSize(new Dimension(600,600));
//		container.setBackground(Color.LIGHT_GRAY);
		
//		this.setBackground(Color.PINK);
		this.setLayout(new BorderLayout());

		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
		add(container);
	}

}
