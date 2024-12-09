package com.gjun.bean;

//公司資訊JavaBean
public class Company3 implements java.io.Serializable{
	//Data Field
	private String who;
	private String companyName;
	
	public Company3() {
		System.out.println("Company3 Bean 產生...");
	}
	
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
		

}
