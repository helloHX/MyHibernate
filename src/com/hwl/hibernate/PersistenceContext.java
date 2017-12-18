package com.hwl.hibernate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.entityDBMapping.PersisterProperty;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;

/**
 * class PersistenceContext
 * 
 * @todo 提供session缓存
 * @author huangWenLong
 * @date 2017年12月11日
 */
public class PersistenceContext {
	private Session session;
	private Map<EntityKey, Object> entitiesByKey = new HashMap<>();
	private Map<EntityKey, Object> entitiesSnapshot = new HashMap<>();// 实体快照
	private Map<ForeignKey, Object> foreginEntity = new HashMap<>();
	private Map<ForeignKey, Object> foreginEntitySnapshot = new HashMap<>();/// 外键实体快照
	private Map<ForeignKey, Collection> frCollectionCache = new HashMap<>();// 集合缓存
	private Map<ForeignKey, Collection> frCollectionCacheSnapshot = new HashMap<>();// 集合实体

	public PersistenceContext(Session session) {
		super();
		this.session = session;
	}

	public void addObjectToCache(EntityKey entityKey, Object object,Object snapshot) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		entitiesByKey.remove(key);
		entitiesSnapshot.remove(key);
		entitiesByKey.put(entityKey, object);
		entitiesSnapshot.put(entityKey, snapshot);// 添加快照中
	}

	/**
	 * 
	 * @return: EntityKey
	 * @author: huangWenLong
	 * @Description:删除缓冲快照中的实体，实体成为游离态
	 * @date: 2017年12月17日 下午5:02:30
	 */
	public void delectEntityBykey(EntityKey entityKey){
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		entitiesByKey.remove(key);
		entitiesSnapshot.remove(key);
	}
	
	public Object getEntitiesByKey(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		return entitiesByKey.get(key);
	}
	
	public Object getEntitiesSnapshotByKey(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		return entitiesSnapshot.get(key);
	}

	public Object getForeginEntity(ForeignKey foreignKey) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		return foreginEntity.get(key);
	}

	public void addForeginEntity(ForeignKey foreignKey, Object obj,Object snapshot) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		foreginEntity.remove(key);
		foreginEntitySnapshot.remove(key);
		foreginEntity.put(foreignKey, obj);
		foreginEntitySnapshot.put(foreignKey, snapshot);// 添加到快照中
	}

	public ForeignKey findForeiginKeyInSet(Set<ForeignKey> keySet, ForeignKey foreignKey) {
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if (foreignKey.equals(key)) {
				return key;
			}
		}
		return null;
	}


	public EntityKey findEntityKeyInSet(Set<EntityKey> keySet, EntityKey entityKey) {
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			EntityKey key = (EntityKey) iterator.next();
			if (entityKey.equals(key)) {
				return key;
			}
		}
		return null;
	}

	public Collection getFrCollectionCache(ForeignKey foreignKey) {
		Set<ForeignKey> keySet = frCollectionCache.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		return frCollectionCache.get(key);
	}

	public void addFrCollectionCache(ForeignKey foreignKey, Collection col,Collection snapshot) {
		Set<ForeignKey> keySet = frCollectionCache.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		frCollectionCache.remove(key);
		frCollectionCacheSnapshot.remove(key);
		frCollectionCache.put(foreignKey, col);
		frCollectionCacheSnapshot.put(foreignKey, snapshot);// 添加到快照中
	}

	public Object getEntityFromCache(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		return entitiesByKey.get(key);
	}
	
	public boolean isCache(Object obj) {
		Collection<Object> entityCache = entitiesByKey.values();
		for (Iterator iterator = entityCache.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(obj.equals(object)) {
				return true;
			}
		}
		Collection<Object> foreginEntityCache = entitiesByKey.values();
		for (Iterator iterator = foreginEntityCache.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(obj.equals(object)) {
				return true;
			}
		}
		Collection<Collection> CollectionCache = frCollectionCache.values();
		for (Iterator iterator = CollectionCache.iterator(); iterator.hasNext();) {
			Collection collection = (Collection) iterator.next();
			for (Iterator iterator2 = collection.iterator(); iterator2.hasNext();) {
				Object object = (Object) iterator2.next();
				if(obj.equals(object)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isChangeEntity(Object obj,TableEntityPersister entityPersister) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			EntityKey key = (EntityKey) iterator.next();
			if (entitiesByKey.get(key).equals(obj)) {
				
				Object snapshot = entitiesSnapshot.get(key);
			}
		}
		Set<ForeignKey> foreiginKeySet = foreginEntity.keySet();
		for (Iterator iterator = foreiginKeySet.iterator(); iterator.hasNext();) {
			ForeignKey key = (ForeignKey) iterator.next();
			if (foreginEntity.get(key).equals(obj)) {
				//判断
				Object snapshot = foreginEntitySnapshot.get(key);
			}
		}
//		Set<ForeignKey> frCollectionkeySet = frCollectionCache.keySet();
//		for (Iterator iterator = frCollectionkeySet.iterator(); iterator.hasNext();) {
//			ForeignKey key = (ForeignKey) iterator.next();
//			Collection collection  = frCollectionCache.get(key);
//			for (Iterator iterator2 = collection.iterator(); iterator2.hasNext();) {
//				Object object = (Object) iterator2.next();
//				if(obj.equals(object)) {
//					//判断
//					Collection collectionSnapshot = frCollectionCache.get(key);
//					collectionSnapshot.
//				}
//			}
//		}
		return true;
	}
	
	public boolean judgeChange(Object obj1,Object obj2,TableEntityPersister entityPersister) throws Exception {
		Class<?> clazz = obj1.getClass();
		Map<String, PersisterProperty> persisterPropertys = entityPersister.getPropertys();
		Set<String> keySet = persisterPropertys.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			PersisterProperty property = persisterPropertys.get(key);
			String filedName = property.getName();
			Field filed = clazz.getDeclaredField(filedName);
			filed.setAccessible(true);
			if(filed.get(obj1).equals(filed.get(obj2))) {
				continue;
			}else {
				return false;
			}
		}
		return true;
	}

}
