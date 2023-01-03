package com.edu.pos.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	Socket socket;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	LoginMain loginMain;

	public ServerThread(LoginMain loginMain, Socket socket) {
		// 메인에서 생성한 소켓을 받겠다
		this.socket=socket;
		this.loginMain=loginMain;
		
		try {
			// 네트워크 작동 중인 실제 소켓을 읽어들일 스트림 생성
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void listen() {
		String msg=null;
		
		try {
			msg=buffr.readLine();
			loginMain.loginPage.area.append(msg+"\n");
			
			// *2* 모두에게 샌드함수 호출하기(즉 텍스트 뿌리기) -- 샌드의 역할!
			// 2-1) 각각의 클라이언트마다 대응하고 있는 서버소켓이 담긴 리스트마다 꺼내기
			for(int i=0; i<loginMain.vec.size(); i++) {
				ServerThread st=loginMain.vec.get(i);
				
				st.sendMsg(msg);
			}
			// 서버는 리슨에서 샌드 메서드에게 읽은 메시지를 전달해줄 의무가 있다!
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
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
