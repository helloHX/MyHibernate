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
 * @date 2017��12��8��
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
	 * @Description:���ҹ�������������д��ڣ����ڻ����л�ȡ��������治���ھʹ����ݿ��ѯ
	 * @date: 2017��12��13�� ����10:53:09
	 */
	public void load(LoadEvent event) {
		EntityKey entityKey = null;
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));// ʵ��ӳ��
		entityKey = new EntityKey(event.getEntityID(), event.getEntityPersister());
		Object obj = event.getEventSource().getPersistenceContext().getEntityFromCache(entityKey);
		if (obj != null) {// �����в�����
			event.setResult(obj);
		} else {
			// �����ݿ��л�ȡ
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
	 * @Description:�����ݿ��ѯ
	 * @date: 2017��12��13�� ����11:10:34
	 */
	public Object createObj(LoadEvent event) throws Exception {
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		EntityKey key = new EntityKey(event.getEntityID(), entityPersister);
		Object target = null;
		Object snapshot = null;
		target = event.getEventSource().getPersistenceContext().getEntitiesByKey(key);// �ӻ����������
		if (target == null) {// �����ݿ��м���
			Class<?> clazz = Class.forName(event.getEntityName());
			ResultSet resultSet = queryPersister((TableEntityPersister) event.getEntityPersister(),
					event.getEntityID());
			List<Map<String, Object>> hander = resultSetHander(resultSet);
			target = initObject(clazz, hander, entityPersister).get(0);// �������ͳ�ʼ��
			snapshot = initObject(clazz, hander, entityPersister).get(0);// �������ͳ�ʼ��
			
			event.getEventSource().getPersistenceContext().addObjectToCache(key, target,snapshot);// ���뻺����-�����
			
			tryLoadSubClass(entityPersister, target, event.getEventSource(), hander.get(0));
		}
		// ������ɺ�
		return target;
	}

	/**
	 * 
	 * @param entityPersister
	 *            ����
	 * @param target
	 *            Ŀ�����
	 * @param eventSource
	 * @param hander
	 * @throws Exception
	 * @return: void
	 * @author: huangWenLong
	 * @Description:����װ������
	 * @date: 2017��12��16�� ����12:52:43
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
					|| subClassPersister.getType().equals(SubClassPersister.rl_type.many_to_many)) {// һ�Զ�,��Զ�
				if (subClassPersister.isLazy()) {
					MyCollectionProxy collectionProxy = new MyCollectionProxy();
					Set<?> set = new HashSet<>();// ����ط�������һ�Զ�ֻ��ʹ��setʵ��
					fieldValue = collectionProxy.getInstance(set, subClassPersister, eventSource,
							(Serializable) hander.get(entityPersister.getColunmId()));// ����һ�������ش�����
				} else {
					fieldValue = eventSource.querySubClass(subClassPersister,
							(Serializable) hander.get(entityPersister.getColunmId()));
				}

			}
			if (subClassPersister.getType().equals(SubClassPersister.rl_type.one_to_one)
					|| subClassPersister.getType().equals(SubClassPersister.rl_type.many_to_one)) {// һ��һ,���һ
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
	 * @Description:���һ��һ��һ���������Ӷ���
	 * @date: 2017��12��15�� ����11:34:20
	 */
	public void subClassload(LoadSubCLassEvent event) throws Exception {
		// ������ʼ��event
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));
		ForeignKey foreignKey = new ForeignKey(event.getForeignkeyId(), event.getSubClassPersister());
		Object result = null;
		// �ӻ����в���
		result = event.getEventSource().getPersistenceContext().getForeginEntity(foreignKey);
		if (result == null) {
			try {
				// �������صĶ�����ʵ����
				ResultSet resultSet = queryPersister((TableEntityPersister) event.getEntityPersister(),
						event.getForeignkeyId());
				List<Map<String, Object>> hander = resultSetHander(resultSet);
				List<Object> obj = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				List<Object> snapshot = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				// �ж������ض����л����ǻ�����Ҫ���ص��Ӷ�������
				result = obj.get(0);// ����ֻ��һ��

				event.getEventSource().getPersistenceContext().addForeginEntity(foreignKey, result,snapshot.get(0));// ��ӵ����������

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
	 * @Description:���������Ӽ���
	 * @date: 2017��12��15�� ����11:35:01
	 */
	public void subClassQuery(LoadSubCLassEvent event) {
		// ������ʼ��event
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getEntityName()));
		// �������ϵĴ���
		ForeignKey foreignKey = new ForeignKey(event.getMainTableKey(), event.getSubClassPersister());
		// �����жϻ������Ƿ����
		Object result = null;
			result = event.getEventSource().getPersistenceContext().getFrCollectionCache(foreignKey);
		if(result == null){
			ResultSet resultSet = null;
			try {
				// һ�Զ�
				if (event.getSubClassPersister().getType().equals(SubClassPersister.rl_type.one_to_many)) {
					resultSet = queryOneToManySubClass((TableEntityPersister) event.getEntityPersister(),
							event.getSubClassPersister(), event.getMainTableKey());
				}
				// ��Զ�
				if (event.getSubClassPersister().getType().equals(SubClassPersister.rl_type.many_to_many)) {
					resultSet = queryManyToManySubClass((TableEntityPersister) event.getEntityPersister(),
							event.getSubClassPersister(), event.getMainTableKey());
				}
				List<Map<String, Object>> hander = resultSetHander(resultSet);
				List<Object> objList = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				List<Object> objListSnapshot = initObject(Class.forName(event.getEntityPersister().getClassName()), hander,
						(TableEntityPersister) event.getEntityPersister());
				// ��ӵ����ϻ�����
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
	 * @Description:���������ļ���
	 * @date: 2017��12��15�� ����4:31:12
	 */
	public void ssClassLoad(LoadSubCLassEvent event, List<Map<String, Object>> hander, List<Object> objList)
			throws Exception {
		// �����ӱ��й��������
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
	 * @Description:���ߴ����ݿ��в�Ѱ���Ľ������ʵ��
	 * @date: 2017��12��16�� ����11:48:34
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
	 * @Description:�������ݿ��в���������ݣ����������У��������
	 * @date: 2017��12��16�� ����3:04:44
	 */
	public List<Map<String, Object>> resultSetHander(ResultSet rs) throws SQLException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();

		HashMap<String, Object> m = null;
		// ���������
		while (rs.next()) {
			m = new HashMap<String, Object>();
			// ��������е����ݱ��浽HashMap��
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
	 *            ʵ����ӳ��
	 * @param id
	 *            ʵ���id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:��ѯ����ʵ��
	 * @date: 2017��12��16�� ����10:59:22
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
	 * @Description:����ʵ��ӳ�����Ľṹ��������Ҫ��ѯ������
	 * @date: 2017��12��16�� ����3:05:36
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
	 *            �ӱ��ӳ��
	 * @param subClassPersister
	 *            �ӱ������Ĺ�ϵ
	 * @param ownerID
	 *            ����id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:һ�Զ��������ѯ
	 * @date: 2017��12��16�� ����10:58:42
	 */
	public ResultSet queryOneToManySubClass(TableEntityPersister entityPersister, SubClassPersister subClassPersister,
			Serializable ownerID) throws SQLException {
		StringBuffer sql = createSelectPart(entityPersister);
		sql.append(" from " + entityPersister.getTableName());
		sql.append(" where " + entityPersister.getTableName() + "." + subClassPersister.getForeignKey());
		switch (subClassPersister.getOwner().getIdType()) {// ���������������
		case "string":// �ӱ�����������
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
	 *            �ӱ��ӳ��
	 * @param subClassPersister
	 *            �����м��
	 * @param ownerID
	 *            �����id
	 * @return
	 * @throws SQLException
	 * @return: ResultSet
	 * @author: huangWenLong
	 * @Description:��Զ��������ѯ
	 * @date: 2017��12��16�� ����10:57:54
	 */
	public ResultSet queryManyToManySubClass(TableEntityPersister entityPersister, SubClassPersister subClassPersister,
			Serializable ownerID) throws SQLException {

		StringBuffer sql = createSelectPart(entityPersister);

		sql.append(" from " + subClassPersister.getTableName() + " LEFT JOIN " + entityPersister.getTableName());
		sql.append(" ON (" + subClassPersister.getTableName() + "." + subClassPersister.getPrimaryKey() + " = "
				+ entityPersister.getTableName() + "." + entityPersister.getColunmId() + ")");
		sql.append(" where " + subClassPersister.getForeignKey());// ��Ҫ������Id��column����
		switch (subClassPersister.getOwner().getIdType()) {// ���������������
		case "string":// �ӱ�����������
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
