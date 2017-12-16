package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class SaveEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class SaveEvent extends AbstractEvent {

	/**
	 * @param eventSource
	 */
	public SaveEvent(Session eventSource) {
		super(eventSource);
	}

}
