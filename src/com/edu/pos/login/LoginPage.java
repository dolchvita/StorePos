package com.edu.pos.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.edu.pos.model.Login;
import com.edu.pos.model.LoginDAO;
import com.edu.pos.sub.SubMain;

public class LoginPage extends JPanel implements ActionListener{
	JPanel p_north, p_east, p_west, p_south, p_center;
	JPanel p_form, p_button;
	JPanel p1,p2;
	JLabel la_id, la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login;
	JButton bt_setting;
	JButton bt_close;
	JButton bt_pos;
	
	// 센터 영역(네트워크)
	JPanel p_net;
	JButton bt_net;
	JButton bt_msg;
	JButton bt_client;
	JTextArea area;
	JScrollPane scroll;
	
	// 로그인 여부 논리값
	boolean flag=false;
	
	LoginMain loginMain;
	LoginDAO loginDAO;

	SettingPage settingPage;		// 환경설정 창
	
//	ClientFrame2 clientFrame;
	ClientFrame clientFrame;
	
	int count;
	
	SubMain subMain;
	
	JFileChooser chooser;
	File file;
	
	public LoginPage(LoginMain loginMain) {
		this.loginMain=loginMain;
		loginDAO=new LoginDAO();
		
		p_north=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				File file=new File("C:/java_workspace2/data/project1230/image/posbg.jpg");
				Image image;
				
				try {
					image = ImageIO.read(file);
					g2.drawImage(image, 0, 0, 600, 180, loginMain);
					
					g2.setColor(Color.WHITE);
					g2.fillRect(160, 60, 300, 80);
					
					g2.setColor(Color.BLUE);
					g2.setFont(new Font("Dottum", Font.BOLD, 50));
					g2.drawString("중앙 이대점", 180, 120);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		p_east=new JPanel();
		p_west=new JPanel();
		p_south=new JPanel();
		p_center=new JPanel();
		
		p_north.setBackground(Color.BLUE);
		p_east.setBackground(Color.RED);
		p_west.setBackground(Color.YELLOW);
		p_south.setBackground(Color.LIGHT_GRAY);
		
		p_center.setBackground(Color.PINK);
		
		
		p_north.setPreferredSize(new Dimension(600,180));	//북쪽
		p_south.setPreferredSize(new Dimension(600,100));	//남쪽
		
		p_east.setPreferredSize(new Dimension(80,330));	//동쪽
		p_west.setPreferredSize(new Dimension(60,330));	//서쪽
		
		
		//센터 영역을 다시 BorderLayout 적용
		
		//센터 패널에 붙일 두 패널
		p_form=new JPanel();
		p_button=new JPanel();
		p_form.setPreferredSize(new Dimension(570,120));
		p_button.setPreferredSize(new Dimension(320,260));
		p_button.setLayout(new BorderLayout());
//		p_form.setBackground(Color.CYAN);
		p_button.setBackground(Color.BLACK);
		
		/*****/
		// p_button 패널 영역
		p_net=new JPanel();
		bt_net=new JButton("연동");
		bt_msg=new JButton("홍보하기");
		bt_client=new JButton("고객");
		area=new JTextArea("로그인이 필요한 서비스입니다");
		scroll=new JScrollPane(area);

		chooser=new JFileChooser("C:/java_workspace2/data/project1230/data");
		
		p_net.setPreferredSize(new Dimension(320,80));
		p_net.setBackground(Color.CYAN);
		Dimension d4=new Dimension(90,50);
		bt_net.setPreferredSize(d4);
		bt_msg.setPreferredSize(d4);
		bt_client.setPreferredSize(d4);
		p_net.add(bt_net);
		p_net.add(bt_msg);
		p_net.add(bt_client);
		
		p_button.add(scroll);
		p_button.add(p_net, BorderLayout.NORTH);
		
		
		
		p1=new JPanel();
		p2=new JPanel();
//		p1.setBackground(Color.GREEN);
//		p2.setBackground(Color.WHITE);
		p1.setPreferredSize(new Dimension(420,110));
		p2.setPreferredSize(new Dimension(95,110));
		
		
		la_id=new JLabel("사용자 ID");
		la_pass=new JLabel("사용자 P/W");
		t_id=new JTextField("id 입력");
		t_pass=new JPasswordField();
		bt_login=new JButton("Login");
		
		//텍스트필드 공통 사이즈
		Dimension d=new Dimension(255,35);
		Dimension d2=new Dimension(110,35);
		la_id.setPreferredSize(d2);
		la_pass.setPreferredSize(d2);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		bt_login.setPreferredSize(new Dimension(70,65));
		
		
		// p_south 영역
		bt_setting=new JButton("환경설정");
		bt_close=new JButton("닫기");
		bt_pos=new JButton("제품등록");
		Dimension d3=new Dimension(100,80);
		bt_setting.setPreferredSize(d3);
		bt_close.setPreferredSize(d3);
		bt_pos.setPreferredSize(d3);
		
		bt_setting.setBackground(Color.PINK);
		bt_close.setBackground(Color.PINK);
		bt_pos.setBackground(Color.PINK);
		
		p_south.add(bt_setting);
		p_south.add(bt_close);
		p_south.add(bt_pos);
		
		p1.add(la_id);
		p1.add(t_id);
		p1.add(la_pass);
		p1.add(t_pass);
		
		p2.add(bt_login);
		
		p_form.add(p1);
		p_form.add(p2);
		
		p_center.add(p_form);
		p_center.add(p_button);
		
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(600,680));
	
		add(p_north, BorderLayout.NORTH);
//		add(p_east, BorderLayout.EAST);
//		add(p_west, BorderLayout.WEST);
		add(p_south, BorderLayout.SOUTH);
		add(p_center);
		
		t_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				t_id.setText("");
			}
		});
		
		t_pass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				t_pass.setText("");
			}
		});

		bt_login.addActionListener(this);
		bt_setting.addActionListener(this);
		bt_net.addActionListener(this);
		bt_msg.addActionListener(this);
		bt_pos.addActionListener(this);
		
		bt_client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flag){
					Object obj=e.getSource();
					count++;
					if(count==1) {
						clientFrame=new ClientFrame(Color.PINK);					
					}else {
						clientFrame=new ClientFrame(Color.YELLOW);										
					}				
				}else {
					JOptionPane.showMessageDialog(LoginPage.this, "로그인이 필요한 서비스입니다");
				}
			}
		});
	}
	
	
	// 1-3) 문자열을 받아 제이슨으로 파싱하자
	public String parse(String data) {
		JSONParser parser=new JSONParser();
		String msg=null;
		try {
			JSONObject json=(JSONObject)parser.parse(data);
			
			msg=(String)json.get("홍보");
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	
	public String open() {
		int result=chooser.showOpenDialog(this);
		FileReader reader=null;
		BufferedReader buffr=null;
		StringBuilder sb=new StringBuilder();
		
		// 1-1) 파일에 있는 문자를 읽어오기
		if(result==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			
			try {
				reader=new FileReader(file);
				buffr=new BufferedReader(reader);
				
				String data=null;
				while(true) {
					data=buffr.readLine();
					if(data==null)break;
					//area.append(data);
					
					// 1-2) 읽어들인 내용을 담자
					sb.append(data);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(buffr!=null) {
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(reader!=null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return sb.toString();
	}
	
	public void loginCheck() {	
		Login login=loginDAO.select(t_id.getText(), new String(t_pass.getPassword()));
		if(login!=null) {
			JOptionPane.showMessageDialog(loginMain, "로그인 되었습니다");
			
			flag=true;
			area.setText("");
			
		}else{
			JOptionPane.showMessageDialog(loginMain, "로그인 정보를 확인해주세요");
			
		}

	}


	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==bt_login) {		//로그인
			loginCheck();
			
		}else if(obj==bt_setting) {		//환경설정
			if(flag){
				settingPage=new SettingPage();				
			}else {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}
			
		}else if(obj==bt_net) {		//연동
			if(flag){
				loginMain.serverThread.start();			
			}else {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}
		
		}else if(obj==bt_msg) {		//홍보하기
			if(flag){
				String data=open();
				String msg=parse(data);
				
				area.append(msg);				
			}else {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}
			
		}else if(obj==bt_pos) {
			if(flag){
				subMain=new SubMain();
				loginMain.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}
		}
	}
	
	
}
