package com.hwl.hibernate.eventListener;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.EntityKey;
import com.hwl.hibernate.ForeignKey;
import com.hwl.hibernate.Session;
import com.hwl.hibernate.entityDBMapping.EntityPersister;
import com.hwl.hibernate.entityDBMapping.PersisterProperty;
import com.hwl.hibernate.entityDBMapping.SubClassPersister;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;
import com.hwl.hibernate.proxy.MyCollectionProxy;
import com.hwl.hibernate.proxy.MySingleProxy;
import com.hwl.hibernate.util.Log;
import com.hwl.hiernate.event.LoadEvent;
import com.hwl.hiernate.event.LoadSubCLassEvent;

/**
 * class LoadListener
 * 
 * @author huangWenLong
 * @date 2017年12月8日
 */
public class LoadListener implements Listener {
	private Connection connection;
	private final Log Log = new Log(LoadListener.class.getName());
	public LoadListener(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * 
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:查找工作，如果缓存中存在，就在缓存中获取，如果缓存不存在就从数据库查询
	 * @date: 2017年12月13日 上午10:53:09
	 */
	public void load(LoadEvent event) {
		EntityKey entityKey = null;
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));// 实体映射
		entityKey = new EntityKey(event.getEntityID(), event.getEntityPersister());
		Object obj = event.getEventSource().getPersistenceContext().getEntityFromCache(entityKey);
		if (obj != null) {// 缓存中不存在
			event.setResult(obj);
		} else {
			// 从数据库中获取
			try {
				event.setResult(createObj(event));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return: Object
	 * @author: huangWenLong
	 * @Description:从数据库查询
	 * @date: 2017年12月13日 下午11:10:34
	 */
	public Object createObj(LoadEvent event) throws Exception {
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		EntityKey key = new EntityKey(event.getEntityID(), entityPersister);
		Object target = null;
		Object snapshot = null;
		target = event.getEventSource().getPersistenceContext().getEntitiesByKey(key);// 从缓存里面加载
		if (target == null) {// 从数据库中加载
			Class<?> clazz = Class.forName(event.getEntityName());
			ResultSet resultSet = queryPersister((TableEntityPersister) event.getEntityPersister(),
					event.getEntityID());
			List<Map<String, Object>> hander = resultSetHander(resultSet);
			target = initObject(clazz, hander, entityPersister).get(0);// 基础类型初始化
			snapshot = initObject(clazz, hander, entityPersister).get(0);// 基础类型初始化
			
			event.getEventSource().getPersistenceContext().addObjectToCache(key, target,snapshot);// 加入缓存中-与快照
			
			tryLoadSubClass(entityPersister, target, event.getEventSource(), hander.get(0));
		}
		// 加载完成后
		return target;
	}

	/**
	 * 
	 * @param entityPersister
	 *            主表
	 * @param target
	 *            目标对象
	 * @param eventSource
	 * @param hander
	 * @throws Exception
	 * @return: void
	 * @author: huangWenLong
	 * @Description:尝试装载联表
	 * @date: 2017年12月16日 上午12:52:43
	 */
	public void tryLoadSubClass(TableEntityPersister entityPersister, Object target, Session eventSource,
			Map<String, Object> hander) throws Exception {
		Map<String, SubClassPersister> subclazzs = entityPersister.getSubClass();
		Set<String> keySet = subclazzs.keySet();

		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String fieldName = (String) iterator.next();

			SubClassPersister subClassPersister = subclazzs.get(fieldName);
			Object fieldValue = null;

			if (subClassPersister.getType().equals(SubClassPersister.rl_type.one_to_many)
					|| subClassPersister.getType().equals(SubClassPersister.rl_type.many_to_many)) {// 一对多,多对多
				if (subClassPersister.isLazy()) {
					MyCollectionProxy collectionProxy = new MyCollectionProxy();
					Set<?> set = new HashSet<>();// 这个地方限制了一对多只能使用set实现
					fieldValue = collectionProxy.getInstance(set, subClassPersister, eventSource,
							(Serializable) hander.get(entityPersister.getColunmId()));// 返回一个懒加载代理类
				} else {
					fieldValue = eventSource.querySubClass(subClassPersister,
							(Serializable) hander.get(entityPersister.getColunmId()));
				}

			}
			if (subClassPersister.getType().equals(SubClassPersister.rl_type.one_to_one)
					|| subClassPersister.getType().equals(SubClassPersister.rl_type.many_to_one)) {// 一对一,多对一
				if (subClassPersister.isLazy()) {
					MySingleProxy singleProxy = new MySingleProxy();
					Object proxied = Class.forName(subClassPersister.getClassName()).newInstance();
					fieldValue = singleProxy.getInstance(proxied, subClassPersister, eventSource,
							(Serializable) hander.get(subClassPersister.getForeignKey()));
				} else {
					fieldValue = eventSource.loadSubClass(subClassPersister,
							(Serializable) hander.get(subClassPersister.getForeignKey()));
				}
			}

			Class<?> clazz = target.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, fieldValue);
		}
	}

