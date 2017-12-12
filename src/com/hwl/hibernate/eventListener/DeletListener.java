package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class DeletListener
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class DeletListener implements Listener {
	private Connection connection;
	
	public DeletListener( Connection connection) {
		super();
		this.connection = connection;
	}

}
