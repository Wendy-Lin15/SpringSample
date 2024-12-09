package com.gjun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gjun.bean.Customers;
import com.gjun.bean.Message;

//提供針對客戶資料維護
@RestController
@RequestMapping("/api/v1/customers")
public class CustomersService {
	//
	@Autowired
	private JdbcTemplate jdbctemplate;
	//傳遞客戶資料進來 進行資料修改
	//回應訊息是有包含Response物件 與封裝JavaBean-Json
	//Request Method-PUT
	@PutMapping(path="/update", consumes="application/json", produces="application/json")
	public ResponseEntity<Message> customersUpdate(@RequestBody Customers customers) {
		String sql="Update Customers Set CompanyName=?, Address=?, Phone=?, Country=? Where CustomerID=?";
		Message message = new Message();
		ResponseEntity<Message> response=null;
		
		try {
			int row = jdbctemplate.update(sql,
					//使用Lambda Expression 實作 PreparedStatementSetter setValues method
					(st)->{
						st.setString(1, customers.getCompanyName());
						st.setString(2, customers.getAddress());
						st.setString(3, customers.getPhone());
						st.setString(4, customers.getCountry());
						st.setString(5, customers.getCustomerID());
					}
					);
			//是否更新到客戶資料
			if(row==1) {
				message.setCode(200);
				message.setMessage("客戶資料更新成功!");
				response=ResponseEntity.ok(message);
			
			}else {
				message.setCode(400);
				message.setMessage("客戶資料更新失敗，客戶資料不存在!!");
				response=ResponseEntity.badRequest().body(message);
				
			}
			
		}catch(DataAccessException e) {
			message.setCode(500);
			message.setMessage("客戶資料更新失敗，系統異常!!");
			response=ResponseEntity.status(500).body(message);
			
		}
		return response;
	}

	//刪除客戶資料 透過path當作參數傳遞客戶編號 
	//Request Method-DELETE
	@DeleteMapping(path="/delete/{cid}/rawdata", produces="application/json")
	public ResponseEntity<Message> customersDelete(@PathVariable("cid") String customerId) {
		//借助注入進來的JdbcTemplate 進行刪除作業
		ResponseEntity<Message> response=null;
		String sqlString="Delete Customers Where CustomerID=?"; //採用參數架構
		try {
			int row = jdbctemplate.update(sqlString, 
					//PreparedStatementSetter Lambda Expression
					(st)->{
						//設定參數
						st.setString(1, customerId);
					}
					);
			//判斷是否有刪除到資料
			if(row==1) {
				Message message=new Message();
				message.setCode(200);
				message.setMessage("客戶編號:" + customerId+" 資料刪除成功!!");
				response = ResponseEntity.ok(message);	//回應http status code 200
				
			}else {
				Message message=new Message();
				message.setCode(400);
				message.setMessage("客戶編號:" + customerId+" 刪除不到資料!!");
				response = ResponseEntity.badRequest().body(message);	//回應http status code 400
				
			}
		}catch(DataAccessException e) {
			Message message = new Message();
			message.setCode(500);
			message.setMessage("客戶編號:" + customerId+" 刪除資料異常 可能有訂單!!");
			response = ResponseEntity.status(500).body(message); //回應http status code 500
			
		}
		return response;
	}
	
}
