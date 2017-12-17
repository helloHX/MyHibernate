package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
  * class JaxbCfgMappingReferenceType
  * @author huangWenLong
  * @date 2017Äê12ÔÂ5ÈÕ
  */
@XmlRootElement(name = "mapping")
public class JaxbCfgMappingReferenceType {
	protected String	_package;
	protected String	clazz;
	protected String	file;
	protected String	jar;
	protected String	resource;
	
	public String get_package() {
		return _package;
	}
	@XmlAttribute( name = "package")
	public void set_package(String _package) {
		this._package = _package;
	}
	public String getClazz() {
		return clazz;
	}
	@XmlAttribute( name = "clazz")
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getFile() {
		return file;
	}
	@XmlAttribute( name = "file")
	public void setFile(String file) {
		this.file = file;
	}
	public String getJar() {
		return jar;
	}
	@XmlAttribute( name = "jar")
	public void setJar(String jar) {
		this.jar = jar;
	}
	public String getResource() {
		return resource;
	}
	@XmlAttribute( name = "resource")
	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
