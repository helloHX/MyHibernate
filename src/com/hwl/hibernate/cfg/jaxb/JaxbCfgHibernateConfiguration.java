package com.hwl.hibernate.cfg.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * class JaxbCfgHibernateConfiguration
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ5ÈÕ
 */
@XmlRootElement( name = "hibernate-configuration" )
public class JaxbCfgHibernateConfiguration {
	private  JaxbCfgSessionFactory jaxbCfgSessionFactory = new JaxbCfgSessionFactory();
	
	public JaxbCfgSessionFactory getJaxbCfgSessionFactory() {
		return jaxbCfgSessionFactory;
	}

	@XmlElement (name = "session-factory")
	public void setJaxbCfgSessionFactory(JaxbCfgSessionFactory jaxbCfgSessionFactory) {
		this.jaxbCfgSessionFactory = jaxbCfgSessionFactory;
	}
	

	@XmlType(propOrder = { "propertys", "mapping" })
	@XmlRootElement( name = "session-factory" )
	public static class JaxbCfgSessionFactory {
		private List<JaxbCfgConfigPropertyType> propertys;
		private List<JaxbCfgMappingReferenceType> mapping;

		public List<JaxbCfgConfigPropertyType> getPropertys() {
			return propertys;
		}

		@XmlElement(name = "propertys")
		public void setPropertys(List<JaxbCfgConfigPropertyType> propertys) {
			this.propertys = propertys;
		}

		public List<JaxbCfgMappingReferenceType> getMapping() {
			return mapping;
		}

		@XmlElement(name = "mapping")
		public void setMapping(List<JaxbCfgMappingReferenceType> mapping) {
			this.mapping = mapping;
		}

	}
}
