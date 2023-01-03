package com.edu.pos.login;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.edu.pos.model.LoginDAO;

public class SettingPage extends JFrame{
	// 화면의 보이기 요소 제어
	boolean flag=true;
	JPanel container;
	JLabel la_id, la_pass;
	JTextField t_id;
	JTextField t_pass;
	JButton bt_change;
	
	LoginDAO loginDAO;
	
	public SettingPage() {
		setTitle("로그인 정보 수정");
		
		container=new JPanel();
		loginDAO=new LoginDAO();
		
		la_id=new JLabel("사용자 ID");
		la_pass=new JLabel("사용자 P/W");
		t_id = new JTextField();
		t_pass = new JTextField();
		bt_change=new JButton("바꾸기 저장");
		
		Dimension d2=new Dimension(110,35);
		la_id.setPreferredSize(d2);
		la_pass.setPreferredSize(d2);
		Dimension d = new Dimension(250, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		
		container.add(la_id);
		container.add(t_id);
		container.add(la_pass);
		container.add(t_pass);
		container.add(bt_change);
		
		add(container);
		
		setVisible(flag);
		setSize(400,250);
		setLocationRelativeTo(null);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				flag=!flag;
			}
		});

		
		bt_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
	}
	
	public void change(){
		String id=t_id.getText();
		String pass=t_pass.getText();
		System.out.println(id);
		
		if(id.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(this, "수정 정보를 입력해주세요");
			
		}else {			
			int result=loginDAO.update(id,pass);
			
			if(result>0) {
				JOptionPane.showMessageDialog(this, "로그인 정보가 수정되었습니다");
			}	
		}
	};

}
