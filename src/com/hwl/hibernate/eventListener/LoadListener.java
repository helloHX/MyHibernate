package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class LoadListener
  * @author huangWenLong
  * @date 2017��12��8��
  */
public class LoadListener implements Listener {
	private Connection connection;

	public LoadListener( Connection connection) {
		super();
		this.connection = connection;
	}
	
}
