package com.gjun.models;

import java.sql.SQLException;
import java.util.List;

//介面規範CRUD與注入DataSource Property
//使用Gneric 泛型
public interface IDao<T,K> {
	//新增C
	boolean insert(T entity) throws SQLException;
	//刪除D
	boolean delete(K key) throws SQLException;
	//修改U
	boolean update(T entity) throws SQLException;
	//單筆查詢R
	T selectForObject(K key) throws SQLException;
	//多筆查詢R
	List<T> selectForList(K key) throws SQLException;
	//強制注入依賴物件 Property Injection
	//void setDataSource(DataSource datasource);

}
