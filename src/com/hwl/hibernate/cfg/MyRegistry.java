package com.hwl.hibernate.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

/**
 * class MyRegistry
 * 
 * @author huangWenLong
 * @date 2017��12��8��
 */
public class MyRegistry {
	private Map<String, String> settings;
	private List<MappingReference> mappings;//ʵ�����ݿ�ӳ���ļ�
	private ConfigLoader configLoader;

	public MyRegistry() {
		super();
		this.settings = new HashMap<>();
		this.mappings = new ArrayList<>();
		configLoader = new ConfigLoader();
	}

	public void configure(String resource) {
		try {
			LoadedConfig loadedConfig = configLoader.loadConfigXmlResource(resource);
			settings.putAll(loadedConfig.getPropertys());
			mappings.addAll(loadedConfig.getMapping());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<MappingReference> getMappings() {
		return mappings;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public ConfigLoader getConfigLoader() {
		return configLoader;
	}

	public void setConfigLoader(ConfigLoader configLoader) {
		this.configLoader = configLoader;
	}

}
