package com.hwl.hibernate.util;

/**
  * class Log
  * @author huangWenLong
  * @date 2017��12��17��
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
