package com.edu.pos.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame{
	JPanel p_north;

	JTextField t_port;
	JButton bt_connect;
	
	JTextArea area;
	JScrollPane scroll;
	
	JPanel p_south;
	JTextField t_input;		//메시지 입력창
	JButton bt_send;			//전송 버튼

	MessageThread thread;
	
	
	public ClientFrame(Color color) {
		p_north=new JPanel();
		p_north.setBackground(color);

		t_port=new JTextField("1998",6);
		bt_connect=new JButton("접속");
	
		area=new JTextArea();
		scroll=new JScrollPane(area);
		

		p_north.add(t_port);
		p_north.add(bt_connect);
		
		p_south=new JPanel();
		t_input=new JTextField(15);
		bt_send=new JButton("전송");
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		
		p_south.add(t_input);
		p_south.add(bt_send);
		
		add(p_south, BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(300,400);
		
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				
				if(key==KeyEvent.VK_ENTER){
					thread.sendMsg(t_input.getText());
					t_input.setText("");
				}
			}
		});
	
	}
	
	
	public void connect() {
		String ip="172.30.1.65";
		int port=1998;
		
		try {
			// 각각의 소켓 생성 (멀티이기 때문)
			Socket socket=new Socket(ip,port);
			
			// 소켓에서 생성된 메시지들을 스트림으로 읽어들이고 있따
			// 생성된 소켓 전달
			thread=new MessageThread(this, socket);
			thread.start();
			
			System.out.println("스레드 생성");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
