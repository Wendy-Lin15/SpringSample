package com.gjun.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gjun.bean.Message;
import com.gjun.bean.UbikeData;

//串接台北市Ubike資料服務
@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/api/v1/ubike")
public class UbikeService {
	//Data Field(Attribute) 注入RestTemplate物件模組
	//Field Injection(屬性注入)
	@Autowired
	private RestTemplate restTemplate; //Request Scope 被請求 呼叫產生一個物件模組
	
	//注入UBike Service URL
	@Value("${app.ubike.tpi}")
	private String ubikeServiceURL;
	
	//Action Method(輸入行政區域--相關Ubike站點資料)
	//行政區域採用Path Variable方式進行傳遞
	@GetMapping(path="/qry/{area}/rawdata", produces="application/json")
	public ResponseEntity ubikeQry(@PathVariable("area") String area) {
		
		ResponseEntity response=null;
		
		//串接台北市Ubike資料服務(api)
		//String jsonString= restTemplate.getForObject(ubikeServiceURL,String.class);  //無法直接轉換List<UbikeData>
		
		ResponseEntity<List<UbikeData>> entity=restTemplate.exchange(ubikeServiceURL,HttpMethod.GET, null,
				//建構一個匿名類別 繼承了ParameterizedTypeReference 實作{}
				new ParameterizedTypeReference<List<UbikeData>>() {
                });
		//透過ResponseEntity取得List<UbikeData>資料(Response Body(Content))
		List<UbikeData> ubikeDatas=entity.getBody();		
		
		//商業邏輯 Query區域........
		List<UbikeData> result=new ArrayList<>();
		//過濾出特定區域站點資料 Stream API
		ubikeDatas.stream().forEach(
				//Lambda Expression(Consumer Function Interface)
				(ubikeData) -> {
			if (ubikeData.sarea.equals(area)) {
				//重新收集起來
				result.add(ubikeData);
			}
		});
		//判斷是否找到資料
		if(result.size()==0) {
			//找不到資料
			Message message=new Message();
			message.setCode(400);
			message.setMessage("找不到相關站點資料");
			response=ResponseEntity.badRequest().body(message);
		}else {
			//找到資料
			response = ResponseEntity.ok(result);
			
		}
		return response;
				
		
	}
	

}
