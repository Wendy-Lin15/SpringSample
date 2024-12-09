package com.gjun.springBean;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.gjun.services.UbikeService;

//與通訊協定有關的Spring Bean
@Configuration
public class HttpConfig {
	//注入logger物件模組
	private static Logger Logger=LoggerFactory.getLogger(UbikeService.class); //稽核對象 類別中繼資料(Class)
	
	//生產RestTemplate物件模組 
	//使用RestTemplateBuilder(參數注入)建立RestTemplate物件
	//參數採用IoC方式注入
	@Bean
	@RequestScope
	public RestTemplate createRestTemplate(RestTemplateBuilder builder) {
		
		//稽核資訊
		Logger.info("RestTemplate 個體物件模組配置起來");
		
		//建立RestTemplate物件 連接最大時間
		builder.setConnectTimeout(Duration.ofSeconds(20));
		//產生RestTemplate物件
		return builder.build();
	}
	
	

}
