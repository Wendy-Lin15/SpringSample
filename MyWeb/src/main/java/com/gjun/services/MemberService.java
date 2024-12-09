package com.gjun.services;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gjun.bean.Member;
import com.gjun.bean.Message;
import com.gjun.repository.MemberRepository;

//會員資料維護服務
@RestController
@RequestMapping(path="/api/v1/member")
public class MemberService {
	//Data Field
	//注入依賴物件(DI) 自訂的DAO for Member
	@Autowired
	private MemberRepository memberRep;
	
	//傳遞會員資料 進行資料修改(PUT) 傳遞一份json
	//原始使用者名稱 透過Path參數傳遞
	@PutMapping(path="/update/{memberid}/rawdata", produces="application/json",
			consumes="application/json")
	public ResponseEntity<Message> memberUpdate(@RequestBody Member member,
			@PathVariable("memberid") String sourceId) {
		//借助 注入進來的Repository
		Message message=null;
		ResponseEntity<Message> response=null;
		try {
			//設定原始來源的User Name
			memberRep.setSourceId(sourceId);
			boolean r=memberRep.update(member);
			if(r) {
				//更新成功
				message=new Message();
				message.setCode(200);
				message.setMessage("會員資料更新完成!!");
				//絕對回應的Http status code 200
				response=new ResponseEntity(message,HttpStatusCode.valueOf(200));
			}else {
					message=new Message();
					message.setCode(400);
					message.setMessage("更新不到資料!!");
					//絕對回應的Http status code 400
					response=new ResponseEntity(message,HttpStatusCode.valueOf(400));
				}
			
		} catch (SQLException e) {
			message=new Message();
			message.setCode(400);
			message.setMessage("會員資料更新失敗!!");
			//絕對回應的Http status code 400
			response=new ResponseEntity(message,HttpStatusCode.valueOf(400));
		}
		return response;
	}

}
