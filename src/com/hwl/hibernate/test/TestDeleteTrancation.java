package com.hwl.hibernate.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.SessionFactory;
import com.hwl.hibernate.Transaction;
import com.hwl.hibernate.cfg.Configuration;
import com.hwl.hibernate.test.entity.Student;

/**
  * class TestDeleteTrancation
  * @author huangWenLong
  * @date 2017Äê12ÔÂ17ÈÕ
  */
class TestDeleteTrancation {

	@Test
	void test() {
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTranscation();
		Student student = (Student) session.load(Student.class, 28);
		session.delete(student);
		transaction.commit();
	}

}
