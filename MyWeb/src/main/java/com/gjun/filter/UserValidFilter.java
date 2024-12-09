package com.gjun.filter;

import java.io.IOException;


import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//抽象類別(公版) 繼承 實作抽象方法
//攔截使用者登入憑證
public class UserValidFilter  extends OncePerRequestFilter{
	//可以使用Logger
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response ,
			FilterChain filterChain) throws ServletException, IOException{
		//TODO 借助Request 取出Cookie(s) 看看是否有登入驗證發出的憑證(Cookie)
		System.out.println("UserValidFilter doFilterInternal...");
		//取出Cookies(借助Request物件)
		Cookie[] cookies=request.getCookies();
		if(cookies==null) {
			//沒有登入憑證
			response.sendError(401, "驗證無法通過!!");
			return;
		}else {
			//掃描Cookies是否有合法的Cookie
			boolean valid=false;
			for(Cookie c :cookies) {
				if(c.getName().equals(".cred")) {
					// 有一個名稱符合的Cookie
					//需要進一步Match Session狀態管理
					//取出既有的Session
					HttpSession session=request.getSession(false);
					if(session==null) {
						response.sendError(401, "驗證無法通過!!");
						return;
					}else {
						//有Session
						//取出Session中的登入憑證
						String cred=(String)session.getAttribute(".cred");
						if(cred==null) {
							response.sendError(401, "驗證無法通過!!");
                            return;
						}else {
							//有Session中的登入憑證
                            //比對Cookie中的登入憑證
                            if (cred.equals(c.getValue())) {
                                //驗證通過
                                valid=true;
                                break;
						}else {
							//response.sendError(401, "驗證無法通過!!");
                            return;
							}
						}
					}
				}
				if (!valid) {
					response.sendError(401, "驗證無法通過!!");
					return;				
			}
		}
		
		//執行FilterChain 往下走...
		filterChain.doFilter(request, response); //繼續往下走...
		
		}			
	}

}
