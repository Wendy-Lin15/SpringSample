package com.gjun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gjun.bean.HelloBean;
import com.gjun.bean.HelloCompanyBean3;

//各類型打招呼的端點
@Controller
public class HelloController {
	/*(方法1)利用DataField Injection
	//Data Field(Attribute)  Field Injection(DI)	
	@Autowired
	private HelloBean hb;	//預設生命週期是共用Global(全域共用物件)*/
	
	
	//(方法2)利用建構子注入，依賴物件Constructor Injection
	private HelloBean hb;	//Data Field
	public HelloController(@Autowired HelloBean hb) {
		this.hb=hb;		
	}
	
	//Data Field
	@Autowired
	private HelloCompanyBean3 helloCompany;
	
	//Action 打招呼
	@RequestMapping(path="/hello/bean", method= {RequestMethod.GET})
	@ResponseBody
	public String helloBeanPage() {
		//換一個公司名子
		hb.setCompanyName("巨匠美語");
		return hb.getCompanyName();
	}
	
	//參數注入Parameter Injection Model(Entity class)
	@RequestMapping(path="/hello/bean2", method= {RequestMethod.GET})
	public String helloBeanPage2(Model model) {
		//HelloBean 傳遞Model to View Page(借助Template engine 持續Model進行後端渲染)
		model.addAttribute("hello",hb);	//實際上是屬於Request Scope
		return "showHello";	//Dispatcher View Page
	}
	
	//提供瀏覽器請求位址 驗證HelloCompany DI 與 Company物件(IoC控制注入反轉)
	//直接回字串 要配置  字串回到前段瀏覽器(當作Response Body) 否則交給樣板引擎 當作網頁.....
	@RequestMapping(path="/hello/company", method= RequestMethod.GET)
	@ResponseBody
	public String helloCompanyPage() {
		String content = helloCompany.sayHello(); //呼叫是窗口物件(裡面有Company存在)
		return content;
	}

}
