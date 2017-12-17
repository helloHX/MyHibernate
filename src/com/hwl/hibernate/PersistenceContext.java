package com.hwl.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.entity.TableEntityPersister;

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

	public void addObjectToCache(EntityKey entityKey, Object object) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		entitiesByKey.remove(key);
		entitiesSnapshot.remove(key);
		entitiesByKey.put(entityKey, object);
		entitiesSnapshot.put(entityKey, object);// 添加快照中
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

	public Object getForeginEntity(ForeignKey foreignKey) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		return foreginEntity.get(key);
	}

	public void addForeginEntity(ForeignKey foreignKey, Object obj) {
		Set<ForeignKey> keySet = foreginEntity.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		foreginEntity.remove(key);
		foreginEntitySnapshot.remove(key);
		foreginEntity.put(foreignKey, obj);
		foreginEntitySnapshot.put(foreignKey, obj);// 添加到快照中
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

	public void addFrCollectionCache(ForeignKey foreignKey, Collection col) {
		Set<ForeignKey> keySet = frCollectionCache.keySet();
		ForeignKey key = findForeiginKeyInSet(keySet, foreignKey);
		frCollectionCache.remove(key);
		frCollectionCacheSnapshot.remove(key);
		frCollectionCache.put(foreignKey, col);
		frCollectionCacheSnapshot.put(foreignKey, col);// 添加到快照中
	}

	public Object getEntityFromCache(EntityKey entityKey) {
		Set<EntityKey> keySet = entitiesByKey.keySet();
		EntityKey key = findEntityKeyInSet(keySet, entityKey);
		return entitiesByKey.get(key);
	}

}
