package com.edu.pos.sub;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.edu.pos.login.LoginMain;
import com.edu.pos.regist.RegistMain;

public class SubLabel extends JLabel{
	String title;
	int index;
	SubMain subMain;
	
	public SubLabel(SubMain subMain, String title, int index) {
		super(title);
		this.title=title;
		this.index=index;
		this.subMain=subMain;
		
		setFont(new Font("Dottum", Font.BOLD, 16));
		setPreferredSize(new Dimension(150,50));
		setBackground(Color.MAGENTA);
	
		Border border=new LineBorder(Color.MAGENTA);
		setBorder(border);
		setHorizontalAlignment(JLabel.CENTER);
	
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if(index==0) {
					subMain.registMain=new RegistMain();
					subMain.overRoll(0);
			
				}else if(index==1) {
					subMain.overRoll(1);
					subMain.showHide(subMain.REMAINPAGE);
					
				}else if(index==2) {
					subMain.overRoll(2);
					subMain.showHide(subMain.POSPAGE);
					
				}else if(index==3) {
					subMain.overRoll(3);
					subMain.showHide(subMain.RECORDPAGE);
					
				}else if(index==4) {
					subMain.overRoll(4);
					subMain.setVisible(false);
					subMain.loginMian=new LoginMain();
					
				}else if(index==5){
					subMain.overRoll(5);
					int result=JOptionPane.showConfirmDialog(subMain, "종료하시겠습니까?");
					if(result==JOptionPane.OK_OPTION) {

						subMain.setVisible(false);
						subMain.loginMian=new LoginMain();
					}
				}
			}
		});
	}
	
	
	
}
