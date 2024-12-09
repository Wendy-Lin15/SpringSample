package com.gjun.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gjun.bean.Member;
import com.gjun.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//配置這一個類別為控制器
//POJO Plain Old Java Object
@Controller
@RequestMapping(path="/users")
public class UsersController {
	
	/*//Data Field 注入DataSource(使用介面多型化架構)
	@Autowired	//後來在Repository已使用了
	private DataSource datasource;*/
	
	//注入Member Repository
	@Autowired
	private MemberRepository memberRep;
	
	//注入網站系統環境物件
	@Autowired
	private Environment env;
	
	//提供一個Action 調用頁面(GET)與進行會員註冊作業(form 採用POST)path=/users/register
	//使用參數架構 注入Java Web Base-HttpServletRequest interface--用來判斷傳送方式
	//表單頁面表單欄位 name attribute對應到注入的JavaBean Member Property 會自動封存資訊
	@RequestMapping(path="/register", method= {RequestMethod.GET,RequestMethod.POST})
	public String register(HttpServletRequest request, Model model, Member member) {
		/*member.setUserName("Eric");
		System.out.println(member.getUserName());*/
		
		String message=null;
		//第一次超連結過來 還是表單post back回來?
		if(request.getMethod().equals("POST")) {
			//進行會員註冊作業
			System.out.println("進行註冊..."+ member.getUserName());
			//進行資料新增作業(會員註冊)
			try {
				memberRep.insert(member);
				message = "會員註冊成功";
			}catch(SQLException e) {
				System.out.println("錯誤: "+ e.getMessage());
				message = "註冊失敗";
			}

			//狀態持續state(透過thymeleaf template engine)
			model.addAttribute("message",message);
			model.addAttribute("member",member);
			
			/*	//採用Http Request封裝資訊底層寫法
			String userName = request.getParameter("userName");
			System.out.println("Http Request底層資訊:" +userName);
			 */
		}

		return "register";
	}

	
	//查詢作業-Login 登入驗證 方法直接下參數名稱應對表單欄位的name attribute 自動封存
	@RequestMapping(path="/login", method= {RequestMethod.GET,RequestMethod.POST})
	public String login(String userName, String password, HttpServletRequest request, HttpServletResponse response, Model model) {
		String message=null; //訊息狀態到頁面
		//調用頁面
		//判斷是否為post back
		if(request.getMethod().equals("POST")) {
			System.out.println("使用者名稱: "+userName);
			// 進行資料驗證
			String key = String.format("%s;%s",userName,password);
			//使用DAO Repository元件
			try {
				Member members= memberRep.selectForObject(key);
				if(members!=null) {
					//驗證成功 發出前端憑證 進行後端Session 狀態管理
					//建構一個前端憑證Cookie
					Cookie cookie= new Cookie(".cred",userName); //建立採用Hash 雜湊值
					///設定Cookie的有效時間
					cookie.setMaxAge(60*5); //5分鐘有效 (最大間隔時間)
					//Cookie 採用HttpOnly安全性
					cookie.setHttpOnly(true);
					//限制同一個Site有效 跟Response Header有關
					cookie.setPath("/");
					//透過Response加入這一個Cookie
					response.addCookie(cookie);
					//Session Enabled ??? 配合前端具有Cookie Container容器
					//進行後端狀端管理 採用Http Session(配合前端請求Request 看看要產生新的或者既有的Session)
					HttpSession session = request.getSession(); //有 問出既有的 沒有 就產生一個新的
					//將Session id 設定到Cookie
					session.setAttribute(".cred", userName);  //setAttribute 設定屬性 包含與新增或者修改
					
					System.out.println("驗證成功!!!");	
					message="登入成功!!";
				}else {
					//TODO 驗證失敗
					System.out.println("驗證失敗~");
					message="登入失敗!!";
				}
			} catch (SQLException e) {
				
			}
		}
		//State Persist 狀態持續到Page去
		model.addAttribute("message", message);
		//調用頁面
		return "login";
	}
	
	
	
	//登出作業
	@GetMapping(path="/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response ) {
		//前提 前端憑證狀態(Cookies)
		Cookie[] cookies=request.getCookies();
		if(cookies != null) {
			for(Cookie c:cookies){
				System.out.println("Cookie Name:" + c.getName()+ " 登出作業");
				if(c.getName().equals(".cred")) {
					//後端Session Attribute 移除
					//透過相對Request 取出既有Session(可能有 可能沒有)
					HttpSession session = request.getSession(false);
					if(session != null) {
						// 移除Session Attribute
						session.removeAttribute(".cred");
					}
					//設定同名稱的Cookie 立即失效
					Cookie cookie=new Cookie(".cred" , null);
					cookie.setMaxAge(0); //立即失效
					cookie.setPath("/"); //限制同一個Site有效
					response.addCookie(cookie);				
					
					break;
				}
			}
		}
		//是否有後端狀態 Session存活一個Session Attribute
		
		return "logout";
	}
	
	
	
	//會員資料查詢Action
	@RequestMapping(path="/qry", method= {RequestMethod.GET ,RequestMethod.POST})
	public String usersQry(String userName, String password, HttpServletRequest request, Model model) {
		//State狀態
		Member member = null;
		String message = null;
		//調用頁面
		if(request.getMethod().equals("POST")) {
			//底層Http封裝 直取表單欄位
			String pwd= request.getParameter("password");
			//進行會員查詢作業
			System.out.println("使用者姓名: "+userName+" ,直接取:"+password+" ,底層取:"+pwd);
			
			//整理參數
			String key=String.format("%s;%s" , userName, password);
			//使用DAO
			try {
				//查詢結果產生的Model(物件)runtime
				member=memberRep.selectForObject(key);
				if(member==null) {
					//查無該使用者
					message="查無該使用者 "+userName+" 的會員資料!!";
				}
				System.out.println("查詢結果: "+member);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				message="後端DB伺服器有問題"; 
			}			
		}
		//取出更新會員資料的服務地址
		String updateServiceURL = env.getProperty("app.users.updateservice");
		
		//State Persist 狀態持續到Page去
		model.addAttribute("message", message);
		model.addAttribute("member",member);
		model.addAttribute("updateService",updateServiceURL);
		return "usersqry"; //透過Thymeleaf engine
	}
	
	
	
	
}
