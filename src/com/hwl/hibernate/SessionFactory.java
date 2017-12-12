package com.hwl.hibernate;

import com.hwl.hibernate.db.JDBChelper;

/**
  * class SessionFactory
  * @author huangWenLong
  * @date 2017Äê11ÔÂ30ÈÕ
  */
public interface SessionFactory {
	String	getName();
	Session openSession();
	Session getCurrentSession();
	void close();
	boolean isClosed();
	JDBChelper getJdbChelper();
}
