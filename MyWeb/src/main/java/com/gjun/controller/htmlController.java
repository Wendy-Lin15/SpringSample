package com.gjun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/htmlTest")
public class htmlController {
	
	public htmlController(){
		System.out.println("Html Controller 配置了...");
	}
	
	//(1)Action 端點入口(程序)-Method
	//(2)描述端點End Point(http://hosted/xxx0)
	//(3)允許前端可以採用Request method-GET(八種之多)
	@RequestMapping(path = "/umeis", method= RequestMethod.GET)
	public String umeis() {
		return "umeisHTML";	//調用頁面(借助樣板引擎Template Engines)
	}
	
	@RequestMapping(path = "/christ2", method= RequestMethod.GET)
	public String chris2() {
		return "CH2-text";
	}
	
	@RequestMapping(path = "/cardEvent", method= RequestMethod.GET)
	public String cardEvent() {
		return "CH5-5Card";
	}
	

}
