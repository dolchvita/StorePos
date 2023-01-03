package com.edu.pos.sub;

import javax.swing.JPanel;

// PosPage에 부착될 모든 패널들의 최상위 부모패널
public class Panel extends JPanel{
	PosPage posPage;
	
	
	public Panel(PosPage posPage) {
		this.posPage=posPage;
		
	}
}
