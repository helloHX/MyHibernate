package com.hwl.hibernate.test.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class ClassRoom {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String code;
	private transient Set<Student> students = new HashSet<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
}
