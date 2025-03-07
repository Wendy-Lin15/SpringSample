package com.gjun.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gjun.bean.HelloBean;

//POJO RESTful Service
@RestController
@RequestMapping(path="api/v1/hello")
public class HelloService {
	
	public HelloService() {
		System.out.println("Hello Service....");
	}
	
	//打招呼服務 Request Method-GET 查詢/回應訊息(Content-type)用text回應
	@GetMapping(path="/sayhello")
	public String sayhello() {
		return "用字串回應 How are you?";
	}
	
	//傳遞一個參數進來(URL/Header)採用URL QueryString http://localhost/xxxx?w=eric
	//produces 決定回應的Response Header Content-Type:application/json (物件序列化成json文件)
	@GetMapping(path="/entity", produces= {"application/json; charset=UTF-8"})
	public HelloBean helloEntity(@RequestParam("w") String who) {
		//建構一個打招呼的物件
		HelloBean hello=new HelloBean();
		hello.setCompanyName("巨匠電腦");
		hello.setWho(who);
		hello.setHelloString("國慶日快樂");
		return hello;	//有預設Middleware序列化成json文件(文字格式) 主要是配合Response Content-Type
	}
	
	//傳遞一份打招呼資訊JSON 進行處理(新增或修改) 修改 PUT
	//要求前端採用JSON送進來 Request Header Context-Type:application/json
	@PutMapping(path="/entity/modify", consumes="application/json",produces="application/json")
	public HelloBean helloReceive(@RequestBody HelloBean bean) {
		bean.setHelloString("Say Hello! From Json data");
		bean.setWho("John");
		return bean;		
	}

}
