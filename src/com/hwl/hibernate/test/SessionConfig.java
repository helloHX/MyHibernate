package com.hwl.hibernate.test;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class SessionConfig
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ2ÈÕ
 */
@XmlRootElement
public class SessionConfig {
//	@XmlElementRef(type = Object.class)
	private HashMap<String, TestEntity> propertys;

	private String sessionConfigName;

	private TestEntity testEntity;

	public TestEntity getTestEntity() {
		return testEntity;
	}

	public void setTestEntity(TestEntity testEntity) {
		this.testEntity = testEntity;
	}



	public HashMap<String, TestEntity> getPropertys() {
		return propertys;
	}

	public void setPropertys(HashMap<String, TestEntity> propertys) {
		this.propertys = propertys;
	}

	public String getSessionConfigName() {
		return sessionConfigName;
	}

	public void setSessionConfigName(String sessionConfigName) {
		this.sessionConfigName = sessionConfigName;
	}

}
