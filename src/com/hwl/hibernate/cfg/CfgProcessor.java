package com.hwl.hibernate.cfg;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateMapping;

/**
  * class CfgProcessor
  * @todo 进行xml的解析
  * @author huangWenLong
  * @date 2017年12月2日
  */
public class CfgProcessor {
	 public final static String SRC = "src/";
	/**
	 * 				
	 * @param resource
	 * @return
	 * @throws JAXBException
	 * @author: huangWenLong
	 * @Description:根据hibernate.cfg.xml中的内容返回相应的configuration对象
	 * @date: 2017年12月8日 下午3:06:46
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
