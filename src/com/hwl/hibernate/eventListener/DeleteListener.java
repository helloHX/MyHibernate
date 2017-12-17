package com.hwl.hibernate.eventListener;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.hwl.hibernate.EntityKey;
import com.hwl.hibernate.entity.TableEntityPersister;
import com.hwl.hibernate.util.StringUtil;
import com.hwl.hiernate.event.DeleteEvent;

/**
  * class DeletListener
  * @author huangWenLong
  * @date 2017年12月12日
  */
public class DeleteListener implements Listener {
	private Connection connection;
	
	public DeleteListener( Connection connection) {
		super();
		this.connection = connection;
	}
	
	public void delete(DeleteEvent event) throws Exception {
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getObjName()));// 实体映射
		Object obj= event.getObj();
		Class<?> clazz = obj.getClass();
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		String idFieldName = entityPersister.getEntityId();
		Method method = clazz.getMethod("get" + StringUtil.paseToName(idFieldName));
		event.setId((Serializable) method.invoke(obj));
		EntityKey entityKey = new EntityKey(event.getId(), event.getEntityPersister());
		Object cacheObj = event.getEventSource().getPersistenceContext().getEntityFromCache(entityKey);
		if(cacheObj == null) {
			throw new IllegalArgumentException("不能删除游离态对象");
		}else {
			//delete in DB and cache
			delectInDBCache(event);
		}
	}
	
	/**
	 * 
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @throws SQLException 
	 * @Description:创建删除对象的sql然后放入action队列中，等待事务的提交
	 * @date: 2017年12月17日 下午4:40:14
	 */
	public void delectInDBCache(DeleteEvent event) throws SQLException {
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		StringBuffer sql = new StringBuffer("DELETE FROM ");
		sql.append(entityPersister.getTableName());
		sql.append(" WHERE " + entityPersister.getTableName() + "." + entityPersister.getColunmId());
		switch (entityPersister.getIdType()) {// 主表中组件的类型
		case "string":// 子表中主表的外键
			sql.append(" = '" + event.getId() + "' ");
			break;
		case "boolean":
			sql.append(" = " + event.getId() + " ");
			break;
		case "integer":
			sql.append(" = " + event.getId() + " ");
			break;
		default:
			sql.append(" = '" + event.getId() + "' ");
			break;
		}
		if(!event.getEventSource().addDeleteAction(sql.toString())) {
			Statement sta = connection.createStatement();
			 sta.execute(sql.toString());
		}
		EntityKey entityKey = new EntityKey(event.getId(), event.getEntityPersister());
		event.getEventSource().getPersistenceContext().delectEntityBykey(entityKey);
	}
}
