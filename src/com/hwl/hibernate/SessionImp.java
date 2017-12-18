package com.hwl.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hwl.hibernate.entityDBMapping.SubClassPersister;
import com.hwl.hibernate.eventListener.DeleteListener;
import com.hwl.hibernate.eventListener.Listener;
import com.hwl.hibernate.eventListener.LoadListener;
import com.hwl.hibernate.eventListener.SaveListener;
import com.hwl.hibernate.eventListener.UpdateListener;
import com.hwl.hiernate.event.DeleteEvent;
import com.hwl.hiernate.event.LoadEvent;
import com.hwl.hiernate.event.LoadSubCLassEvent;
import com.hwl.hiernate.event.SaveEvent;
import com.hwl.hiernate.event.UpdateEvent;

/**
 * class SessionImp
 * 
 * @author huangWenLong
 * @date 2017年12月8日
 */
public class SessionImp implements Session {
	private SessionFactory factory;
	private List<Listener> eventManager;
	private Map<String,List<String>> actionQueue;
	private PersistenceContext persistenceContext;
	private Transaction transaction;
	private Connection connection;

	public SessionImp(SessionFactory factory) {
		this.factory = factory;
		this.persistenceContext = new PersistenceContext(this);
		this.eventManager = new ArrayList<>();
		this.connection = factory.getJdbChelper().getConnection();
		initActionQueue();
		initSessionListener(connection);
	}
	
	public void initActionQueue() {
		this.actionQueue = new HashMap<>();
		this.actionQueue.put("insert", new ArrayList<String>());
		this.actionQueue.put("update", new ArrayList<String>());
		this.actionQueue.put("delete", new ArrayList<String>());
	}
	
	/**
	 * 
	 * @return: boolean
	 * @author: huangWenLong
	 * @Description:加入事务队列
	 * @date: 2017年12月17日 下午4:42:02
	 */
	@Override
	public boolean addDeleteAction(String sql) {
		if(transaction != null && transaction.isActive()) {
			this.actionQueue.get(DELETE).add(sql);
			return true;
		}
		return false;
	}

	@Override
	public boolean addUpdateAction(String sql) {
		if(transaction != null && transaction.isActive()) {
			this.actionQueue.get(UPDATE).add(sql);
			return true;
		}
		return false;
	}

	@Override
	public boolean addInsertAction(String sql) {
		if(transaction != null && transaction.isActive()) {
			this.actionQueue.get(INSERT).add(sql);
			return true;
		}
		return false;
	}

	public Transaction beginTranscation() {
		transaction = new TranscationImp(connection, this);
		return this.transaction;
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
		DeleteEvent event = new DeleteEvent(this);
		event.setObj(object);
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof DeleteListener) {
				DeleteListener deleteListener = (DeleteListener) listener;
				try {
					deleteListener.delete(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		object = null;
	}

	@Override
	public void clear() {
	}

	@Override
	public Serializable save(Object object) {
		SaveEvent event = new SaveEvent(this);
		event.setObject(object);
		event.setId(Integer.parseInt((System.currentTimeMillis() % 500) + ""));
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof SaveListener) {
				SaveListener saveListener = (SaveListener) listener;
				saveListener.save(event);
			}
		}
		return event.getId();
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
		UpdateEvent event = new UpdateEvent(this);
		event.setObject(object);
		for (Iterator iterator = eventManager.iterator(); iterator.hasNext();) {
			Listener listener = (Listener) iterator.next();
			if (listener instanceof UpdateListener) {
				UpdateListener uploadListener = (UpdateListener) listener;
				uploadListener.update(event);
			}
		}
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

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#getActionQueue()
	 */
	@Override
	public Map<String, List<String>> getActionQueue() {
		return this.actionQueue;
	}

}
