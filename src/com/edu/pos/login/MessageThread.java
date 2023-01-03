package com.edu.pos.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	InputStream is;		//바이트기반이 근본이다!
	InputStreamReader reader;
	
	OutputStream os;
	OutputStreamWriter writer;
	BufferedWriter buffw;
	
	ClientFrame adminFrame;
	
	public MessageThread(ClientFrame adminFrame, Socket socket) {
		this.adminFrame=adminFrame;
		this.socket=socket;
		
		// 메시지 읽을 스트림 준비
		try {
			is=socket.getInputStream();
			reader=new InputStreamReader(is);
			buffr=new BufferedReader(reader);
			
			os=socket.getOutputStream();
			writer=new OutputStreamWriter(os);
			buffw=new BufferedWriter(writer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void listen() {
		String msg=null;
		System.out.println("여기까지 왔니?");
		
		try {
			// 스트림 기반으로 읽어서 화면에 출력
			msg=buffr.readLine();
			adminFrame.area.append(msg+"\n");
			
			System.out.println("리슨222");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// 보내는 측면에서 매개변수로 메시지 전달
	public void sendMsg(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public void run() {
		while(true) {
			listen();
		}
	}
	
}
