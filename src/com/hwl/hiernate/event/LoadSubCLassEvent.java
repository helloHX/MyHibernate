package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entity.EntityPersister;
import com.hwl.hibernate.entity.SubClassPersister;

/**
  * class LoadSubCLassEvent
  * @author huangWenLong
  * @date 2017年12月15日
  */
public class LoadSubCLassEvent extends AbstractEvent{
	
	private Serializable foreignkeyId;//主表中该子表的外键
	private Serializable primaryKeyId;//子表中的主键
	private Serializable mainTableKey;//子表中主表的id
	private String entityName;//子表的类名
	private Object result;//返回结果
	private SubClassPersister subClassPersister;
	private EntityPersister entityPersister;//子表映射
	
	
	public Serializable getMainTableKey() {
		return mainTableKey;
	}
	public void setMainTableKey(Serializable mainTableKey) {
		this.mainTableKey = mainTableKey;
	}
	/**
	 * @param eventSource
	 */
	public LoadSubCLassEvent(Session eventSource) {
		super(eventSource);
	}
	public SubClassPersister getSubClassPersister() {
		return subClassPersister;
	}
	public Serializable getPrimaryKeyId() {
		return primaryKeyId;
	}
	public void setPrimaryKeyId(Serializable primaryKeyId) {
		this.primaryKeyId = primaryKeyId;
	}
	public void setSubClassPersister(SubClassPersister subClassPersister) {
		this.subClassPersister = subClassPersister;
	}
	public Serializable getForeignkeyId() {
		return foreignkeyId;
	}
	public void setForeignkeyId(Serializable foreignkeyId) {
		this.foreignkeyId = foreignkeyId;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public EntityPersister getEntityPersister() {
		return entityPersister;
	}
	public void setEntityPersister(EntityPersister entityPersister) {
		this.entityPersister = entityPersister;
	}
	

}
