package com.edu.pos.model;

// 거래처 테이블을 담을 DTO
public class Vender {
	private int vender_idx;
	private String vender_name;
	
	
	public int getVender_idx() {
		return vender_idx;
	}
	public void setVender_idx(int vender_idx) {
		this.vender_idx = vender_idx;
	}
	public String getVender_name() {
		return vender_name;
	}
	public void setVender_name(String vender_name) {
		this.vender_name = vender_name;
	}
	
	
	
}
