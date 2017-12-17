package com.hwl.hibernate.test.entity;

import java.util.Set;

/**
  * class Address
  * @author huangWenLong
  * @date 2017Äê12ÔÂ16ÈÕ
  */
public class Address {
	private Integer streetId;
	private String streetName;
	private Set<Student> stus;
	
	public Set<Student> getStus() {
		return stus;
	}
	public void setStus(Set<Student> stus) {
		this.stus = stus;
	}
	
	public Integer getStreetId() {
		return streetId;
	}
	public void setStreetId(Integer streetId) {
		this.streetId = streetId;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
}
