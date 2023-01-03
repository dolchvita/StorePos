package com.edu.pos.regist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edu.pos.model.Shop;
import com.edu.pos.model.Vender;
import com.edu.pos.sub.RemainPage;

public class ButtonPanel extends Panel implements ActionListener{
	JPanel p_input;
	JPanel p_result;
	JPanel p_space;
	
	//p_input 상품 등록시 입력 폼들
	JTextField t_vender;
	JTextField t_name;
	JTextField t_price;
	JTextField t_code;
	
	// p_space
	JButton[] btn=new JButton[8];
	String[] btName= {"등록","저장","수정","삭제","조회","취소","설정","종료"};

	// p_result 패널 영역
	JTextField t_count;
	JTextField t_money;
	JTextField t_balance;
	JLabel la_count;
	JLabel la_money;
	JLabel la_balance;
	
	Shop shop;
	Vender vender;

	// 엑셀 파일에 들어갈 데이터
	List stockList;
	JFileChooser chooser;
	
	// 엑셀 파일 취소(삭제)할 때 사용
	JFileChooser chooser2;
	File file2;
	
	public ButtonPanel(RegistMain registMain) {
		super(registMain);
		
		p_input=new JPanel();
		p_result=new JPanel();
		p_space=new JPanel();
		
		Dimension d=new Dimension(350,130);
		p_input.setPreferredSize(d);
		p_result.setPreferredSize(d);
		p_space.setPreferredSize(new Dimension(250,130));
		
		// p_input 패널 영역
		t_vender=new JTextField("거래처명");
		t_name=new JTextField("상품 입력");
		t_price=new JTextField("가격 입력");
		t_code=new JTextField("바코드");
		
		Dimension d2=new Dimension(220,30);
		t_vender.setPreferredSize(d2);
		t_name.setPreferredSize(d2);
		t_price.setPreferredSize(d2);
		t_code.setPreferredSize(d2);
		
		p_input.add(t_vender);
		p_input.add(t_name);
		p_input.add(t_price);
		p_input.add(t_code);
		
		
		// p_space 패널 영역
		p_space.setLayout(new GridLayout(2,4));
		
		for(int i=0; i<btn.length; i++) {
			btn[i]=new JButton(btName[i]);
			
			btn[i].addActionListener(this);
			
			p_space.add(btn[i]);
		}
		chooser=new JFileChooser("C:/java_workspace2/data/project1230/excel");
		
		
		//p_result 패널 영역
		t_count=new JTextField();
		t_money=new JTextField();
		t_balance=new JTextField();
		
		t_count.setPreferredSize(d2);
		t_money.setPreferredSize(d2);
		t_balance.setPreferredSize(d2);
		Font font2=new Font("Dotum", Font.BOLD, 18);
		t_count.setFont(font2);
		t_money.setFont(font2);
		
		la_count=new JLabel("수량");
		la_money=new JLabel("금액");
		la_balance=new JLabel("잔액");
		
		Font font=new Font("Dotum", Font.BOLD, 16);
		la_count.setFont(font);
		la_money.setFont(font);
		la_balance.setFont(font);
		
		Dimension d3=new Dimension(60,30);
		la_count.setPreferredSize(d3);
		la_money.setPreferredSize(d3);
		la_balance.setPreferredSize(d3);

		
		p_result.add(la_count);
		p_result.add(t_count);
		p_result.add(la_money);
		p_result.add(t_money);
		p_result.add(la_balance);
		p_result.add(t_balance);
		
		
		
		add(p_input);
		add(p_space);
		add(p_result);
		
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(980,150));
		
