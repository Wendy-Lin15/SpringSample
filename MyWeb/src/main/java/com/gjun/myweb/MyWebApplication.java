package com.gjun.myweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//應用系統啟動時進行初始化Deploy佈署(控制器/Spring Bean/Spring Service/Repository/Component...)
@SpringBootApplication
@ComponentScan(basePackages= {"com.gjun.controller","com.gjun.springBean","com.gjun.repository",
		"com.gjun.services" ,"com.gjun.component" })

public class MyWebApplication {
	
	//Entry Point(CLI啟動)
	public static void main(String[] args) {
		//Boot 網站系統
		SpringApplication.run(MyWebApplication.class, args);
	}

}
