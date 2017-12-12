package com.hwl.hibernate.test;

import javax.xml.bind.annotation.XmlRootElement;

/**
  * class TestEntity
  * @author huangWenLong
  * @date 2017Äê12ÔÂ2ÈÕ
  */
@XmlRootElement(name="testEntity")
public class TestEntity {
	private String entityName;
	private Integer entitySize;
	
	public TestEntity() {
		super();
	}

	public TestEntity(String entityName, Integer entitySize) {
		super();
		this.entityName = entityName;
		this.entitySize = entitySize;
	}
	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Integer getEntitySize() {
		return entitySize;
	}
	public void setEntitySize(Integer entitySize) {
		this.entitySize = entitySize;
	}
	
	@Override
	public String toString() {
		return "TestEntity[entityName" + entityName 
				+ "entitySize" + entitySize;
	}
	
}
