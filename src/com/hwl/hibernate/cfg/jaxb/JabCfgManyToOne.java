package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
  * class JabCfgManyToOne
  * @author huangWenLong
  * @date 2017��12��6��
  */
@XmlRootElement(name="many-to-one")
public class JabCfgManyToOne {
	private String name;
	private String clazz;
	private String column;
	private boolean lazy;
	private boolean inverse;
	private String cascade;

	public boolean isInverse() {
		return inverse;
	}
	@XmlAttribute(name = "inverse")
	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}
	
	public String getCascade() {
		return cascade;
	}
	@XmlAttribute(name = "cascade")
	public void setCascade(String cascade) {
		this.cascade = cascade;
	}
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
