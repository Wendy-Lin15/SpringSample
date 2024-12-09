package com.gjun.component;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gjun.bean.UbikeData;

//預設為Singleton Service Component(Spring Component)
@Service
public class UbikeComponent {
	//注入RequestScope Bean
	@Autowired
	private RestTemplate restTemplate; //Http Client api
	@Autowired
	private Environment env;
	
	//注入Logger
	private static Logger Logger = LoggerFactory.getLogger(UbikeComponent.class);
	
	//空參數建構子
	public UbikeComponent() {
		Logger.info("UbikeComponent Service is created....");	
	}
	
	//共用方法 透過專用的RestTemplate進行遠端Ubike API呼叫 
	public List<UbikeData> tpiUbikeRealData() {
		List<UbikeData> result=null;
		//透過網站環境物件獲取Ubike API URL
		String url=env.getProperty("app.ubike.tpi");
		Logger.info("RestTemplate: "+ restTemplate);
		//透過RestTemplate進行遠端API呼叫 提出Request and Response
		ResponseEntity<List<UbikeData>> responseEntity=restTemplate.exchange(url,HttpMethod.GET, null,
				//建構一個匿名類別 繼承了ParameterizedTypeReference 實作{}
				new ParameterizedTypeReference<List<UbikeData>>() {
                });
		//問看看狀態碼Http status code 200 OK
		if(responseEntity.getStatusCode()==HttpStatus.OK) {
			//取出JSON String被反序列化封裝List<> 集合物件
			result=responseEntity.getBody();			
		}	
		return result;
	}
	
	//傳遞行政區域進行相關即時資訊查詢
	public List<UbikeData> ubikeQryByArea(String area){
		//呼叫自訂方法 去呼喚Ubike 所有資料
		List<UbikeData> result=tpiUbikeRealData();
		//商業邏輯 Query區域........
		List<UbikeData> areaResult=new java.util.ArrayList<>();
		//過濾出特定區域站點資料Stream API(JDK 8) 不使用
		for(UbikeData ubikeData : result) {
			if(ubikeData.sarea.equals(area)) {
				// 重新收集起來
				areaResult.add(ubikeData);				
			}			
		}
		return areaResult;
	}
	
	
	
}
