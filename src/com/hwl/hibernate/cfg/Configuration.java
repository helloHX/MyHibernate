package com.hwl.hibernate.cfg;

import java.util.Properties;

import javax.xml.bind.JAXBException;

import com.hwl.hibernate.SessionFactory;
import com.hwl.hibernate.SessionFactoryImp;

/**
 * class Configuration
 * 
 * @author huangWenLong
 * @date 2017��11��30��
 */
public class Configuration {

	public static final String DEFAULT_CFG_RESOURCE_NAME = "hibernate.cfg.xml";
	private MyRegistry myRegistry;
	private Properties properties;

	public Configuration() {
		myRegistry = new MyRegistry();
		reset();
	}

	public void reset() {
		properties = new Properties();
	}

	public Properties getProperties() {
		return properties;
	}

	public Configuration configure() {
		return this.configure(DEFAULT_CFG_RESOURCE_NAME);
	}

	/**
	 * 
	 * @param resource
	 * @return
	 * @author: huangWenLong
	 * @throws JAXBException
	 * @Description:����hibernate.cfg.xml�е���������
	 * @date: 2017��12��8�� ����2:22:05
	 */
	public Configuration configure(String resource) {
		myRegistry.configure(resource);
		this.properties.putAll(myRegistry.getSettings());
		return this;
	}

	/**
	 * 
	 * @return
	 * @author: huangWenLong
	 * @Description:����SessionFactoryʵ��
	 * @date: 2017��12��3�� ����3:17:29
	 */
	public SessionFactory buildSessionFactory() {
		return new SessionFactoryImp(myRegistry);
	}

}
