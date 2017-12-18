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
 * һ��һ�����صĴ���
 * @author huangWenLong
 * @date 2017��12��13��
 */
public class MySingleProxy implements MethodInterceptor {
	private boolean isLoad;// ��ǰ�����Ƿ��Ѿ������ݿ����
	private String foreginKeyName;
	private Serializable id;
	private Object target;
	private SubClassPersister entityPersister;// ��ǰί�ж����ӳ��
	private Session session;// ����session

	public Object getInstance(Object target, SubClassPersister entityPersister, Session session, Serializable id) {
		this.id = id;
		this.session = session;
		this.target = target;
		this.entityPersister = entityPersister;
		return Enhancer.create(this.target.getClass(), this);
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		// �ж�Session�������ǲ��Ǵ���
		if (isLoad == false) {
			String className = entityPersister.getClassName();
			this.target = session.loadSubClass(entityPersister, id);//�����ݿ��м���
			isLoad = true;
		}
		return method.invoke(target, args);
	}

}
