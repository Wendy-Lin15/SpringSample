package com.gjun.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.gjun.bean.Member;
import com.gjun.models.IDao;
//規劃成Repository
@Repository
public class MemberRepository implements IDao<Member,String> {
	//Data Field 注入依賴物件 DataSource連接工廠
	@Autowired
	@Qualifier("gjun")	//當型別有相同時，用@Qualifier避開
	private DataSource datasource;
	private String sourceId;	//擴充屬性(Source key-修改前的UserName)
	
	//唯寫Property
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	//建構子
	public MemberRepository() {
		System.out.println("Member Repository 建立了");
	}

	@Override
	public boolean insert(Member entity) throws SQLException {
		// 透過注入進來的DataSource 生產一條連接物件
		boolean r = false;
		Connection connection =null;
		try {
			connection = datasource.getConnection(); //開啟連接Open 一條連接Connection
			//建立一個具有參數架構的Statement(JDBC)
			String sql = "Insert Into Member(UserName,Password,RealName,Email) values(?,?,?,?)";
			PreparedStatement st = connection.prepareStatement(sql);
			//設定參數
			st.setString(1, entity.getUserName());
			st.setString(2, entity.getPassword());
			st.setString(3, entity.getRealName());
			st.setString(4, entity.getEmail());
			//執行新增作業
			int counter = st.executeUpdate();
			//新增成功
			r=true;
			
		}catch(SQLException ex) {
			throw ex;
		}finally {
			if(connection!=null) {
				//關閉連接 將連接收回到集區Pooling
				connection.close();
			}
		}
		return r;
	}

	@Override
	public boolean delete(String key) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Member entity) throws SQLException {
		//設定Update SQL
		String sql="Update Member set UserName=?, Password=?, RealName=?, Email=? Where UserName=?";
		//借助注入DataSource產生連結物件
		Connection connection=null;
		boolean r=false;
		try {
			connection=datasource.getConnection();
			//借助連接物件產生命令物件
			PreparedStatement st = connection.prepareStatement(sql);
			//設定參數
			st.setString(1, entity.getUserName());
			st.setString(2, entity.getPassword());
			st.setString(3, entity.getRealName());
			st.setString(4, entity.getEmail());
			st.setString(5, this.sourceId); //修改前的UserName
			//更新資料
			int row=st.executeUpdate(); //executeUpdate()可以用在新增 刪除 修改
			//更新成功
			if(row>0) {
				r=true;
				System.out.println("會員資料更新成功");
			}else {
				System.out.println("會員資料更新失敗");
			}
		}catch(SQLException ex) {
			throw ex;
		}finally {
			if(connection != null) {
				connection.close();	//收回連結到集區
			}
		}
		return r;
	}

	@Override
	public Member selectForObject(String key) throws SQLException {
		//傳遞UserName,Password(字串串聯) 類似Authorization basic token(base64) 採用username;password
		String[] items=key.split(";");
		//會員查詢作業
		String sql="Select UserName, Password, RealName, Email from Member where UserName=? and Password=?";
		//透過注入DataSource產生連結物件
		Connection connection = null;
		Member member=null;
		try {
			connection=datasource.getConnection();
			//透過連接物件產生prepare Statement物件
			PreparedStatement st= connection.prepareStatement(sql);
			//設定參數
			st.setString(1, items[0]);
			st.setString(2, items[1]);
			//執行查詢 (產生Fetching 逐筆讀取的物件)
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				//驗證通過(查到會員資料)
				member = new Member();
				member.setUserName(rs.getString("UserName"));
				member.setPassword(rs.getString("Password"));
				member.setRealName(rs.getString("RealName"));
				member.setEmail(rs.getString("Email"));
			}
			
		}catch(SQLException ex) {
			throw ex;
			
		}finally {
			if(connection != null)
				connection.close();
		}
		return member;
	}

	@Override
	public List<Member> selectForList(String key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
