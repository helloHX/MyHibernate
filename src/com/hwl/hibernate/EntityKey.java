package com.hwl.hibernate;

import java.io.Serializable;

import com.hwl.hibernate.entity.EntityPersister;
import com.hwl.hibernate.entity.TableEntityPersister;


/**
  * class EntityKey
  * @author huangWenLong
  * @date 2017Äê12ÔÂ13ÈÕ
  */
public class EntityKey {
	private final Serializable identifier;
	private final EntityPersister persister;
	
	public EntityKey(Serializable identifier, EntityPersister persister) {
		super();
		this.identifier = identifier;
		this.persister = persister;
	}

	public Serializable getIdentifier() {
		return identifier;
	}

	public EntityPersister getPersister() {
		return this.persister;
	}

	@Override
	public boolean equals(Object obj) {
		EntityKey entityKey1 = (EntityKey)obj;
		if(persister.getClassName().equals(entityKey1.getPersister().getClassName())) {
			if(persister.getEntityId().equals(entityKey1.getPersister().getEntityId())) {
				return true;
			}
		}
		return false;
	}
}
