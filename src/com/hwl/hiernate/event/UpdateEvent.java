package com.hwl.hiernate.event;

import com.hwl.hibernate.Session;

/**
  * class UpdateEvent
  * @author huangWenLong
  * @date 2017��12��12��
  */
public class UpdateEvent extends AbstractEvent{

	/**
	 * @param eventSource
	 */
	public UpdateEvent(Session eventSource) {
		super(eventSource);
	}

}
