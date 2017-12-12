package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class UpdateListener
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class UpdateListener implements Listener {
	private Connection connection;

	public UpdateListener( Connection connection) {
		super();
		this.connection = connection;
	}

}
