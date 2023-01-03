package com.edu.pos.login;

import java.awt.Dimension;

import javax.swing.JPanel;

//모든 페이지들이 상속받을 최상위 페이지(패널)
public class Page extends JPanel{
	LoginMain adminMain;
	
	public Page(LoginMain adminMain) {
		this.adminMain=adminMain;

		this.setPreferredSize(new Dimension(1220,700));
	}
	
	
}
