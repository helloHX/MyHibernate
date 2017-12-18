package com.hwl.hibernate.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.Session;
import com.hwl.hibernate.SessionFactory;
import com.hwl.hibernate.Transaction;
import com.hwl.hibernate.cfg.Configuration;
import com.hwl.hibernate.test.entity.Student;

/**
  * class TestSaveTransaction
  * @author huangWenLong
  * @date 2017年12月17日
  */
class TestSaveTransaction {

	@Test
	void test() {
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTranscation();
		Student student = new Student("123", "小新", 12);
		session.save(student);
		transaction.commit();
	}

}
