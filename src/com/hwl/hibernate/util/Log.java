package com.hwl.hibernate.util;

/**
  * class Log
  * @author huangWenLong
  * @date 2017Äê12ÔÂ17ÈÕ
  */
public class Log {
	public final String location;
	
	public Log(String location) {
		super();
		this.location = location;
	}

	public  void info(String info) {
		System.out.println(info);
	}
}
