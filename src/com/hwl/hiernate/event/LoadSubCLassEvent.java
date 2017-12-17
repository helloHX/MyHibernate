package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entity.EntityPersister;
import com.hwl.hibernate.entity.SubClassPersister;

/**
  * class LoadSubCLassEvent
  * @author huangWenLong
  * @date 2017��12��15��
  */
public class LoadSubCLassEvent extends AbstractEvent{
	
	private Serializable foreignkeyId;//�����и��ӱ�����
	private Serializable primaryKeyId;//�ӱ��е�����
	private Serializable mainTableKey;//�ӱ��������id
	private String entityName;//�ӱ������
	private Object result;//���ؽ��
	private SubClassPersister subClassPersister;
	private EntityPersister entityPersister;//�ӱ�ӳ��
	
	
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
