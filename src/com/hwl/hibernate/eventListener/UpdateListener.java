package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class UpdateListener
  * @author huangWenLong
  * @date 2017��12��12��
  */
public class UpdateListener implements Listener {
	private Connection connection;

	public UpdateListener( Connection connection) {
		super();
		this.connection = connection;
	}

}
