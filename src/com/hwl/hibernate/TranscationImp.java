package com.hwl.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.hwl.hibernate.util.Log;

/**
 * class TranscationImp
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ17ÈÕ
 */
public class TranscationImp implements Transaction {
	private Connection connection;
	private Session session;
	private boolean active;
	private final Log Log = new Log(TranscationImp.class.getName());
	
	public TranscationImp(Connection connection, Session session) {
		super();
		this.session = session;
		this.connection = connection;
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.active = true;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void excuteActionQueue() throws SQLException {
		Map<String, List<String>> actionQueue = session.getActionQueue();
		List<String> insertSql = actionQueue.get(Session.INSERT);
		List<String> updateSql = actionQueue.get(Session.UPDATE);
		List<String> deleteSql = actionQueue.get(Session.DELETE);
		for (Iterator iterator = insertSql.iterator(); iterator.hasNext();) {
			String sql = (String) iterator.next();
			Statement sta = connection.createStatement();
			Log.info(sql.toString());
			sta.execute(sql.toString());
	
		}
		for (Iterator iterator = updateSql.iterator(); iterator.hasNext();) {
			String sql = (String) iterator.next();
			Statement sta = connection.createStatement();
			Log.info(sql.toString());
			sta.execute(sql.toString());
	
		}
		for (Iterator iterator = deleteSql.iterator(); iterator.hasNext();) {
			String sql = (String) iterator.next();
			Statement sta = connection.createStatement();
			Log.info(sql.toString());
			sta.execute(sql.toString());
		
		}
	}

	@Override
	public void commit() {
		try {
			excuteActionQueue();
			this.connection.commit();
			this.connection.setAutoCommit(true);
			this.active = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		try {
			this.connection.rollback();
			this.connection.setAutoCommit(true);
			this.active = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
