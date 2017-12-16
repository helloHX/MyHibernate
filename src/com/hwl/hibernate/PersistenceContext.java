package com.hwl.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.entity.TableEntityPersister;

/**
  * class PersistenceContext
  * @todo �ṩsession����
  * @author huangWenLong
  * @date 2017��12��11��
  */
public class PersistenceContext {
	private Session session;
	private Map<EntityKey,Object> entitiesByKey = new HashMap<>();
//	private Map<EntityKey,Object> entitiesSnapshot;//ʵ�����
	private Map<ForeignKey,Object> foreginEntity = new HashMap<>();
//	private Map<ForeignKey,Object> foreginEntitySnapshot;///���ʵ�����
	
	private Map<ForeignKey,Collection> frCollectionCache = new HashMap<>();//���ϻ���
//	private Map<ForeignKey,PersisterCollection> frCollectionCache;//����ʵ��
	
	public PersistenceContext(Session session) {
		super();
		this.session = session;
	}
	
	public void addObjectToCache(EntityKey entityKey,Object object) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			EntityKey key = (EntityKey) iterator.next();
			if(entityKey.equals(key)) {
				entitiesByKey.remove(key);
			}
		}
		entitiesByKey.put(entityKey, object);
	}
	
	
	public Object getEntitiesByKey(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			EntityKey key = (EntityKey) iterator.next();
			if(entityKey.equals(key)) {
				return entitiesByKey.get(key);
			}
		}
		return null;
	}

	
	public Object getForeginEntity(ForeignKey foreignKey) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if(foreignKey.equals(key)) {
				return foreginEntity.get(key);
			}
		}
		return null;
	}

	public void addForeginEntity(ForeignKey foreignKey, Object obj) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if(foreignKey.equals(key)) {
				foreginEntity.remove(key);
			}
		}
		foreginEntity.put(foreignKey, obj);
	}
	

	public Collection getFrCollectionCache(ForeignKey foreignKey) {
		Set<ForeignKey> keySet = frCollectionCache.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if(foreignKey.equals(key)) {
				return frCollectionCache.get(key);
			}
		}
		return null;
	}

	public void addFrCollectionCache(ForeignKey foreignKey, Collection col) {
		Set<ForeignKey> keySet = frCollectionCache.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if(foreignKey.equals(key)) {
				frCollectionCache.remove(key);
			}
		}
		foreginEntity.put(foreignKey, col);
	}

	public Object getEntityFromCache(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			EntityKey key = (EntityKey) iterator.next();
			if(entityKey.equals(key)) {
				return entitiesByKey.get(key);
			}
		}
		return null;
	}
	
}
