package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entity.EntityPersister;

/**
  * class SaveEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class SaveEvent extends AbstractEvent {
	private Object object;
	private Session eventSource;
	private Serializable id;
	private EntityPersister entityPersister;
	/**
	 * @param eventSource
	 */
	public SaveEvent(Session eventSource) {
		super(eventSource);
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
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
	public EntityPersister getEntityPersister() {
		return entityPersister;
	}
	public void setEntityPersister(EntityPersister entityPersister) {
		this.entityPersister = entityPersister;
	}

	
}
