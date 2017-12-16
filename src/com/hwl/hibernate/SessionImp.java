package com.hwl.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hwl.hibernate.entity.SubClassPersister;
import com.hwl.hibernate.eventListener.DeleteListener;
import com.hwl.hibernate.eventListener.Listener;
import com.hwl.hibernate.eventListener.LoadListener;
import com.hwl.hibernate.eventListener.SaveListener;
import com.hwl.hibernate.eventListener.UpdateListener;
import com.hwl.hiernate.event.LoadEvent;
import com.hwl.hiernate.event.LoadSubCLassEvent;

/**
 * class SessionImp
 * 
 * @author huangWenLong
 * @date 2017年12月8日
 */
public class SessionImp implements Session {
	private SessionFactory factory;
	private List<Listener> eventManager;
	private PersistenceContext persistenceContext;
	private Connection connection;

	public SessionImp(SessionFactory factory) {
		this.factory = factory;
		this.persistenceContext = new PersistenceContext(this);
		this.eventManager = new ArrayList<>();
		this.connection = factory.getJdbChelper().getConnection();
		initSessionListener(connection);
	}

	/**
	 * 
	 * @return: void
	 * @author: huangWenLong
	 * @Description:数据库操作监听器
	 * @date: 2017年12月12日 下午3:25:41
	 */
	public void initSessionListener(Connection connection) {
		this.addEventManager(new LoadListener(connection));
		this.addEventManager(new SaveListener(connection));
		this.addEventManager(new DeleteListener(connection));
		this.addEventManager(new UpdateListener(connection));
	}

	public PersistenceContext getPersistenceContext() {
		return persistenceContext;
	}

	public void addEventManager(Listener listener) {
		eventManager.add(listener);
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public List<Listener> getEventManager() {
		return eventManager;
	}

	@Override
	public void delete(Object object) {
		// TODO 创建一个删除事件传递给Listener后面由listener处理
	}

	@Override
	public void clear() {

	}

	@Override
	public Serializable save(Object object) {
		return null;
	}

	@Override
	public void saveOrUpdate(Object object) {

	}

	@Override
	public void refresh() {

	}

	@Override
	public void evict(Object object) {

	}

	@Override
	public void refresh(Object object) {

	}

	@Override
	public void persist(Object object) {

	}

	@Override
	public Object merge(Object object) {
		return null;
	}

	@Override
	public Object load(Class<?> clazz, Serializable id) {
		LoadEvent event = new LoadEvent(this);
		event.setEntityID(id);
		event.setEntityName(clazz.getName());
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof LoadListener) {
				LoadListener loadListener = (LoadListener) listener;
				loadListener.load(event);
			}
		}
		return event.getResult();
	}

	@Override
	public Object load(String entityName, Serializable id) {
		return null;
	}

	@Override
	public Object get(String entityName, Serializable id) {
		return null;
	}

	@Override
	public void update(Object object) {

	}

	
	@Override
	public Object loadSubClass(SubClassPersister subClassPersister, Serializable id) throws Exception {
		LoadSubCLassEvent event = new LoadSubCLassEvent(this);
		event.setEntityName(subClassPersister.getClassName());
		event.setForeignkeyId(id);
		event.setSubClassPersister(subClassPersister);
//		event.setForeignKeyName(subClassPersister.getOwner());
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof LoadListener) {
				LoadListener loadListener = (LoadListener) listener;
				loadListener.subClassload(event);
			}
		}
		return event.getResult();
	}

	@Override
	public Object querySubClass(SubClassPersister subClassPersister, Serializable id) {
		LoadSubCLassEvent event = new LoadSubCLassEvent(this);
		event.setEntityName(subClassPersister.getClassName());
		event.setMainTableKey(id);
		event.setSubClassPersister(subClassPersister);
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof LoadListener) {
				LoadListener loadListener = (LoadListener) listener;
				loadListener.subClassQuery(event);
			}
		}
		return event.getResult();
	}

}
