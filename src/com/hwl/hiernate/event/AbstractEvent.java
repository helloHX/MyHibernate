package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class AbstractEvent
  * @author huangWenLong
  * @date 2017��12��12��
  */
public abstract class AbstractEvent {
	private Session eventSource;

	public AbstractEvent(Session eventSource) {
		super();
		this.eventSource = eventSource;
	}

	public Session getEventSource() {
		return eventSource;
	}

	public void setEventSource(Session eventSource) {
		this.eventSource = eventSource;
	}
	
}
