package com.edu.pos.sub;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.edu.pos.model.TotalRecordModel;

public class RecordPage extends JPanel{
	JPanel p_container;
	JPanel p_north;
	JButton bt_update;
	
	JTable table;
	JScrollPane scroll;
	TotalRecordModel model;
	
	public SubMain subMain;
	public RecordPage(SubMain subMain) {
		this.subMain=subMain;
		
		p_container=new JPanel();
		p_container.setLayout(new BorderLayout());
		p_container.setBackground(Color.CYAN);
//		p_container.setPreferredSize(new Dimension());
		
		p_north=new JPanel();
		p_north.setPreferredSize(new Dimension(810,80));
		p_north.setBackground(Color.BLACK);
		
		bt_update=new JButton("동기화");
		p_north.add(bt_update);
		
		
		table=new JTable(model=new TotalRecordModel(this));
		scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(810,550));
		
		p_container.add(p_north, BorderLayout.NORTH);
		p_container.add(scroll);
		
		add(p_container);
		setBackground(Color.YELLOW);
		
		
		bt_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.totalRecordList=subMain.totalRecordDAO.selectAll();
				table.updateUI();
			}
		});
	}
}
