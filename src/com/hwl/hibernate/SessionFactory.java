package com.hwl.hibernate;

import com.hwl.hibernate.db.JDBChelper;

/**
  * class SessionFactory
  * @author huangWenLong
  * @date 2017��11��30��
  */
public interface SessionFactory {
	String	getName();
	Session openSession();
	Session getCurrentSession();
	void close();
	boolean isClosed();
	JDBChelper getJdbChelper();
}
