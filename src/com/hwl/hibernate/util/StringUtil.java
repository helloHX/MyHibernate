package com.hwl.hibernate.util;

/**
  * class StringUtil
  * @author huangWenLong
  * @date 2017��12��13��
  */
public class StringUtil {
	public static String paseToName(String s) {
		char beginc = s.charAt(0);
		char minC = (char) (beginc - ' ');
		String name = minC + s.substring(1);
		return name;
	}
	
	public static String paseToMethod(String s) {
		char beginc = s.charAt(0);
		char minC = (char) (beginc + ' ');
		String name = minC + s.substring(1);
		return name;
	}
	
}