		t_name.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_name.setText("");
			}
		});
		
		t_price.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_price.setText("");
			}
		});
		
		t_code.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_code.setText("");
			}
		});
		
	}
	
	
	public void regist() {
		//텍스트 필드에 입력된 내용을 shop DTO에 담아 DAO처리하도록 한다!
		
		int venderIdx=registMain.venderDAO.getVenderIdx(t_vender.getText());
		Shop shop=new Shop();
		Vender vender=new Vender();
		shop.setVender(vender);
		
		vender.setVender_idx(venderIdx);	//여기서 shop에 들어있는 vender도 세팅
		shop.setShop_name(t_name.getText());
		shop.setPrice(Integer.parseInt(t_price.getText()));
		shop.setCode((t_code.getText()));
		
		/* 민쌤 코멘트 */
		//숫자로 변경하는 과정에서, 숫자가 아닌게 있는거 같아요
		//바코느는 문자로 처리하셔도 되빈다 바코드 자체로, 우리가 연산을 한다거나 그런일이 없기 때문에 , 그냥 문자로 취급하시는게 나아요
		//문자로 바꾸셔도 됨
		
		int result=registMain.shopDAO.insert(shop);

		
		if(result>0) {
			JOptionPane.showMessageDialog(registMain, "등록되었습니다");
			
			// 테이블 목록을 다시 갱신하는 메서드를 가져오자! --> 메인에 메서드 만들자
			/* 테이블의 목록을 다시보여주려면 어떻해야 할까?
			 * 테이블 자체는 아무런 능력이 없고 테이블에게 정보를 제공해주는 테이블"모델"의 데이터가 바뀌어야 한다
			 * 바뀐 후 테이블은 업뎃UI만 때리면 됨*/
			registMain.getList();
		}	
	}


	
	
	// 1-4) 쿼리실행
	public void getDetail(int shop_idx) {
		System.out.println(shop_idx+" 테스트중 (샵 아이디)");
		

		// 테이블 선택으로 인해 생성되어진 vender DTO 저장한다 -> 멤버변수로
//		vender=registMain.venderDAO.select(vender_idx);
		
		
		// 멤버변수로 저장
		shop=registMain.shopDAO.select(shop_idx);
	
		t_vender.setText(shop.getVender().getVender_name());
		t_name.setText(shop.getShop_name());
		t_price.setText(Integer.toString(shop.getPrice()));
		t_code.setText(shop.getCode());
		
		System.out.println("테스트중 "+shop.getVender().getVender_name());
	}

	
	public void edit() {
		// 1-1 수정할 내용을 설정해서 DAO에게 넘겨주면 되는것!

		shop.setShop_name(t_name.getText());
		shop.setPrice(Integer.parseInt(t_price.getText()));
		shop.setCode(t_code.getText());
		
		int result=registMain.shopDAO.update(shop);
		
		if(result>0) {
			JOptionPane.showMessageDialog(registMain, "수정 완료");
			
			registMain.getList();
		}
	}

	
	public void del() {
		int result=registMain.shopDAO.delete(shop.getShop_idx());
		
		if(result>0) {
			JOptionPane.showMessageDialog(registMain, "삭제 완료");
		
			// 테이블 갱신
			registMain.getList();
		}
	}
	
	
	
	// 모든 가격 더하기
	public void sumPrice() {
		// 레코드 수
		int total=registMain.table.getRowCount();
		t_count.setText(total+" 개");
		
		int sumSal=0;
		
		for(int i=0; i<total; i++) {
			int sal=Integer.parseInt(registMain.table.getValueAt(i, 3).toString());
			
			sumSal=sumSal+sal;
		}
		t_money.setText("총 "+sumSal+" 원");
	}
	
	
	
	// 입력폼 모든 텍스트필드 내역을 지우는 메서드
	public void reset() {
		t_vender.setText("");
		t_name.setText("");
		t_price.setText("");
		t_code.setText("");
	}
	

	
	public void saveFile() {
		int result=chooser.showOpenDialog(registMain);
		
		if(result!=JFileChooser.APPROVE_OPTION){
			return;
		}
		
		File file=chooser.getSelectedFile();
		
		HSSFWorkbook book=new HSSFWorkbook();
		HSSFSheet sheet=book.createSheet();
		HSSFRow row=sheet.createRow(0);
		
		// 일단 엑섹 column 채우기 (테이블과 비슷)
		String[] column= {"No", "거래처","상품명","소비자가","바코드","일자"};
		
		for(int i=0; i<column.length; i++) {
			HSSFCell cell=row.createCell(i);
			cell.setCellValue(column[i]);
		}
		
		stockList=registMain.shopDAO.selectAll();
		
		// 이제 엑셀에 실제 데이터 채우기
		for(int i=0; i<stockList.size(); i++) {
			Shop shop=(Shop)stockList.get(i);
			
			// 한 줄당 셀을 채운다
			HSSFRow record=sheet.createRow(i+1);		//컬럼 다음 줄
			
			HSSFCell shop_idx=record.createCell(0);
			HSSFCell vender_name=record.createCell(1);
			HSSFCell shop_name=record.createCell(2);
			HSSFCell price=record.createCell(3);
			HSSFCell code=record.createCell(4);
			HSSFCell regdate=record.createCell(5);

			shop_idx.setCellValue(shop.getShop_idx());
			vender_name.setCellValue(shop.getVender().getVender_name());
			shop_name.setCellValue(shop.getShop_name());
			price.setCellValue(shop.getPrice());
			code.setCellValue(shop.getCode());
			regdate.setCellValue(shop.getRegdate());
		}
		
		FileOutputStream fos=null;
		
		try {
			// 지정한 곳에 파일 저장됨!
			fos=new FileOutputStream(file);
			book.write(fos);
			JOptionPane.showMessageDialog(registMain, "엑셀파일 저장 완료");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void cancel() {
		if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
			file2=chooser.getSelectedFile();
			file2.delete();
			JOptionPane.showMessageDialog(registMain, "선택한 파일이 삭제되었습니다");
		}
		
	}
	
	

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		
		if(obj==btn[0]) {	//등록
			regist();
			
		}else if(obj==btn[1]) {	//저장
			if(JOptionPane.showConfirmDialog(registMain, "파일로 저장하시겠습니까?")==JOptionPane.OK_OPTION){
				saveFile();
			}
		
		}else if(obj==btn[2]) {	// 수정
			if(JOptionPane.showConfirmDialog(registMain, "수정하시겠습니까?")==JOptionPane.OK_OPTION){
				edit();
			}
			
		}else if(obj==btn[3]) {	//삭제
			if(JOptionPane.showConfirmDialog(registMain, "삭제하시겠습니까?")==JOptionPane.OK_OPTION){
				del();
			}

		}else if(obj==btn[4]) {		//조회
			sumPrice();
			
		}else if(obj==btn[5]) {		//취소
			if(JOptionPane.showConfirmDialog(registMain, "파일등록을 취소하시겠습니까?")==JOptionPane.OK_OPTION){
				cancel();
			}
			
		}else if(obj==btn[7]) {		//종료
			if(JOptionPane.showConfirmDialog(registMain, "종료하시겠습니까?")==JOptionPane.OK_OPTION){
				registMain.setVisible(false);
			}
		}
	}
	
	
	
	
	
}
