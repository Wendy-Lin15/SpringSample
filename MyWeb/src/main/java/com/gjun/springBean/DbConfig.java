package com.gjun.springBean;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

//註冊關於資料庫存取應用模組Spring Bean
@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class DbConfig {
	//Data Field 注入resource property name  //${property name}是Spring EL(Expression)
	@Value("${spring.database.url}")
	private String url;
	@Value("${spring.database.username}")
	private String username;
	@Value("${spring.database.password}")
	private String password;
	
	//注入Environement網站系統環境物件
	@Autowired
	private Environment env;
	
	
	//註冊DataSource(生產連接物件連接工廠) 共用Global-Singleton Pattern
	@Bean
	@Qualifier("gjun")
	@Scope("singleton")
	public DataSource createDataSource() {
		//建構for mssql DataSource(for sql server)
		SQLServerDataSource datasource =new SQLServerDataSource();
		//注入相關屬性url/user name/ password
		datasource.setURL(url);
		datasource.setUser(username);
		datasource.setPassword(password);
		System.out.println("DataSource連接工廠產生了..." + datasource.getURL());
		return datasource;
	}
	
	//第二套DataBase(Northwnd)
	//使用介面多型化
	@Bean
	@Qualifier("northwnd") //指定Bean Id
	@Scope("singleton") //共用物件模組
	public DataSource createNorthwndDataSource() {
		//建構for mssql DataSource(for sql server)
		SQLServerDataSource datasource=new SQLServerDataSource();
		//注入相關屬性url/user name/ password
		datasource.setURL(env.getProperty("app.database.url")); //Property Injection
		datasource.setUser(env.getProperty("app.database.username"));
		datasource.setPassword(env.getProperty("app.database.password"));
		System.out.println("Northwnd DataSource 連接工廠產生了..."+ datasource.getURL());
		return datasource;
		
	}
	
	//配置jdbcTemplate Spring Bean
	//採用IoC注入控制反轉
	@Bean
	@RequestScope //請求作用域 生命週期
	public JdbcTemplate createJdbcTemplate(@Qualifier("northwnd") DataSource datasource) {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(datasource); //透過屬性注入依賴關係
		return template;
	}
	

}
