package com.hwl.hibernate.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.hwl.hibernate.cfg.CfgProcessor;
import com.hwl.hibernate.cfg.jaxb.JabCfgManyToOne;
import com.hwl.hibernate.cfg.jaxb.JabCfgSet;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateMapping;
import com.hwl.hibernate.cfg.jaxb.JabCfgClass.JacCfgClassProperty;

/**
 * class SingleTableEntityPersister
 * 
 * @author huangWenLong
 * @date 2017年12月11日
 */
public class TableEntityPersister implements EntityPersister {
	private String className;
	private String tableName;
	private String entityId;
	private String colunmId;
	private String idType;
	private boolean lazy;
	private Map<String, PersisterProperty> propertys;// 数据库字段与实体字段的对应
	private Map<String, SubClassPersister> subClass;// 外联表的关联

	public TableEntityPersister() {
		propertys = new HashMap<>();
		subClass = new HashMap<>();
		
	}
	
	public void addSubClass(String name,SubClassPersister subClassPersister) {
		this.subClass.put(name, subClassPersister);
	}
	
	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getColunmId() {
		return colunmId;
	}

	public void setColunmId(String colunmId) {
		this.colunmId = colunmId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Override
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, PersisterProperty> getPropertys() {
		return propertys;
	}

	public void setPropertys(Map<String, PersisterProperty> propertys) {
		this.propertys = propertys;
	}

	public void addProperty(String name, PersisterProperty property) {
		this.propertys.put(name, property);
	}

	public Map<String, SubClassPersister> getSubClass() {
		return subClass;
	}

	public void setSubClass(Map<String, SubClassPersister> subClass) {
		this.subClass = subClass;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwl.hibernate.EntityPersister#getTableName()
	 */
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return this.tableName;
	}

	/**
	 * 
	 * @return: EntityPersister
	 * @author: huangWenLong
	 * @Description:根据MappingReference解析
	 * @date: 2017年12月12日 下午3:48:39
	 */
	public static EntityPersister consume(String resource) {
		TableEntityPersister entityPersister = new TableEntityPersister();
		try {
			JaxbCfgHibernateMapping mapping = CfgProcessor.unmarshalMapping(resource);
			
			entityPersister.setClassName(mapping.getJabCfgClass().getName());
			entityPersister.setTableName(mapping.getJabCfgClass().getTable());
			entityPersister.setLazy(mapping.getJabCfgClass().isLazy());
			entityPersister.setEntityId(mapping.getJabCfgClass().getJaxCfgClassId().getName());
			entityPersister.setColunmId(mapping.getJabCfgClass().getJaxCfgClassId().getColumn());
			entityPersister.setIdType(mapping.getJabCfgClass().getJaxCfgClassId().getType());
			
			if (null != mapping.getJabCfgClass().getPropertys()) {
				List<JacCfgClassProperty> propertys = mapping.getJabCfgClass().getPropertys();
				for (Iterator iterator = propertys.iterator(); iterator.hasNext();) {
					JacCfgClassProperty jacCfgClassProperty = (JacCfgClassProperty) iterator.next();
					entityPersister.addProperty(jacCfgClassProperty.getName(),
							new PersisterProperty(jacCfgClassProperty.getColumn(), jacCfgClassProperty.getType(),
									jacCfgClassProperty.getName()));
				}
			}
			if (null != mapping.getJabCfgClass().getJaxCfgSet()) {
				List<JabCfgSet> jaxCfgSet = mapping.getJabCfgClass().getJaxCfgSet();
				for (Iterator iterator = jaxCfgSet.iterator(); iterator.hasNext();) {
					JabCfgSet jabCfgSet = (JabCfgSet) iterator.next();
					SubClassPersister subClassPersister = new SubClassPersister();
					subClassPersister.setForeignKey(jabCfgSet.getJabCfgForeignKey().getColumn());
					subClassPersister.setTableName(jabCfgSet.getTable());
					subClassPersister.setName(jabCfgSet.getName());
					
					if(jabCfgSet.getJabCfgManyToMany() != null) {
						subClassPersister.setClassName(jabCfgSet.getJabCfgManyToMany().getClazz());
						subClassPersister.setPrimaryKey(jabCfgSet.getJabCfgManyToMany().getColumn());
						subClassPersister.setLazy(jabCfgSet.getJabCfgManyToMany().isLazy());
						subClassPersister.setType(SubClassPersister.rl_type.many_to_many);
					}
					if(jabCfgSet.getJabCfgOneToMany() != null) {
						subClassPersister.setClassName(jabCfgSet.getJabCfgOneToMany().getClazz());
						subClassPersister.setPrimaryKey(jabCfgSet.getJabCfgOneToMany().getColumn());
						subClassPersister.setLazy(jabCfgSet.getJabCfgOneToMany().isLazy());
						subClassPersister.setType(SubClassPersister.rl_type.one_to_many);
					}
					entityPersister.addSubClass(jabCfgSet.getName(), subClassPersister);
				}
			}
			if(null != mapping.getJabCfgClass().getManyToOneList()) {
				List<JabCfgManyToOne> manyToOneList = mapping.getJabCfgClass().getManyToOneList();
				for (Iterator iterator = manyToOneList.iterator(); iterator.hasNext();) {
					JabCfgManyToOne jabCfgManyToOne = (JabCfgManyToOne) iterator.next();
					SubClassPersister subClassPersister = new SubClassPersister(); 
					subClassPersister.setName(jabCfgManyToOne.getName());
					subClassPersister.setPrimaryKey(jabCfgManyToOne.getColumn());
					subClassPersister.setLazy(jabCfgManyToOne.isLazy());
					subClassPersister.setType(SubClassPersister.rl_type.many_to_one);
					entityPersister.addSubClass(jabCfgManyToOne.getName(), subClassPersister);
				}
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return entityPersister;
	}

}
