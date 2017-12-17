package com.hwl.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hwl.hibernate.entity.SubClassPersister;

/**
 * class Session
 * 
 * @author huangWenLong
 * @date 2017年11月30日
 */
public interface Session {

	String INSERT = "insert";
	String UPDATE = "update";
	String DELETE = "delete";

	void delete(Object object);

	void clear();

	Serializable save(Object object);

	void saveOrUpdate(Object object);

	PersistenceContext getPersistenceContext();

	Transaction beginTranscation();

	Map<String, List<String>> getActionQueue();

	boolean addDeleteAction(String sql);

	boolean addUpdateAction(String sql);

	boolean addInsertAction(String sql);

	void refresh();

	SessionFactory getFactory();

	void evict(Object object);

	void refresh(Object object);

	void persist(Object object);

	Object merge(Object object);

	Object load(Class<?> clazz, Serializable id);

	/**
	 * 
	 * @return: Object
	 * @author: huangWenLong
	 * @throws Exception
	 * @Description:懒加载在触发的真实加载单个子类
	 * @date: 2017年12月13日 下午10:37:00
	 */
	Object loadSubClass(SubClassPersister subClassPersister, Serializable id) throws Exception;

	/**
	 * 
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:懒加载在触发的真实加载子类
	 * @date: 2017年12月13日 下午10:27:32
	 */
	Object querySubClass(SubClassPersister subClassPersister, Serializable id);

	Object load(String entityName, Serializable id);

	Object get(String entityName, Serializable id);

	void update(Object object);

}
