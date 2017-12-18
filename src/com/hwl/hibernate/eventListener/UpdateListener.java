package com.hwl.hibernate.eventListener;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.EntityKey;
import com.hwl.hibernate.entityDBMapping.PersisterProperty;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;
import com.hwl.hiernate.event.UpdateEvent;

/**
 * class UpdateListener
 * 
 * @author huangWenLong
 * @date 2017年12月12日
 */
public class UpdateListener implements Listener {
	private Connection connection;

	public UpdateListener(Connection connection) {
		super();
		this.connection = connection;
	}

	public void update(UpdateEvent event) {
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getObjName()));// 实体映射
		updateDB(event);
		updateSuClass(event);
	}

	/**
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @Description:TODO
	 * @date: 2017年12月17日 下午10:43:59
	 */
	private void updateSuClass(UpdateEvent event) {

	}

	/**
	 * 
	 * @param entityPersister
	 * @return
	 * @return: StringBuffer
	 * @author: huangWenLong
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @Description:TODO
	 * @date: 2017年12月17日 下午10:44:36
	 */
	public StringBuffer createPartSql(TableEntityPersister entityPersister, Object obj1, Object obj2) throws Exception {
		boolean ischange = false;// 是否存在属性发生改变
		StringBuffer sql = new StringBuffer("UPDATE ");
		sql.append(entityPersister.getTableName() + " SET ");
		Class<?> clazz = obj1.getClass();
		Map<String, PersisterProperty> persisterPropertys = entityPersister.getPropertys();
		Set<String> keySet = persisterPropertys.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			PersisterProperty property = persisterPropertys.get(key);
			String filedName = property.getName();
			Field filed = clazz.getDeclaredField(filedName);
			filed.setAccessible(true);
			if (!filed.get(obj1).equals(filed.get(obj2))) {
				ischange = true;
				switch (property.getType()) {// 主表中组件的类型
				case "string":
					sql.append(property.getColunm() + " = '" + filed.get(obj1) + "' ,");
					break;
				case "boolean":
					sql.append(property.getColunm() + " = " + filed.get(obj1) + " ,");
					break;
				case "integer":
					sql.append(property.getColunm() + " = " + filed.get(obj1) + " ,");
					break;
				default:
					sql.append(property.getColunm() + " = '" + filed.get(obj1) + "' ,");
					break;
				}
			}
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 2));
		sql.append(" WHERE " + entityPersister.getColunmId());
		Field idFiled = obj1.getClass().getDeclaredField(entityPersister.getEntityId());
		idFiled.setAccessible(true);

		switch (entityPersister.getIdType()) {
		case "string":
			sql.append(" = '" + idFiled.get(obj1) + "' ");
			break;
		case "boolean":
			sql.append(" = " + idFiled.get(obj1) + " ");
			break;
		case "integer":
			sql.append(" = " + idFiled.get(obj1) + " ");
			break;
		default:
			sql.append(" = '" + idFiled.get(obj1) + "' ");
			break;
		}
		if (ischange == false) {
			return null;
		} else {
			return sql;
		}
	}

	/**
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @Description:TODO
	 * @date: 2017年12月17日 下午10:43:57
	 */
	private void updateDB(UpdateEvent event) {
		EntityKey entityKey = null;
		entityKey = new EntityKey(event.getId(), event.getEntityPersister());
		Object obj2 = event.getEventSource().getPersistenceContext().getEntitiesSnapshotByKey(entityKey);
		try {
			StringBuffer sql = createPartSql((TableEntityPersister) event.getEntityPersister(), event.getObject(),
					obj2);
			if (sql != null) {//发生变化
				if (!event.getEventSource().addUpdateAction(sql.toString())) {
					Statement sta = connection.createStatement();
					sta.execute(sql.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
