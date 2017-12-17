package com.hwl.hibernate.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hwl.hibernate.cfg.jaxb.JaxbCfgConfigPropertyType;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration.JaxbCfgSessionFactory;
import com.hwl.hibernate.cfg.jaxb.JaxbCfgMappingReferenceType;

/**
  * class LoadedConfig
  * todo 暂存从*.cfg.xml中读出来的信息
  * @author huangWenLong
  * @date 2017年12月6日
  */
public class LoadedConfig {
	
	private Map<String,String> propertys = new HashMap<>();
	private List<MappingReference> mapping = new ArrayList<>();

	public Map<String, String> getPropertys() {
		return propertys;
	}
	public void setPropertys(Map<String, String> propertys) {
		this.propertys = propertys;
	}
	
	public void addConfigurationValue(String name,String value) {
		this.propertys.put(name, value);
	}
	
	public List<MappingReference> getMapping() {
		return mapping;
	}
	public void setMapping(List<MappingReference> mapping) {
		this.mapping = mapping;
	}
	
	public void addMappingReference(MappingReference mappingReference) {
		this.mapping.add(mappingReference);
	}
	
	/**
	 * 
	 * @return: LoadedConfig
	 * @author: huangWenLong
	 * @Description:解析JaxbCfgHibernateConfiguration中的内容
	 * @date: 2017年12月8日 下午4:15:16
	 */
	public static LoadedConfig consume(JaxbCfgHibernateConfiguration jaxbCfg) {
		LoadedConfig config = new LoadedConfig();
		JaxbCfgSessionFactory jaxbCfgSessionFactory =  jaxbCfg.getJaxbCfgSessionFactory();
		for (Iterator iterator = jaxbCfgSessionFactory.getPropertys().iterator(); iterator.hasNext();) {
			JaxbCfgConfigPropertyType jaxbCfgConfigPropertyType = (JaxbCfgConfigPropertyType) iterator.next();
			config.addConfigurationValue(jaxbCfgConfigPropertyType.getName(), jaxbCfgConfigPropertyType.getValue());
		}
		for (JaxbCfgMappingReferenceType jaxbCfgMappingReferenceType : jaxbCfgSessionFactory.getMapping()) {
			config.addMappingReference(MappingReference.consume(jaxbCfgMappingReferenceType));
		}
		
		return config;
	}
	
}
