package com.gjun.bean;
//JavaBean
public class Member implements java.io.Serializable {
	//Data Field
	private String userName;
	private String password;
	private String realName;
	private String email;
	//Property as setXxxx() and getXxxx() set/get->xxxxXxx=value
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
