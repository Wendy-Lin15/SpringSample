package com.gjun.component;
//Listener聆聽登入驗證成功後端進行Session管理(Attribute)--稽核用
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

//Java Servlet API 使用@WebListener進行配置

@Component
public class LogonHandler implements HttpSessionAttributeListener{

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// 聆聽是否登入成功
		if(se.getName().equals(".cred")) {
			System.out.println("有人登入了...");
			//TODO 進行稽核紀錄產生Login logger Table 新增
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// 聆聽是否登出成功
		if(se.getName().equals(".cred")){
			System.out.println("有人登出了...");
			//TODO 進行稽核紀錄產生Login logger Table 修改
		}
	}

	

}
