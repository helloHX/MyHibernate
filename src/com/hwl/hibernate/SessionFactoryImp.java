package com.hwl.hibernate;

import java.util.List;

import com.hwl.hibernate.cfg.MappingReference;
import com.hwl.hibernate.cfg.MyRegistry;
import com.hwl.hibernate.db.JDBChelper;
import com.hwl.hibernate.entity.EntityPersister;

/**
 * class SessionFactoryImp
 * 
 * @author huangWenLong
 * @date 2017年12月3日
 */
public class SessionFactoryImp implements SessionFactory {
	private String name;
	private MyRegistry myRegistry;
	private JDBChelper jdbChelper;
	private List<EntityPersister> metaModel;
	
	// 等待属性代替EntityPersister
	public SessionFactoryImp(MyRegistry myRegistry) {
		this.myRegistry = myRegistry;
		jdbChelper = new JDBChelper(myRegistry);// 初始化jdbc连接
	}
	
	public void initMetaModel() {
		List<MappingReference> mappings = myRegistry.getMappings();
	}

	public JDBChelper getJdbChelper() {
		return jdbChelper;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Session openSession() {
		SessionImp sessionImp = new SessionImp(this);
		return null;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public Session getCurrentSession() {
		return null;
	}

}
