package com.hwl.hibernate.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.SessionFactory;
import com.hwl.hibernate.Transaction;
import com.hwl.hibernate.cfg.Configuration;
import com.hwl.hibernate.test.entity.Student;

/**
  * class TestUpdateTranscation
  * @author huangWenLong
  * @date 2017Äê12ÔÂ17ÈÕ
  */
class TestUpdateTranscation {

	@Test
	void test() {
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTranscation();
		Student student = (Student) session.load(Student.class, 28);
		System.out.println(student.getCode());
		System.out.println(student.getName());
		System.out.println(student.getAge());
		student.setAge(89);
		session.update(student);
		transaction.commit();
		
	}
}