	/**
	 * 
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @throws Exception
	 * @Description:多对一，一对一加载联表子对象
	 * @date: 2017年12月15日 上午11:34:20
	 */
	public void subClassload(LoadSubCLassEvent event) throws Exception {
		// 继续初始化event
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));
		ForeignKey foreignKey = new ForeignKey(event.getForeignkeyId(), event.getSubClassPersister());
		Object result = null;
		// 从缓冲中查找
		result = event.getEventSource().getPersistenceContext().getForeginEntity(foreignKey);
		if (result == null) {
			try {
				// 将懒加载的对象真实加载
				ResultSet resultSet = queryPersister((TableEntityPersister) event.getEntityPersister(),
						event.getForeignkeyId());
				List<Map<String, Object>> hander = resultSetHander(resultSet);
				List<Object> obj = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				List<Object> snapshot = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				// 判断懒加载对象中还不是还有需要加载的子对象内容
				result = obj.get(0);// 这里只有一个

				event.getEventSource().getPersistenceContext().addForeginEntity(foreignKey, result,snapshot.get(0));// 添加到外键缓冲中

				ssClassLoad(event, hander, obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		event.setResult(result);
	}

	/**
	 * 
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @Description:加载联表子集合
	 * @date: 2017年12月15日 上午11:35:01
	 */
	public void subClassQuery(LoadSubCLassEvent event) {
		// 继续初始化event
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));
		// 单个集合的处理
		ForeignKey foreignKey = new ForeignKey(event.getMainTableKey(), event.getSubClassPersister());
		// 首先判断缓存中是否存在
		Object result = null;
			result = event.getEventSource().getPersistenceContext().getFrCollectionCache(foreignKey);
		if(result == null){
			ResultSet resultSet = null;
			try {
				// 一对多
				if (event.getSubClassPersister().getType().equals(SubClassPersister.rl_type.one_to_many)) {
					resultSet = queryOneToManySubClass((TableEntityPersister) event.getEntityPersister(),
							event.getSubClassPersister(), event.getMainTableKey());
				}
				// 多对多
				if (event.getSubClassPersister().getType().equals(SubClassPersister.rl_type.many_to_many)) {
					resultSet = queryManyToManySubClass((TableEntityPersister) event.getEntityPersister(),
							event.getSubClassPersister(), event.getMainTableKey());
				}
				List<Map<String, Object>> hander = resultSetHander(resultSet);
				List<Object> objList = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				List<Object> objListSnapshot = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				// 添加到集合缓存中
				Set<Object> objSet = new HashSet<>();
				objSet.addAll(objList);
				
				Set<Object> objSetSnapshot = new HashSet<>();
				objSet.addAll(objListSnapshot);
				
				event.getEventSource().getPersistenceContext().addFrCollectionCache(foreignKey, objSet,objSetSnapshot);
				ssClassLoad(event, hander, objList);
				event.setResult(objSet);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @param event
	 * @throws Exception
	 * @return: void
	 * @author: huangWenLong
	 * @Description:联表的联表的加载
	 * @date: 2017年12月15日 下午4:31:12
	 */
	public void ssClassLoad(LoadSubCLassEvent event, List<Map<String, Object>> hander, List<Object> objList)
			throws Exception {
		// 加载子表中关联的外表
		for (int i = 0; i < objList.size(); i++) {
			Object obj = objList.get(i);
			tryLoadSubClass((TableEntityPersister) event.getEntityPersister(), obj, event.getEventSource(),
					hander.get(i));
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param resultSet
	 * @param entityPersister
	 * @return
	 * @throws Exception
	 * @return: List<Object>
	 * @author: huangWenLong
	 * @Description:更具从数据库中查寻到的结果构建实体
	 * @date: 2017年12月16日 上午11:48:34
	 */
	public List<Object> initObject(Class<?> clazz, List<Map<String, Object>> resultSet,
			TableEntityPersister entityPersister) throws Exception {
		List<Object> resultList = new ArrayList<>();
		for (Iterator iterator = resultSet.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			Object target = clazz.newInstance();
			Map<String, PersisterProperty> propertys = entityPersister.getPropertys();
			Set<String> keySet = propertys.keySet();
			for (Iterator iterator2 = keySet.iterator(); iterator2.hasNext();) {
				String filedName = (String) iterator2.next();
				Field field = clazz.getDeclaredField(filedName);
				field.setAccessible(true);
				field.set(target, map.get(propertys.get(filedName).getColunm()));

			}
			resultList.add(target);
		}
		return resultList;
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @return: List<Map<String,Object>>
	 * @author: huangWenLong
	 * @Description:将从数据库中查出来的内容，放在容器中，方便访问
	 * @date: 2017年12月16日 下午3:04:44
	 */
	public List<Map<String, Object>> resultSetHander(ResultSet rs) throws SQLException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();

		HashMap<String, Object> m = null;
		// 遍历结果集
		while (rs.next()) {
			m = new HashMap<String, Object>();
			// 将结果集中的数据保存到HashMap中
			for (int i = 1; i <= cols; i++) {
				m.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			resultList.add(m);
		}
		return resultList;
	}

	/**
	 * 
	 * @param entityPersister
	 *            实体表的映射
	 * @param id
	 *            实体表id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:查询单个实体
	 * @date: 2017年12月16日 上午10:59:22
	 */
	public ResultSet queryPersister(TableEntityPersister entityPersister, Serializable id) throws SQLException {
		// = (TableEntityPersister) event.getEntityPersister();
		StringBuffer sql = createSelectPart(entityPersister);
		sql.append(" from " + entityPersister.getTableName());
		sql.append(" where " + entityPersister.getColunmId());
		switch (entityPersister.getIdType()) {
		case "string":
			sql.append(" = '" + id + "' ");
			break;
		case "boolean":
			sql.append(" = " + id + " ");
			break;
		case "integer":
			sql.append(" = " + id + " ");
			break;
		default:
			sql.append(" = '" + id + "' ");
			break;
		}
		Log.info(sql.toString());
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql.toString());
	}

	/**
	 * 
	 * @param entityPersister
	 * @return
	 * @return: StringBuffer
	 * @author: huangWenLong
	 * @Description:更具实体映射对象的结构，创建需要查询的内容
	 * @date: 2017年12月16日 下午3:05:36
	 */
	public StringBuffer createSelectPart(TableEntityPersister entityPersister) {
		StringBuffer sql = new StringBuffer("select ");

		Map<String, PersisterProperty> persisterPropertys = entityPersister.getPropertys();
		Set<String> keySet = persisterPropertys.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			sql.append(entityPersister.getTableName() + "." + persisterPropertys.get(key).getColunm() + ", ");
		}
		Map<String, String> foreignIds = entityPersister.getForeignId();
		keySet = foreignIds.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			sql.append(entityPersister.getTableName() + "." + foreignIds.get(key) + ", ");
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 2));
		return sql;
	}

	/**
	 * 
	 * @param entityPersister
	 *            子表的映射
	 * @param subClassPersister
	 *            子表和主表的关系
	 * @param ownerID
	 *            主表id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:一对多的外键表查询
	 * @date: 2017年12月16日 上午10:58:42
	 */
	public ResultSet queryOneToManySubClass(TableEntityPersister entityPersister, SubClassPersister subClassPersister,
			Serializable ownerID) throws SQLException {
		StringBuffer sql = createSelectPart(entityPersister);
		sql.append(" from " + entityPersister.getTableName());
		sql.append(" where " + entityPersister.getTableName() + "." + subClassPersister.getForeignKey());
		switch (subClassPersister.getOwner().getIdType()) {// 主表中组件的类型
		case "string":// 子表中主表的外键
			sql.append(" = '" + ownerID + "' ");
			break;
		case "boolean":
			sql.append(" = " + ownerID + " ");
			break;
		case "integer":
			sql.append(" = " + ownerID + " ");
			break;
		default:
			sql.append(" = '" + ownerID + "' ");
			break;
		}
		Log.info(sql.toString());
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql.toString());
	}

	/**
	 * 
	 * @param entityPersister
	 *            子表的映射
	 * @param subClassPersister
	 *            关联中间表
	 * @param ownerID
	 *            主表的id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:多对多的外键表查询
	 * @date: 2017年12月16日 上午10:57:54
	 */
	public ResultSet queryManyToManySubClass(TableEntityPersister entityPersister, SubClassPersister subClassPersister,
			Serializable ownerID) throws SQLException {

		StringBuffer sql = createSelectPart(entityPersister);

		sql.append(" from " + subClassPersister.getTableName() + " LEFT JOIN " + entityPersister.getTableName());
		sql.append(" ON (" + subClassPersister.getTableName() + "." + subClassPersister.getPrimaryKey() + " = "
				+ entityPersister.getTableName() + "." + entityPersister.getColunmId() + ")");
		sql.append(" where " + subClassPersister.getForeignKey());// 需要主表中Id的column名字
		switch (subClassPersister.getOwner().getIdType()) {// 主表中组件的类型
		case "string":// 子表中主表的外键
			sql.append(" = '" + ownerID + "' ");
			break;
		case "boolean":
			sql.append(" = " + ownerID + " ");
			break;
		case "integer":
			sql.append(" = " + ownerID + " ");
			break;
		default:
			sql.append(" = '" + ownerID + "' ");
			break;
		}
		Log.info(sql.toString());
		Statement statement = connection.createStatement();
		return statement.executeQuery(sql.toString());
	}

}
