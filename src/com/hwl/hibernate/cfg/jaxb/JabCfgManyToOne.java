package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
  * class JabCfgManyToOne
  * @author huangWenLong
  * @date 2017Äê12ÔÂ6ÈÕ
  */
@XmlRootElement(name="many-to-one")
public class JabCfgManyToOne {
	private String name;
	private String clazz;
	private String column;
	private boolean lazy;
	
	public boolean isLazy() {
		return lazy;
	}
	
	@XmlAttribute(name="lazy")
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	
	public String getName() {
		return name;
	}
	@XmlAttribute(name="name",required=true)
	public void setName(String name) {
		this.name = name;
	}
	public String getClazz() {
		return clazz;
	}
	@XmlAttribute(name="class",required=true)
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getColumn() {
		return column;
	}
	@XmlAttribute(name="column",required=true)
	public void setColumn(String column) {
		this.column = column;
	}
}
