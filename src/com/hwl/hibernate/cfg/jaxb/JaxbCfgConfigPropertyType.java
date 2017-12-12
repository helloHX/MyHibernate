package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * class JaxbCfgConfigPropertyType
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ5ÈÕ
 */
@XmlRootElement(name = "property")
public class JaxbCfgConfigPropertyType {
	protected String name;
	protected String value;

	
	
	public JaxbCfgConfigPropertyType() {
		super();
	}

	public JaxbCfgConfigPropertyType(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = "name", required = true)
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}

}
