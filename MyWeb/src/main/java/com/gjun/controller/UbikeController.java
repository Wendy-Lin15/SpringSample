package com.gjun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.bean.UbikeData;
import com.gjun.component.UbikeComponent;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/ubike")
public class UbikeController {
	
	//Data Field 注入一個Global Service Component(Singleton)
	@Autowired
	private UbikeComponent ubikeComponent; //Singleton Service Component
	
	//提供一個View Page 進行Ubike行政區域查詢
	@RequestMapping("/area/qry")
	public String  ubikeArea() {
		return "ubikearea";	//借助Thymeleaf View Page
	}
	
	//提供一個View Page 進行Ubike所有資料查詢
	//注入Persistence state to Request 的Model Interface
	@RequestMapping("/all")
	public String ubikeAll(Model model) {
		//呼叫Instance Service Component Method
		List<UbikeData> result=ubikeComponent.tpiUbikeRealData();
		System.out.println("Ubike 基座數: "+result.size());
		model.addAttribute("result", result);	//Model to View Page(預設為Request Scope
		//持續查詢結果Model to View Page進行SSR(Server Side Rendering)渲染
		return "ubikeAll";  //借助Thymeleaf View Page
	}
	
	//提供一個 View Page進行Ubike Realtime行政區域查詢
	@RequestMapping(path="/area/realtime/result",method= {RequestMethod.POST, RequestMethod.GET})
	public String ubikeQryArea(HttpServletRequest request,Model model) {
		//調用頁面 進行Post back
		if(request.getMethod().equals("POST")) {
			//進行資料查詢 呼叫自訂Service Component Method
			String area = request.getParameter("area");
			
			List<UbikeData> result = ubikeComponent.ubikeQryByArea(area);
			System.out.println("Ubike 基座數: "+result.size());
			model.addAttribute("result", result); //Model to View Page) 預設為Request Scope
		}
		
		return "ubikeRealtime"; //借助Thymeleaf View Page"
	}
	
	
	

}
