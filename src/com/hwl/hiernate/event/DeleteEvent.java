package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class DeleteEvent
  * @author huangWenLong
  * @date 2017��12��12��
  */
public class DeleteEvent extends AbstractEvent{

	/**
	 * @param eventSource
	 */
	public DeleteEvent(Session eventSource) {
		super(eventSource);
	}


}
