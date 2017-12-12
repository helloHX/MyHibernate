package com.hwl.hibernate;

import java.util.Map;

import com.hwl.hibernate.entity.TableEntityPersister;

/**
  * class PersistenceContext
  * @todo 提供session缓存
  * @author huangWenLong
  * @date 2017年12月11日
  */
public class PersistenceContext {
	private Session session;
	private Map<String,TableEntityPersister> tableEntityPersisters;
	public PersistenceContext(Session session) {
		super();
		this.session = session;
	}
	
	
	private void addTableEntityPersisters(String key,TableEntityPersister tableEntityPersister) {
		this.tableEntityPersisters.put(key, tableEntityPersister);
	}
	
}
