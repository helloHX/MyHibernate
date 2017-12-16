package com.hwl.hibernate;

import java.io.Serializable;

import com.hwl.hibernate.entity.EntityPersister;
import com.hwl.hibernate.entity.SubClassPersister;
import com.hwl.hibernate.entity.TableEntityPersister;

/**
  * class ForeignKey
  * @author huangWenLong
  * @date 2017年12月14日
  */
public class ForeignKey {
	private  Serializable foreignKey;//主表中的外键
	private SubClassPersister subClassPersister;
	
	public ForeignKey() {
		super();
	}
	public ForeignKey(Serializable foreignKey, SubClassPersister subClassPersister) {
		super();
		this.foreignKey = foreignKey;
		this.subClassPersister = subClassPersister;
	}
	public Serializable getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(Serializable foreignKey) {
		this.foreignKey = foreignKey;
	}

	
	public SubClassPersister getSubClassPersister() {
		return subClassPersister;
	}
	public void setSubClassPersister(SubClassPersister subClassPersister) {
		this.subClassPersister = subClassPersister;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ForeignKey) {
			ForeignKey key1 = (ForeignKey)obj;
			if(key1.getForeignKey().equals(this.foreignKey)
					&& subClassPersister.getClassName().equals(key1.getSubClassPersister().getClassName())
					&& subClassPersister.getOwner().getClassName()
					.equals(key1.getSubClassPersister().getOwner().getClassName())) {
				return true;
			}
		}
		return false;
	}
	
	
}
