package com.hwl.hiernate.event;

import java.io.Serializable;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.entityDBMapping.EntityPersister;

/**
  * class DeleteEvent
  * @author huangWenLong
  * @date 2017年12月17日
  */
public class DeleteEvent extends AbstractEvent{
	private Session eventSource;
	private Object obj;//被删除的对象
	private String objName;
	private Serializable id;
	private EntityPersister entityPersister;
	
	
	
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

	public DeleteEvent(Session eventSource) {
		super(eventSource);
		this.eventSource = eventSource;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
		this.objName = obj.getClass().getName();
	}
	
	
}
