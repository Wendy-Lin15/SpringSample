package com.gjun.bean;
//JavaBean(POJO) Mapping to Table Customers
public class Customers implements java.io.Serializable{
	//Data Field
	private String customerID;
	private String companyName;
	private String address;
	private String phone;
	private String country;
	//get&set
	public String getCustomerID() {
		return customerID;
	}
	//Property Injection Setter Method customerID=value
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}
