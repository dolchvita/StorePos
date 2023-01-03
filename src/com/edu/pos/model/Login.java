package com.edu.pos.model;

public class Login {

	private int login_idx;
	private String id;
	private String pass;
	private String regdate;
	
	
	
	public int getLogin_idx() {
		return login_idx;
	}
	public void setLogin_idx(int login_idx) {
		this.login_idx = login_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
}
