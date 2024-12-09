package com.gjun.springBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import com.gjun.bean.Company3;
import com.gjun.bean.HelloBean;
import com.gjun.bean.HelloCompanyBean3;

//HelloBean Spring Bean配置
@Configuration
public class HelloBeanConfig {
	
	//使用Method 註冊HelloBean 到Spring Web Container(容器或引擎)
	@Bean
	@Scope()	//預設生命週期範圍-Singleton(獨一Pattern)，沒寫@Scopeu,也是預設
	public HelloBean createHelloBean() {
		//建構一個HelloBean Instance
		HelloBean bean = new HelloBean();
		bean.setCompanyName("巨匠電腦");
		bean.setHelloString("您好!!");
		bean.setWho("Wendy");
		
		return bean;
	}
	
	//配置Company Bean
	@Bean
	@Scope("singleton")	//Global共用
	public Company3 createCompany() {
		//建構公司物件
		Company3 com = new Company3();
		com.setCompanyName("巨匠電腦");
		com.setWho("Lin ");
		return com;
	}
	
	//配置與公司行號物件Hello Bean 反轉一個物件(company)進入 實現IoC 注入控制反轉
	//使用參數注入(依賴配置好的Spring Bean)
	@Bean
	@RequestScope	//每一個注入配合Request 各自一個新物件，不共用
	public HelloCompanyBean3 createHelloCompany3(Company3 company) {
		HelloCompanyBean3 hm3 = new HelloCompanyBean3();
		hm3.setMessage("早安，你好");
		hm3.setCompany(company);	//setXXX() 參數注入依賴物件Property Injection
		return hm3;
	}
	

}
