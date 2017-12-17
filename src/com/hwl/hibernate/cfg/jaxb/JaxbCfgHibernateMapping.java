package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
  * class JaxbCfgHibernateMapping
  * @author huangWenLong
  * @date 2017��12��6��
  */
@XmlRootElement( name = "hibernate-mapping" )
public class JaxbCfgHibernateMapping {
	
	private JabCfgClass jabCfgClass;

	public JabCfgClass getJabCfgClass() {
		return jabCfgClass;
	}
	
	@XmlElement(name="class")
	public void setJabCfgClass(JabCfgClass jabCfgClass) {
		this.jabCfgClass = jabCfgClass;
	}
}
