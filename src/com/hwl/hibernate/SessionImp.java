package com.hwl.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hwl.hibernate.eventListener.DeletListener;
import com.hwl.hibernate.eventListener.Listener;
import com.hwl.hibernate.eventListener.LoadListener;
import com.hwl.hibernate.eventListener.SaveListener;
import com.hwl.hibernate.eventListener.UpdateListener;

/**
  * class SessionImp
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
		this.addEventManager(new DeletListener(connection));
		this.addEventManager(new UpdateListener(connection));
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

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object object) {
		// TODO 创建一个删除事件传递给Listener后面由listener处理
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#save(java.lang.Object)
	 */
	@Override
	public Serializable save(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Object object) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#refresh()
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#evict(java.lang.Object)
	 */
	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object object) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object object) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#merge(java.lang.Object)
	 */
	@Override
	public Object merge(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#load(java.lang.Object, java.io.Serializable)
	 */
	@Override
	public void load(Object object, Serializable id) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#load(java.lang.String, java.io.Serializable)
	 */
	@Override
	public Object load(String entityName, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#get(java.lang.String, java.io.Serializable)
	 */
	@Override
	public Object get(String entityName, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hwl.hibernate.Session#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}



}
