package com.gjun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.bean.Customers;


import jakarta.servlet.http.HttpServletRequest;

//客戶資料維護控制器(Controller)
//POJO(Plain Old Java Object)
@Controller
@RequestMapping("/customers")	//做第一層端點
public class CustomersController {
	
	//Data Field
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	public CustomersController() {
		System.out.println("CustomersController啟動...");
	}
	
	
	//Method as Action
	//找出客戶所有資料--查詢(第一次 往往超連結)--採用GET/POST
	@RequestMapping(path="/all", method= {RequestMethod.GET,RequestMethod.POST})
	public String customersAll(HttpServletRequest request, Model model) {
		List<Customers> result =new ArrayList<>();
		//判斷是否採用POST 進行資料維護
		if(request.getMethod().equals("POST")) {
			//TODO 查詢客戶所有資料
			System.out.println("查詢客戶所有資料...");
			//SQL查詢敘述
			String sql = "SELECT CustomerID, CompanyName, Address, Phone, Country FROM customers";
			//執行查詢
			result = jdbcTemplate.query(sql, 
					//RowMapper Interface 查詢結果逐筆進入callback(客製化將查詢如何如何處理)
					//一個介面一個抽象方法 可以使用Lambda Expression
					(rs,num)->{
						//查詢結果逐筆進來
						Customers customer=new Customers();
						//封裝相對記錄欄位內容
						customer.setCustomerID(rs.getString("CustomerID"));
						customer.setCompanyName(rs.getString("CompanyName"));
						customer.setAddress(rs.getString("Address"));
						customer.setPhone(rs.getString("Phone"));
						customer.setCountry(rs.getString("Country"));
						return customer;
					}
					);
			System.out.println("查詢結果筆數:"+result.size());
			//查詢結果轉換成離線物件模組Model(執行階段持續到View Page)
			//TODO 適當時間進入View Page 才來採用Model 進行參考
		}
		
		model.addAttribute("customers", result);
		//調用頁面(透過Thymeleaf template engine)
		return "customersall";
	}

}
