package com.hwl.hibernate.cfg.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * class JabCfgSet
 * 
 * @author huangWenLong
 * @date 2017Äê12ÔÂ6ÈÕ
 */
@XmlRootElement(name = "set")
public class JabCfgSet {
	private String name;
	private String table;
	private JabCfgForeignKey jabCfgForeignKey;
	private JabCfgManyToMany JabCfgManyToMany;
	private JabCfgOneToMany JabCfgOneToMany;

	public JabCfgOneToMany getJabCfgOneToMany() {
		return JabCfgOneToMany;
	}

	@XmlElement(name = "one-to-many")
	public void setJabCfgOneToMany(JabCfgOneToMany jabCfgOneToMany) {
		JabCfgOneToMany = jabCfgOneToMany;
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

	public JabCfgForeignKey getJabCfgForeignKey() {
		return jabCfgForeignKey;
	}

	@XmlElement(name = "key", required = true)
	public void setJabCfgForeignKey(JabCfgForeignKey jabCfgForeignKey) {
		this.jabCfgForeignKey = jabCfgForeignKey;
	}

	public JabCfgManyToMany getJabCfgManyToMany() {
		return JabCfgManyToMany;
	}

	@XmlElement(name = "many-to-many", required = true)
	public void setJabCfgManyToMany(JabCfgManyToMany jabCfgManyToMany) {
		JabCfgManyToMany = jabCfgManyToMany;
	}

	@XmlRootElement(name = "key")
	public static class JabCfgForeignKey {
		private String column;

		public String getColumn() {
			return column;
		}

		@XmlAttribute(name = "column", required = true)
		public void setColumn(String column) {
			this.column = column;
		}

	}

	@XmlRootElement(name = "many-to-many")
	public static class JabCfgManyToMany {
		private String clazz;
		private String column;
		private boolean lazy;

		public boolean isLazy() {
			return lazy;
		}

		@XmlAttribute(name = "lazy")
		public void setLazy(boolean lazy) {
			this.lazy = lazy;
		}

		public String getClazz() {
			return clazz;
		}

		@XmlAttribute(name = "class", required = true)
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public String getColumn() {
			return column;
		}

		@XmlAttribute(name = "column", required = true)
		public void setColumn(String column) {
			this.column = column;
		}

	}

	@XmlRootElement(name = "one-to-many")
	public static class JabCfgOneToMany {
		private String clazz;
		private String column;
		private boolean lazy;

		
		public JabCfgOneToMany() {
			super();
		}

		public JabCfgOneToMany(String clazz, String column) {
			super();
			this.clazz = clazz;
			this.column = column;
		}

		public boolean isLazy() {
			return lazy;
		}

		@XmlAttribute(name = "lazy")
		public void setLazy(boolean lazy) {
			this.lazy = lazy;
		}

		public String getClazz() {
			return clazz;
		}

		@XmlAttribute(name = "class", required = true)
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}

		public String getColumn() {
			return column;
		}

		@XmlAttribute(name = "column", required = true)
		public void setColumn(String column) {
			this.column = column;
		}

	}
}
