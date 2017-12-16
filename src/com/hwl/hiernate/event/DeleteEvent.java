package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class DeleteEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class DeleteEvent extends AbstractEvent{

	/**
	 * @param eventSource
	 */
	public DeleteEvent(Session eventSource) {
		super(eventSource);
	}


}
