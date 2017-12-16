package com.hwl.hibernate.test;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.SessionFactory;
import com.hwl.hibernate.cfg.Configuration;
import com.hwl.hibernate.test.entity.Student;

/**
  * class TestAny
  * @author huangWenLong
  * @date 2017Äê12ÔÂ13ÈÕ
  */
class TestAny {

	@Test
	void test() {
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Student student = (Student) session.load(Student.class, 28);
		System.out.println(student.getCode());
		System.out.println(student.getName());
		System.out.println(student.getAge());
		System.out.println(student.getClassRoom().getCode());
	}
//	@Test
//	void test() {
//		Map<TestEntity,String> map = new HashMap<>();
//		TestEntity t1 = new TestEntity();
//		t1.setEntityName("11");
//		t1.setEntitySize(2);
//		map.put(t1, "a");
//		TestEntity t2 = new TestEntity();
//		t2.setEntityName("11");
//		t2.setEntitySize(2);
//		Set<TestEntity> key = map.keySet();
//		System.out.println(key.contains(t2));
//		map.put(t1, "b");
//		System.out.println(map.size());
//	}

}
