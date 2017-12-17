package com.hwl.hibernate.cfg;

import java.util.Map;

import javax.xml.bind.JAXBException;

/**
  * class ConfigLoader
  * @author huangWenLong
  * @date 2017年11月30日
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
	 * @Description:加载resource中的配置内容
	 * @date: 2017年12月8日 下午3:12:17
	 */
	public LoadedConfig loadConfigXmlResource(String resource) throws JAXBException{
		return LoadedConfig.consume(CfgProcessor.unmarshal(resource));
	}
	
	
}
