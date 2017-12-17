package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entity.EntityPersister;

/**
  * class LoadEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class LoadEvent extends AbstractEvent{

	private Serializable entityID;
	private String entityName;
	private Object result;
	private EntityPersister entityPersister;
	/**
	 * @param eventSource
	 */
	public LoadEvent(Session eventSource) {
		super(eventSource);
	}
	
	public Serializable getEntityID() {
		return entityID;
	}
	public void setEntityID(Serializable entityID) {
		this.entityID = entityID;
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
