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
 * @date 2017��12��3��
 */
public class SessionFactoryImp implements SessionFactory {
	private String name;
	private MyRegistry myRegistry;
	private JDBChelper jdbChelper;
	private List<EntityPersister> metaModel;
	
	// �ȴ����Դ���EntityPersister
	public SessionFactoryImp(MyRegistry myRegistry) {
		this.myRegistry = myRegistry;
		jdbChelper = new JDBChelper(myRegistry);// ��ʼ��jdbc����
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
