package com.hwl.hibernate.entity;

/**
  * class EntityPersister
  * @author huangWenLong
  * @date 2017��12��11��
  */
public interface EntityPersister {
	String getClassName();
	String getTableName();
	String getEntityId();
}
