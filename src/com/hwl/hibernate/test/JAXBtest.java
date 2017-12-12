package com.hwl.hibernate.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.jupiter.api.Test;

import com.hwl.hibernate.cfg.jaxb.JabCfgClass;
import com.hwl.hibernate.cfg.jaxb.JabCfgClass.JacCfgClassProperty;
import com.hwl.hibernate.cfg.jaxb.JabCfgClass.JaxCfgClassId;
import com.hwl.hibernate.cfg.jaxb.JabCfgSet;
import com.hwl.hibernate.cfg.jaxb.JabCfgSet.JabCfgForeignKey;
import com.hwl.hibernate.cfg.jaxb.JabCfgSet.JabCfgOneToMany;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgConfigPropertyType;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateMapping;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgMappingReferenceType;

/**
 * class JAXBtest
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ2ÈÕ
 */
public class JAXBtest {
	
	@Test
	public void jaxbTest() throws JAXBException {
		JaxbCfgHibernateConfiguration jaxbCfgHibernateConfiguration = new JaxbCfgHibernateConfiguration();
		List<JaxbCfgConfigPropertyType> propertys = new ArrayList<>();
		List<JaxbCfgMappingReferenceType> mapping  = new ArrayList<>();
		JaxbCfgConfigPropertyType jaxbCfgConfigPropertyType1 = new JaxbCfgConfigPropertyType("name","root");
		JaxbCfgConfigPropertyType jaxbCfgConfigPropertyType2 = new JaxbCfgConfigPropertyType("password","123456");
		JaxbCfgConfigPropertyType jaxbCfgConfigPropertyType3 = new JaxbCfgConfigPropertyType("url","localhost:mysql");
		propertys.add(jaxbCfgConfigPropertyType1);
		propertys.add(jaxbCfgConfigPropertyType2);
		propertys.add(jaxbCfgConfigPropertyType3);
		JaxbCfgMappingReferenceType jaxbCfgMappingReferenceType1 = new JaxbCfgMappingReferenceType();
		JaxbCfgMappingReferenceType jaxbCfgMappingReferenceType2 = new JaxbCfgMappingReferenceType();
		JaxbCfgMappingReferenceType jaxbCfgMappingReferenceType3 = new JaxbCfgMappingReferenceType();
		jaxbCfgMappingReferenceType1.setResource("com.hwl.entity.E1");
		jaxbCfgMappingReferenceType2.setResource("com.hwl.entity.E2");
		jaxbCfgMappingReferenceType3.setResource("com.hwl.entity.E3");
		mapping.add(jaxbCfgMappingReferenceType1);
		mapping.add(jaxbCfgMappingReferenceType2);
		mapping.add(jaxbCfgMappingReferenceType3);
		jaxbCfgHibernateConfiguration.getJaxbCfgSessionFactory().setMapping(mapping);
		jaxbCfgHibernateConfiguration.getJaxbCfgSessionFactory().setPropertys(propertys);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(JaxbCfgHibernateConfiguration.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		// output pretty prin	ted
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(jaxbCfgHibernateConfiguration, System.out);  
	}
	
	@Test
	public void jaxbMappingTest() throws JAXBException {
		JaxbCfgHibernateMapping jaxbCfgHibernateMapping = new JaxbCfgHibernateMapping();
		JabCfgClass jabCfgClass = new JabCfgClass();
		jabCfgClass.setJaxCfgClassId(new JaxCfgClassId("id","String","stuId"));
		List<JacCfgClassProperty> properties = new ArrayList<>();
		properties.add(new JacCfgClassProperty("name", "stuName", "String"));
		properties.add(new JacCfgClassProperty("sex", "stuSex", "String"));
		properties.add(new JacCfgClassProperty("age", "stuAge", "Integer"));
		jabCfgClass.setPropertys(properties);
		JabCfgSet set = new JabCfgSet();
		set.setName("orders");
		set.setTable("orderId");
		JabCfgForeignKey key = new JabCfgForeignKey();
		key.setColumn("orderId");
		JabCfgOneToMany one_to_many = new JabCfgOneToMany("com.hwl.entity.Order","orderId");
		set.setJabCfgOneToMany(one_to_many);
//		jabCfgClass.setJaxCfgSet(set);
		jaxbCfgHibernateMapping.setJabCfgClass(jabCfgClass);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(JaxbCfgHibernateMapping.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		// output pretty prin	ted
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    jaxbMarshaller.marshal(jaxbCfgHibernateMapping, System.out);  
	}
}
