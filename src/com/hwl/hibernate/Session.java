package com.hwl.hibernate;

import java.io.Serializable;

/**
 * class Session
 * 
 * @author huangWenLong
 * @date 2017Äê11ÔÂ30ÈÕ
 */
public interface Session {
	void delete(Object object);

	void clear();

	Serializable save(Object object);

	void saveOrUpdate(Object object);

	void refresh();

	void evict(Object object);

	void refresh(Object object);

	void persist(Object object);

	Object merge(Object object);

	void load(Object object, Serializable id);

	Object load(String entityName, Serializable id);

	Object	get(String entityName, Serializable id);
	
	void update(Object object);
	
	
}
