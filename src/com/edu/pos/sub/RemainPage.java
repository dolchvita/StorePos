package com.edu.pos.sub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edu.pos.model.Shop;
import com.edu.pos.model.StockModel;

public class RemainPage extends JPanel{
	public SubMain subMain;
	
	JPanel p_container;
	JPanel p_north;
	JComboBox combo;
	JButton bt_excel;
	JButton bt_update;
	
	
	// 엑셀 파일에 들어갈 데이터
	List stockList;
	JFileChooser chooser;
	
	JTable table;
	JScrollPane scroll;
	StockModel model;
	
	
	public RemainPage(SubMain subMain) {
		this.subMain=subMain;
		
		p_container=new JPanel();
		p_container.setLayout(new BorderLayout());
		p_container.setBackground(Color.CYAN);
//		p_container.setPreferredSize(new Dimension());
		
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(810,80));
		p_north.setBackground(Color.PINK);
		
		combo=new JComboBox();
		combo.addItem("보기 선택");
		combo.addItem("재고순");
		
		bt_excel=new JButton("엑셀출력");
		bt_update=new JButton("동기화");
		chooser=new JFileChooser("C:/java_workspace2/data/project1230/excel");
		
		p_north.add(combo);
		p_north.add(bt_excel);
		p_north.add(bt_update);
		
		table=new JTable(model=new StockModel(this));
		scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(810,550));
		
		p_container.add(p_north, BorderLayout.NORTH);
		p_container.add(scroll);
		
		add(p_container);
		
		setBackground(Color.RED);
		
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String str=(String)e.getItem();
					
					if(str.equals("재고순")) {
						model.shopList=subMain.shopDAO.orderBy();
						table.updateUI();						
					}else {
						getList();
					}
				}
				
				
			}
		});
		
		bt_excel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		
		bt_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList();
			}
		});
	}

	
	public void saveFile() {
		int result=chooser.showOpenDialog(subMain);
		
		if(result!=JFileChooser.APPROVE_OPTION){
			return;
		}
		
		File file=chooser.getSelectedFile();
		
		HSSFWorkbook book=new HSSFWorkbook();
		HSSFSheet sheet=book.createSheet();
		HSSFRow row=sheet.createRow(0);
		
		// 일단 엑섹 column 채우기 (테이블과 비슷)
		String[] column= {"거래처","상품명","소비자가","바코드","재고수"};
		
		for(int i=0; i<column.length; i++) {
			HSSFCell cell=row.createCell(i);
			cell.setCellValue(column[i]);
		}
		
		
		stockList=subMain.shopDAO.selectCount();
		
		// 이제 엑셀에 실제 데이터 채우기
		for(int i=0; i<stockList.size(); i++) {
			Shop shop=(Shop)stockList.get(i);
			
			// 한 줄당 셀을 채운다
			HSSFRow record=sheet.createRow(i+1);		//컬럼 다음 줄
			
			HSSFCell vender_name=record.createCell(0);
			HSSFCell shop_name=record.createCell(1);
			HSSFCell price=record.createCell(2);
			HSSFCell code=record.createCell(3);
			HSSFCell cnt=record.createCell(4);
			
			vender_name.setCellValue(shop.getVender().getVender_name());
			shop_name.setCellValue(shop.getShop_name());
			price.setCellValue(shop.getPrice());
			code.setCellValue(shop.getCode());
			cnt.setCellValue(shop.getCount());
		}
		
		FileOutputStream fos=null;
		
		try {
			// 지정한 곳에 파일 저장됨!
			fos=new FileOutputStream(file);
			book.write(fos);
			JOptionPane.showMessageDialog(this, "엑셀파일 저장 완료");
			
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
	
	
	
	public void getList() {
		model.shopList=subMain.shopDAO.selectCount();
		table.updateUI();
	}
	
}
