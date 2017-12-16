package com.hwl.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hwl.hibernate.cfg.MappingReference;
import com.hwl.hibernate.cfg.MyRegistry;
import com.hwl.hibernate.db.JDBChelper;
import com.hwl.hibernate.entity.EntityPersister;
import com.hwl.hibernate.entity.TableEntityPersister;

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
	private List<EntityPersister> metaModel = new ArrayList<>();

	// �ȴ����Դ���EntityPersister
	public SessionFactoryImp(MyRegistry myRegistry) {
		this.myRegistry = myRegistry;
		initMetaModel();
		jdbChelper = new JDBChelper(myRegistry);// ��ʼ��jdbc����
	}

	public void initMetaModel() {
		List<MappingReference> mappings = myRegistry.getMappings();
		for (Iterator iterator = mappings.iterator(); iterator.hasNext();) {
			MappingReference mappingReference = (MappingReference) iterator.next();
			metaModel.add(TableEntityPersister.consume(mappingReference.getReference()));
		}
	}

	@Override
	public List<EntityPersister> getMetaModels() {
		return metaModel;
	}

	public EntityPersister getMetaModel(String entityName) {
		for (Iterator iterator = metaModel.iterator(); iterator.hasNext();) {
			EntityPersister entityPersister = (EntityPersister) iterator.next();
			if (entityPersister.getClassName().equals(entityName)) {
				return entityPersister;
			}
		}
		return null;
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
		return sessionImp;
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
