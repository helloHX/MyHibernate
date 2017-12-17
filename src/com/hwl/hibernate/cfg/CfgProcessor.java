package com.hwl.hibernate.cfg;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateMapping;

/**
  * class CfgProcessor
  * @todo ����xml�Ľ���
  * @author huangWenLong
  * @date 2017��12��2��
  */
public class CfgProcessor {
	 public final static String SRC = "src/";
	/**
	 * 				
	 * @param resource
	 * @return
	 * @throws JAXBException
	 * @author: huangWenLong
	 * @Description:����hibernate.cfg.xml�е����ݷ�����Ӧ��configuration����
	 * @date: 2017��12��8�� ����3:06:46
	 */
	public static JaxbCfgHibernateConfiguration unmarshal(String resource) throws JAXBException {
		File file = new File(SRC + resource);
		JAXBContext jaxbContext = JAXBContext.newInstance( JaxbCfgHibernateConfiguration.class );
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (JaxbCfgHibernateConfiguration) jaxbUnmarshaller.unmarshal( file );
	}
	
	public static JaxbCfgHibernateMapping unmarshalMapping(String resource) throws JAXBException {
		File file = new File(SRC + resource);
		JAXBContext jaxbContext = JAXBContext.newInstance( JaxbCfgHibernateMapping.class );
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return (JaxbCfgHibernateMapping) jaxbUnmarshaller.unmarshal( file );
	}
	
}
