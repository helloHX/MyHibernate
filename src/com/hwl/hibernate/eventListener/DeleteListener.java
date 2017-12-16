package com.hwl.hibernate.eventListener;

import java.sql.Connection;

/**
  * class DeletListener
  * @author huangWenLong
  * @date 2017Äê12ÔÂ12ÈÕ
  */
public class DeleteListener implements Listener {
	private Connection connection;
	
	public DeleteListener( Connection connection) {
		super();
		this.connection = connection;
	}

}
