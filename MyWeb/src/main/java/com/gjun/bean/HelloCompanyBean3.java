package com.gjun.bean;

public class HelloCompanyBean3 implements java.io.Serializable{
	//Data Field
	private String message;
	private Company3 company; //跟公司物件有關
	
	public HelloCompanyBean3() {
		System.out.println("HelloCompanyBean3 產生...");
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Company3 getCompany() {
		return company;
	}
	public void setCompany(Company3 company) {
		this.company = company;
	}
	
	//取得公司打招呼資訊
	public String sayHello() {
		String msg = String.format("公司:%s 說:%s", company.getCompanyName(),this.message);
		return msg;
	}

}
