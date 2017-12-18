package com.hwl.hibernate.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entityDBMapping.SubClassPersister;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;
import com.hwl.hibernate.util.StringUtil;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * class MyProxy
 * 一对一懒加载的处理
 * @author huangWenLong
 * @date 2017年12月13日
 */
public class MySingleProxy implements MethodInterceptor {
	private boolean isLoad;// 当前对象是否已经从数据库加载
	private String foreginKeyName;
	private Serializable id;
	private Object target;
	private SubClassPersister entityPersister;// 当前委托对象的映射
	private Session session;// 所属session

	public Object getInstance(Object target, SubClassPersister entityPersister, Session session, Serializable id) {
		this.id = id;
		this.session = session;
		this.target = target;
		this.entityPersister = entityPersister;
		return Enhancer.create(this.target.getClass(), this);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		// 判断Session缓存中是不是存在
		if (isLoad == false) {
			String className = entityPersister.getClassName();
			this.target = session.loadSubClass(entityPersister, id);//从数据库中记载
			isLoad = true;
		}
		return method.invoke(target, args);
	}

}
