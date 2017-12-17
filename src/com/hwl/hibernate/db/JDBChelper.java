package com.hwl.hibernate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.hwl.hibernate.cfg.MyRegistry;

/**
 * class JDBChelper
 * 
 * @author huangWenLong
 * @date 2017��12��8��
 */
public class JDBChelper {
	private MyRegistry myRegistry;
	private Connection connection;
	private static final String JDBDRIVER = "connection.driver_class";
	private static final String USERNAME = "connection.username";
	private static final String PWD = "connection.password";
	private static final String URL = "connection.url";

	public JDBChelper(MyRegistry myRegistry) {
		this.myRegistry = myRegistry;
	}

	/**
	 * 
	 * @return: boolean
	 * @author: huangWenLong
	 * @Description:��ȡ���ݿ��ƶ��Ƿ��Զ��ύ
	 * @date: 2017��12��8�� ����8:38:57
	 */
	public boolean isAutoCommit() {
		try {
			return this.connection.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void setNotAutoCommit() throws SQLException {
		this.connection.setAutoCommit(false);
	}
	
	public void resetAutoCommit() throws SQLException {
		this.connection.setAutoCommit(true);
	}
	
	/**
	 * 
	 * @return: void
	 * @author: huangWenLong
	 * @Description:���������ļ��������ݿ����ӣ������޸�Ϊ�����ӳ��л�ȡ��
	 * @date: 2017��12��8�� ����8:39:42
	 */
	public Connection getConnection() {
		try {
			Class.forName(myRegistry.getSettings().get(JDBDRIVER));
			String userName = myRegistry.getSettings().get(USERNAME);
			String url = myRegistry.getSettings().get(URL);
			String password = myRegistry.getSettings().get(PWD);
			this.connection = DriverManager.getConnection(url, userName, password);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 
	 * @return: Statement
	 * @author: huangWenLong
	 * @Description:��ȡ���ݿ��ִ�����
	 * @date: 2017��12��8�� ����8:40:38
	 */
	public Statement getStatement() {
		Statement statement = null;
		try {
			statement = this.connection.createStatement();
   		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	
}
