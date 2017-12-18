package com.hwl.hibernate.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.entityDBMapping.PersisterProperty;
import com.hwl.hibernate.entityDBMapping.SubClassPersister;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;

/**
 * class UnmarshalTest
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ12ÈÕ
 */
class UnmarshalTest {

	@Test
	void test() throws JAXBException {
		String resource = "com/hwl/hibernate/test/ClassRoom.hbm.xml";
		TableEntityPersister tableEntityPersister = (TableEntityPersister) TableEntityPersister.consume(resource);
		System.out.println(tableEntityPersister.getClassName());
		System.out.println(tableEntityPersister.getColunmId());
		System.out.println(tableEntityPersister.getEntityId());
		System.out.println(tableEntityPersister.getIdType());
		System.out.println(tableEntityPersister.getTableName());
		Map<String, PersisterProperty> property = tableEntityPersister.getPropertys();
		Set<String> set = property.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(property.get(string).toString());
		}
		Map<String, SubClassPersister> map = tableEntityPersister.getSubClass();
		Set<String> subclazzs = map.keySet();
		for (Iterator iterator = subclazzs.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(map.get(string).toString());
		}
	}

}
