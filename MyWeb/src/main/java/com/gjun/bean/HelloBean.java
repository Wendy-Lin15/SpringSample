package com.gjun.bean;

//Hello JavaBean
public class HelloBean implements java.io.Serializable {
	//Data Field(Attribute)--封裝性的屬性
	private String companyName;
	private String helloString;
	private String who;
	
	public HelloBean() {
		System.out.println("Hello Bean 產生...");
	}
	
	//setter and getter
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getHelloString() {
		return helloString;
	}
	public void setHelloString(String helloString) {
		this.helloString = helloString;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}		
	

}
