package com.hwl.hibernate.test;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

import junit.framework.Test;

/**
  * class TestEntity
  * @author huangWenLong
  * @date 2017Äê12ÔÂ2ÈÕ
  */
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

//	@Override
//	public int compareTo(TestEntity o) {
//		if(this.entityName.equals(o.entityName) &&
//				this.entitySize.equals(o.entitySize)) {
//			return 0;
//		}
//		return 1;
//	}
	
	

	@Override
	public boolean equals(Object obj) {
		if(this.entityName.equals(((TestEntity)obj).entityName) &&
				this.entitySize.equals(((TestEntity)obj).entitySize)) {
			return true;
		}
		return false;
	}



	
}
