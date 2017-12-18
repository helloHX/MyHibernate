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
 * @date 2017年12月15日
 */
public class MyCollectionProxy implements MethodInterceptor {
	private boolean isLoad;// 当前集合是否已经从数据库加载
	private Serializable id;// 当前映射对象的父对象的id
	private String foreginKeyName;//主表中的外键名
	private Object target;// collection集合
	private SubClassPersister entityPersister;// 当前委托对象的映射
	private Session session;// 所属session

	/**
	 * 
	 * @param target collection集合
	 * @param entityPersister 当前委托对象的映射
	 * @param session 所属session
	 * @param id 当前映射对象的主表中的id值
	 * @return
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:TODO
	 * @date: 2017年12月16日 上午11:09:54
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
			Object collection =  session.querySubClass(entityPersister, id);//从数据库中记载
			Class<?> clazz = target.getClass();
			Method addMethod = clazz.getMethod("addAll", Collection.class);//记载的内容添加到Collection中
			addMethod.invoke(target, collection);
			isLoad = true;
		}
	
		return method.invoke(target, args);
	}

}
