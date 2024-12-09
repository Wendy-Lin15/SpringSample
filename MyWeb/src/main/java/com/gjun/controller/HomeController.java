package com.gjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//控制器 設計首頁入口的Action
@Controller
public class HomeController {
	
	public HomeController() {
		System.out.println("Home Controller 配置了...");
	}
	
	//(1)Action 端點入口(程序)-Method
	//(2)描述端點End Point(http://hosted/xxx0)
	//(3)允許前端可以採用Request method-GET(八種之多)
	@RequestMapping(path={"/","home"}, method= {RequestMethod.GET})
	@ResponseBody		//把return的值，帶到response的body裡 回到瀏覽器端
	public String index() {
		return "<font size='6' color='red'>Hello World!</font>";
	}
	
	//Hello Action 不使用@ResponseBody 回應字串 預設借助頁面解析器?進行Parse
	@RequestMapping(path={"/homePage"}, method= {RequestMethod.GET})
	public String hello() {
		return "home";	//調用頁面(借助樣板引擎Template Engines)
	}

}
