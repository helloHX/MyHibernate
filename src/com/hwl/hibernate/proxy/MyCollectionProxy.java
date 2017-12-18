package com.hwl.hibernate.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entityDBMapping.SubClassPersister;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * class MyCollectionProxy
 * 
 * @author huangWenLong
 * @date 2017��12��15��
 */
public class MyCollectionProxy implements MethodInterceptor {
	private boolean isLoad;// ��ǰ�����Ƿ��Ѿ������ݿ����
	private Serializable id;// ��ǰӳ�����ĸ������id
	private String foreginKeyName;//�����е������
	private Object target;// collection����
	private SubClassPersister entityPersister;// ��ǰί�ж����ӳ��
	private Session session;// ����session

	/**
	 * 
	 * @param target collection����
	 * @param entityPersister ��ǰί�ж����ӳ��
	 * @param session ����session
	 * @param id ��ǰӳ�����������е�idֵ
	 * @return
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:TODO
	 * @date: 2017��12��16�� ����11:09:54
	 */
	public Object getInstance(Object target, SubClassPersister entityPersister, Session session, Serializable id) {
		this.id = id;
		this.session = session;
		this.target = target;
		this.entityPersister = entityPersister;
		return Enhancer.create(this.target.getClass(), this);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		if (isLoad == false) {

			String className = entityPersister.getClassName();
			Object collection =  session.querySubClass(entityPersister, id);//�����ݿ��м���
			Class<?> clazz = target.getClass();
			Method addMethod = clazz.getMethod("addAll", Collection.class);//���ص�������ӵ�Collection��
			addMethod.invoke(target, collection);
			isLoad = true;
		}
	
		return method.invoke(target, args);
	}

}
