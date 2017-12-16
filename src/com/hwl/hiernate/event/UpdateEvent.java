package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class UpdateEvent
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class UpdateEvent extends AbstractEvent{

	/**
	 * @param eventSource
	 */
	public UpdateEvent(Session eventSource) {
		super(eventSource);
	}

}
