package com.hwl.hibernate.entity;

/**
  * class PersisterProperty
  * @author huangWenLong
  * @date 2017Äê12ÔÂ11ÈÕ
  */
public class PersisterProperty {
	private String colunm;
	private String type;
	private String name;
	
	
	public PersisterProperty() {
		super();
	}
	
	public PersisterProperty(String colunm, String type, String name) {
		super();
		this.colunm = colunm;
		this.type = type;
		this.name = name;
	}

	public String getColunm() {
		return colunm;
	}
	public void setColunm(String colunm) {
		this.colunm = colunm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PersisterProperty [colunm=" + colunm + ", type=" + type + ", name=" + name + "]";
	}

	
	
}
