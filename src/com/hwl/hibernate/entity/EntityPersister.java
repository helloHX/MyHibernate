package com.hwl.hibernate.entity;

/**
  * class EntityPersister
  * @author huangWenLong
  * @date 2017Äê12ÔÂ11ÈÕ
  */
public interface EntityPersister {
	String getClassName();
	String getTableName();
	String getEntityId();
}
