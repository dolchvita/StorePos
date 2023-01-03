package com.edu.pos.login;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.edu.pos.util.DBManager;

//로그인페이지, 상세페이지, 재고페이지가 올 총 화면
public class LoginMain extends JFrame{
	JPanel container;		//화면 여러개(로그인, 상세, 재고)를 붙여서 화면 전환에 사용될 루트 컨테이너
	LoginPage loginPage;
	
	public static final int LOGINPAGE=0;
	public static final int DETAILPAGE=1;
	public static final int SUBPAGE=2;
	
	ServerSocket server;
	Thread serverThread;		// 메인스레드를 무한대기에 빠지게 하면 안 되기 때문에 스레드 필요!

	int port=1998;
	
	// 3-1) 멀티로 가기 위해서 복수의 클라이언트들을 담아놓을 리스트
	Vector<ServerThread> vec=new Vector<ServerThread>();
	
	
	public LoginMain() {

		setTitle("내꺼");
		container=new JPanel();
//		container.setBackground(Color.GREEN);
		
		// 화면 전환에 사용될 페이지들 부착
		//loginPage=new LoginPage(this);
		loginPage=new LoginPage(this);
		DBManager dbManager=DBManager.getInstance();
		
		container.add(loginPage);
		
		add(container);
		
		setSize(650,750);
		setVisible(true);
		
		// 창들이 이어지려면 닫을 때 시스템 종료시키면 안 된다
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dbManager.release(dbManager.getConnection());
				System.exit(0);
			}
		});
		
		serverThread=new Thread() {
			@Override
			public void run() {
				startServer();
			}
		};
	}
	
	
	public void startServer() {
		
		try {
			server=new ServerSocket(port);
			loginPage.area.append("서버 가동!\n");
			
			
			while(true) {
				
				Socket socket=server.accept();
				
				String ip=socket.getInetAddress().getHostAddress();
				loginPage.area.append(ip+" 접속자 발견!\n");
				
				ServerThread st=new ServerThread(this, socket);
				st.start();
				
				// 3-2) 탄생한 쓰레드를 담아놓기
				vec.add(st);
				loginPage.area.append("현재 전달할 고객 수 : "+vec.size()+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		new LoginMain();
	}
	
	
}
