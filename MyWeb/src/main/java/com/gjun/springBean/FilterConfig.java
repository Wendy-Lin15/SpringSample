package com.gjun.springBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.gjun.filter.ApiKeyFilter;
import com.gjun.filter.UserValidFilter;

//配置Filter 進行相關Middleware設定
@Configuration
public class FilterConfig {
	
	//Data Field 注入Environement網站系統環境物件
	@Autowired
	private Environment env;
	
	//Logger
	private static final Logger logger=LoggerFactory.getLogger(FilterConfig.class); 
	
	//配置User Login validation Filter
	@Bean
	@Scope("singleton")
	public FilterRegistrationBean<UserValidFilter> userValidFilter(){
		
		//建構FilterRegistrationBean物件
		FilterRegistrationBean<UserValidFilter> filterBean=new FilterRegistrationBean<UserValidFilter>();
		//設定Filter
		filterBean.setFilter(new UserValidFilter());
		//設定Filter Name
		filterBean.setName("UserValidFilter");
		//設定Filter Url Pattern(s) 配合資源檔設定 採用動態目錄設定
		filterBean.addUrlPatterns(env.getProperty("app.users.validation.path").split(";"));
		//設定Filter Order
		filterBean.setOrder(1);
		
		return filterBean;
	}
	
	//配置ApiKey Filter Bean
	@Bean
	@Scope("singleton") //共用的Bean
	public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter(){
			
		//記錄
		logger.info("ApiKeyFilter Bean 產生了...");
		// 建構FilterRegistrationBean物件
		FilterRegistrationBean<ApiKeyFilter> filterBean = new FilterRegistrationBean<ApiKeyFilter>();
		//設定Filter
		filterBean.setFilter(new ApiKeyFilter(env));
		// 設定Filter Name
		filterBean.setName("ApiKeyFilter");
		// 設定Filter Url Pattern(s) 配合資源檔設定 採用動態目錄設定
		filterBean.addUrlPatterns(env.getProperty("app.api.root.path").split(";"));
		// 設定Filter Order
		filterBean.setOrder(2);
		return filterBean;
		}

}
