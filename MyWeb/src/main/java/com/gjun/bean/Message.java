package com.gjun.bean;
//訊息Bean
public class Message implements java.io.Serializable {
	private int code;
	private String message;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
