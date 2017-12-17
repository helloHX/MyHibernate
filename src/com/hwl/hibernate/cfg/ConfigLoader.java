package com.hwl.hibernate.cfg;

import java.util.Map;

import javax.xml.bind.JAXBException;

/**
  * class ConfigLoader
  * @author huangWenLong
  * @date 2017��11��30��
  */
public class ConfigLoader {
//	private Map<?,?> settings;
//	
//
//	public Map<?, ?> getSettings() {
//		return settings;
//	}
//
//	public void setSettings(Map<?, ?> settings) {
//		this.settings = settings;
//	}
//
//	public ConfigLoader() {
//		super();
//	}
	
	/**
	 * 
	 * @return: LoadedConfig
	 * @author: huangWenLong
	 * @Description:����resource�е���������
	 * @date: 2017��12��8�� ����3:12:17
	 */
	public LoadedConfig loadConfigXmlResource(String resource) throws JAXBException{
		return LoadedConfig.consume(CfgProcessor.unmarshal(resource));
	}
	
	
}
