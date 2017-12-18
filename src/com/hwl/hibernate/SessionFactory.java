package com.hwl.hibernate;

import java.util.List;

import com.hwl.hibernate.db.JDBChelper;
import com.hwl.hibernate.entityDBMapping.EntityPersister;

/**
  * class SessionFactory
  * @author huangWenLong
  * @date 2017��11��30��
  */
public interface SessionFactory {
	String	getName();
	Session openSession();
	List<EntityPersister> getMetaModels();
	EntityPersister getMetaModel(String entityName);
	Session getCurrentSession();
	void close();
	boolean isClosed();
	JDBChelper getJdbChelper();
}
