package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class SaveListener
  * @author huangWenLong
  * @date 2017��12��12��
  */
public class SaveListener implements Listener {
	private Connection connection;

	public SaveListener( Connection connection) {
		super();
		this.connection = connection;
	}

}
