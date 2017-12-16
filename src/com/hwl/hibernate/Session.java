package com.hwl.hibernate;

import java.io.Serializable;

import com.hwl.hibernate.entity.SubClassPersister;

/**
 * class Session
 * 
 * @author huangWenLong
 * @date 2017��11��30��
 */
public interface Session {
	void delete(Object object);

	void clear();

	Serializable save(Object object);

	void saveOrUpdate(Object object);

	PersistenceContext getPersistenceContext();

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
	Object loadSubClass(SubClassPersister subClassPersister,Serializable id) throws Exception;
	
	/**
	 * 
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:�������ڴ�������ʵ��������
	 * @date: 2017��12��13�� ����10:27:32
	 */
	Object querySubClass(SubClassPersister subClassPersister,Serializable id);
	
	Object load(String entityName, Serializable id);

	Object get(String entityName, Serializable id);

	void update(Object object);

}
