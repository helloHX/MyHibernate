package com.hwl.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hwl.hibernate.entity.SubClassPersister;

/**
 * class Session
 * 
 * @author huangWenLong
 * @date 2017��11��30��
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
	 * @Description:�������ڴ�������ʵ���ص�������
	 * @date: 2017��12��13�� ����10:37:00
	 */
	Object loadSubClass(SubClassPersister subClassPersister, Serializable id) throws Exception;

	/**
	 * 
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:�������ڴ�������ʵ��������
	 * @date: 2017��12��13�� ����10:27:32
	 */
	Object querySubClass(SubClassPersister subClassPersister, Serializable id);

	Object load(String entityName, Serializable id);

	Object get(String entityName, Serializable id);

	void update(Object object);

}
