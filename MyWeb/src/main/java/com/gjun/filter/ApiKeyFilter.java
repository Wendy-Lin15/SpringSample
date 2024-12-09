package com.gjun.filter;

import java.io.IOException;

import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//被一個Spring Bean(Wrapper)包裹 自訂建構注入必要資訊
//繼承Spring OncePerRequestFilter 抽象類別
public class ApiKeyFilter extends OncePerRequestFilter {
	private Environment env; //網站系統環境物件
	//建構子注入 網站系統環境物件
	public ApiKeyFilter(Environment env) {
			this.env = env;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//取出前端Request 封裝 Header(s)中的ApiKey(Key Name)
		String keyName=env.getProperty("app.api.key.name"); //naming Service
		//輸出
		System.out.println("ApiKey Name 名稱 :"+keyName);
		//透過Http Request 取出ApiKey
		String keyValue=request.getHeader(keyName);
		//輸出
		System.out.println("ApiKey Value 值 :"+keyValue);
		if(keyValue!= null) {
			//TODO 驗證是否有效?
		}else {
			//非法
		}
		
		}
		

}
