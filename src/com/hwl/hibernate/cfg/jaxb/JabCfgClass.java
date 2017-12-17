package com.hwl.hibernate.cfg.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.hwl.hibernate.cfg.jaxb.JaxbCfgHibernateConfiguration.JaxbCfgSessionFactory;

/**
 * class JabCfgClass
 * 
 * @author huangWenLong
 * @date 2017年12月6日
 */
@XmlType( propOrder = { "jaxCfgClassId", "propertys", "jaxCfgSet","manyToOneList","oneToOneList"})
@XmlRootElement(name = "class")
public class JabCfgClass {
	private String name;
	private String table;
//	private String mutable;
//	private String catalog;
	private boolean lazy;
	private JaxCfgClassId jaxCfgClassId;
	private List<JacCfgClassProperty> propertys;
	private List<JabCfgSet> jaxCfgSet;
	private List<JabCfgManyToOne> manyToOneList;
	private List<JabCfgOneToOne> oneToOneList;
	
	public List<JabCfgOneToOne> getOneToOneList() {
		return oneToOneList;
	}
	@XmlElement(name="one-to-one")
	public void setOneToOneList(List<JabCfgOneToOne> oneToOneList) {
		this.oneToOneList = oneToOneList;
	}

	public List<JabCfgManyToOne> getManyToOneList() {
		return manyToOneList;
	}

	@XmlElement(name="many-to-one")
	public void setManyToOneList(List<JabCfgManyToOne> manyToOneList) {
		this.manyToOneList = manyToOneList;
	}

	public List<JabCfgSet> getJaxCfgSet() {
		return jaxCfgSet;
	}

	@XmlElement(name="set")
	public void setJaxCfgSet(List<JabCfgSet> jaxCfgSet) {
		this.jaxCfgSet = jaxCfgSet;
	}

	public JaxCfgClassId getJaxCfgClassId() {
		return jaxCfgClassId;
	}

	@XmlElement(name="id",required = true)
	public void setJaxCfgClassId(JaxCfgClassId jaxCfgClassId) {
		this.jaxCfgClassId = jaxCfgClassId;
	}
	
	public List<JacCfgClassProperty> getPropertys() {
		return propertys;
	}
	
	@XmlElement(name="property")
	public void setPropertys(List<JacCfgClassProperty> propertys) {
		this.propertys = propertys;
	}


	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name", required = true)
	public void setName(String name) {
		this.name = name;
	}

	public String getTable() {
		return table;
	}
	
	@XmlAttribute(name = "table", required = true)
	public void setTable(String table) {
		this.table = table;
	}

//	public String getMutable() {
//		return mutable;
//	}
//
//	public void setMutable(String mutable) {
//		this.mutable = mutable;
//	}
//
//	public String getCatalog() {
//		return catalog;
//	}
//
//	public void setCatalog(String catalog) {
//		this.catalog = catalog;
//	}

	public boolean isLazy() {
		return lazy;
	}

	@XmlAttribute(name = "lazy")
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	
	@XmlRootElement(name = "id")
	public static class JaxCfgClassId{
		private String name;
		private String type;
		private String column;
		
		public JaxCfgClassId() {
			super();
		}

		public JaxCfgClassId(String name, String type, String column) {
			super();
			this.name = name;
			this.type = type;
			this.column = column;
		}
		
		//默认id由用户创建
		public String getName() {
			return name;
		}
		@XmlAttribute(name = "name", required = true)
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		@XmlAttribute(name = "type", required = true)
		public void setType(String type) {
			this.type = type;
		}
		public String getColumn() {
			return column;
		}
		@XmlAttribute(name = "column", required = true)
		public void setColumn(String column) {
			this.column = column;
		}
	}
	
	@XmlRootElement(name = "property")
	public static class JacCfgClassProperty{
		private String name;//属性
		private String column;
		private String type;
		private boolean lazy;
		private boolean notNull;
		
		
		public JacCfgClassProperty() {
			super();
		}

		public JacCfgClassProperty(String name, String column, String type) {
			super();
			this.name = name;
			this.column = column;
			this.type = type;
		}
		
		public boolean isLazy() {
			return lazy;
		}
		@XmlAttribute(name="lazy")
		public void setLazy(boolean lazy) {
			this.lazy = lazy;
		}
		
		public boolean isNotNull() {
			return notNull;
		}
		@XmlAttribute(name="not-null")
		public void setNotNull(boolean notNull) {
			this.notNull = notNull;
		}
		
		public String getName() {
			return name;
		}
		
		@XmlAttribute(name="name",required=true)
		public void setName(String name) {
			this.name = name;
		}
		
		public String getColumn() {
			return column;
		}
		
		@XmlAttribute(name="column",required=true)
		public void setColumn(String column) {
			this.column = column;
		}
		
		public String getType() {
			return type;
		}
		
		@XmlAttribute(name="type",required=true)
		public void setType(String type) {
			this.type = type;
		}
	}
}
