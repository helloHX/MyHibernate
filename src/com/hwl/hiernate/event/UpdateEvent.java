package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entityDBMapping.EntityPersister;

/**
  * class UpdateEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class UpdateEvent extends AbstractEvent{

	private Object object;
	private Session eventSource;
	private Serializable id;
	private String objName;
	private EntityPersister entityPersister;
	public UpdateEvent(Session eventSource) {
		super(eventSource);
		this.eventSource = eventSource;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
		this.objName = object.getClass().getName();
	}
	public Session getEventSource() {
		return eventSource;
	}
	public void setEventSource(Session eventSource) {
		this.eventSource = eventSource;
	}
	public Serializable getId() {
		return id;
	}
	public void setId(Serializable id) {
		this.id = id;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public EntityPersister getEntityPersister() {
		return entityPersister;
	}
	public void setEntityPersister(EntityPersister entityPersister) {
		this.entityPersister = entityPersister;
	}

	
}
