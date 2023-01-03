package com.edu.pos.regist;

import javax.swing.JPanel;

// 입력페이지에 있는 모든 패널의 최상위 객체
public class Panel extends JPanel{
	
	RegistMain registMain;
	
	
	public Panel(RegistMain registMain) {
		this.registMain=registMain;
	}
}
