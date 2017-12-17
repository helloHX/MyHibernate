package com.hwl.hibernate.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.hwl.hibernate.cfg.CfgProcessor;
import com.hwl.hibernate.cfg.jaxb.JabCfgManyToOne;
import com.hwl.hibernate.cfg.jaxb.JabCfgOneToOne;
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
	private String entityId;//主键属性名
	private String colunmId;//主键表列名
	private String idType;
	private boolean lazy;
	private Map<String,String> foreignId;//一对一/多的属性-外键
	private Map<String, PersisterProperty> propertys;// 实体属性名与数据库字段信息的键值对
	private Map<String,String> fieldMap;//数据库字段-属性
	private Map<String,String> subClassMap;//数据库外键-属性
	private Map<String, SubClassPersister> subClass;// 外联表的关联

	public TableEntityPersister() {
		foreignId = new HashMap<>();
		propertys = new HashMap<>();
		subClass = new HashMap<>();
		subClassMap = new HashMap<>();
		fieldMap = new HashMap<>();
	}
	
	public void addForeignId(String name,String idName) {
		this.foreignId.put(name, idName);
	}
	
	
	public Map<String, String> getForeignId() {
		return foreignId;
	}

	public void addSubClass(String name,SubClassPersister subClassPersister) {
		this.subClassMap.put(subClassPersister.getPrimaryKey(), name);
		this.subClass.put(name, subClassPersister);
	}
	
	public String getFiledName(String colunm) {
		return fieldMap.get(colunm);
	}
	
	public String getSubClassName(String colunm) {
		return fieldMap.get(colunm);
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
		this.fieldMap.put(property.getColunm(), name);
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

	
	@Override
	public String getTableName() {
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
		String resourcePath = resource;
		TableEntityPersister entityPersister = new TableEntityPersister();
		try {
			JaxbCfgHibernateMapping mapping = CfgProcessor.unmarshalMapping(resourcePath);
			
			entityPersister.setClassName(mapping.getJabCfgClass().getName());
			entityPersister.setTableName(mapping.getJabCfgClass().getTable());
			entityPersister.setLazy(mapping.getJabCfgClass().isLazy());
			entityPersister.setEntityId(mapping.getJabCfgClass().getJaxCfgClassId().getName());
			entityPersister.setColunmId(mapping.getJabCfgClass().getJaxCfgClassId().getColumn());
			entityPersister.setIdType(mapping.getJabCfgClass().getJaxCfgClassId().getType());
			
			entityPersister.addProperty(entityPersister.getEntityId(),
					new PersisterProperty(entityPersister.getColunmId(), entityPersister.getIdType(),
							entityPersister.getEntityId()));//id也作为一个属性放入属性列表中里面
			
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
						entityPersister.addForeignId(jabCfgSet.getName(), jabCfgSet.getJabCfgForeignKey().getColumn());//方便后面将id区
						subClassPersister.setType(SubClassPersister.rl_type.many_to_many);
					}
					if(jabCfgSet.getJabCfgOneToMany() != null) {
						subClassPersister.setClassName(jabCfgSet.getJabCfgOneToMany().getClazz());
						subClassPersister.setPrimaryKey(jabCfgSet.getJabCfgOneToMany().getColumn());
						subClassPersister.setLazy(jabCfgSet.getJabCfgOneToMany().isLazy());
						subClassPersister.setType(SubClassPersister.rl_type.one_to_many);
					}
					subClassPersister.setOwner(entityPersister);
					
					entityPersister.addSubClass(jabCfgSet.getName(), subClassPersister);
				}
			}
			if(null != mapping.getJabCfgClass().getManyToOneList()) {
				List<JabCfgManyToOne> manyToOneList = mapping.getJabCfgClass().getManyToOneList();
				for (Iterator iterator = manyToOneList.iterator(); iterator.hasNext();) {
					JabCfgManyToOne jabCfgManyToOne = (JabCfgManyToOne) iterator.next();
					SubClassPersister subClassPersister = new SubClassPersister(); 
					subClassPersister.setName(jabCfgManyToOne.getName());
					subClassPersister.setClassName(jabCfgManyToOne.getClazz());
					subClassPersister.setForeignKey(jabCfgManyToOne.getColumn());
					subClassPersister.setLazy(jabCfgManyToOne.isLazy());
					subClassPersister.setType(SubClassPersister.rl_type.many_to_one);
					
					subClassPersister.setOwner(entityPersister);
					
					entityPersister.addForeignId(jabCfgManyToOne.getName(), jabCfgManyToOne.getColumn());//方便后面将id区
					
					entityPersister.addSubClass(jabCfgManyToOne.getName(), subClassPersister);
				}
			}
			if(null != mapping.getJabCfgClass().getOneToOneList()) {//一对一
				List<JabCfgOneToOne> oneToOneList = mapping.getJabCfgClass().getOneToOneList();
				for (Iterator iterator = oneToOneList.iterator(); iterator.hasNext();) {
					JabCfgOneToOne jabCfgOneToOne = (JabCfgOneToOne) iterator.next();
					SubClassPersister subClassPersister = new SubClassPersister(); 
					subClassPersister.setName(jabCfgOneToOne.getName());
					subClassPersister.setClassName(jabCfgOneToOne.getClazz());
					subClassPersister.setForeignKey(jabCfgOneToOne.getColumn());
					subClassPersister.setLazy(jabCfgOneToOne.isLazy());
					subClassPersister.setType(SubClassPersister.rl_type.one_to_one);
					subClassPersister.setOwner(entityPersister);
					entityPersister.addForeignId(jabCfgOneToOne.getName(), jabCfgOneToOne.getColumn());//方便后面将id区
					entityPersister.addSubClass(jabCfgOneToOne.getName(), subClassPersister);
				}
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return entityPersister;
	}

}
