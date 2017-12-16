package com.hwl.hibernate.entity;

/**
  * class JoinedSubclassEntityPersister
  * @author huangWenLong
  * @date 2017��12��11��
  */
public class SubClassPersister {
	private TableEntityPersister owner;
	private String name;
	private String className;
	private String tableName;
	private String foreignKey;//many_to_many�м���������id��column��/one-to-many�ӱ��������id������ʱXML���Ѿ���ʼ����
	private String primaryKey;//�����ӱ��id��column��
	private boolean lazy;
	private rl_type type;//����������������֮��Ĺ�ϵ
	
	public static enum rl_type{
		one_to_one,
		one_to_many,
		many_to_many,
		many_to_one
	}
	
	
	
	public TableEntityPersister getOwner() {
		return owner;
	}


	public void setOwner(TableEntityPersister owner) {
		this.owner = owner;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public SubClassPersister() {
		super();
	}


	public SubClassPersister(String name, String className, String tableName, String foreignKey, String primaryKey,
			boolean lazy, rl_type type,TableEntityPersister owner) {
		super();
		this.owner = owner;
		this.name = name;
		this.className = className;
		this.tableName = tableName;
		this.foreignKey = foreignKey;
		this.primaryKey = primaryKey;
		this.lazy = lazy;
		this.type = type;
	}


	public SubClassPersister(String name, String className, String foreignKey, boolean lazy,
			rl_type type,TableEntityPersister owner) {
		super();
		this.owner = owner;
		this.name = name;
		this.className = className;
		this.foreignKey = foreignKey;
		this.lazy = lazy;
		this.type = type;
	}



	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public rl_type getType() {
		return type;
	}

	public void setType(rl_type type) {
		this.type = type;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	@Override
	public String toString() {
		return "SubClassPersister [name=" + name + ", className=" + className + ", tableName=" + tableName
				+ ", foreignKey=" + foreignKey + ", primaryKey=" + primaryKey + ", lazy=" + lazy + ", type=" + type
				+ "]";
	}

	
}
