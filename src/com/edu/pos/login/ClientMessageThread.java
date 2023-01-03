package com.edu.pos.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientMessageThread extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	ClientFrame adminFrame;
	
	public ClientMessageThread(ClientFrame adminFrame, Socket socket) {
		this.adminFrame=adminFrame;
		this.socket=socket;
		
		// 메시지 읽을 스트림 준비
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void listen() {
		String msg=null;
		
		try {
			// 스트림 기반으로 읽어서 화면에 출력
			msg=buffr.readLine();
			adminFrame.area.append(msg+"\n");
			
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
	
}
