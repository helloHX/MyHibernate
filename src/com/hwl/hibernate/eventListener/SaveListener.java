package com.hwl.hibernate.eventListener;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hwl.hibernate.EntityKey;
import com.hwl.hibernate.entityDBMapping.PersisterProperty;
import com.hwl.hibernate.entityDBMapping.SubClassPersister;
import com.hwl.hibernate.entityDBMapping.TableEntityPersister;
import com.hwl.hiernate.event.SaveEvent;

/**
 * class SaveListener
 * 
 * @author huangWenLong
 * @date 2017��12��12��
 */
public class SaveListener implements Listener {
	private Connection connection;

	public SaveListener(Connection connection) {
		super();
		this.connection = connection;
	}

	public void save(SaveEvent event) {
		event.setEntityPersister(event.getEventSource().getFactory().getMetaModel(event.getObjName()));// ʵ��ӳ��
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		try {
			//������趨Ϊ���id
			Object obj = event.getObject();
			Class<?> clazz = obj.getClass();
			Field idFiled = clazz.getDeclaredField(entityPersister.getEntityId());
			idFiled.setAccessible(true);
			idFiled.set(obj, event.getId());
			saveToDB(event);// ���������
			saveSubClass(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param event
	 * @return: void
	 * @author: huangWenLong
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @Description:�洢�������ӱ����
	 * @date: 2017��12��17�� ����7:56:47
	 */
	private void saveSubClass(SaveEvent event)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Object obj = event.getObject();
		Class<?> clazz = obj.getClass();
		TableEntityPersister entityPersister = (TableEntityPersister) event.getEntityPersister();
		Map<String, SubClassPersister> subclazzs = entityPersister.getSubClass();
		Set<String> keySet = subclazzs.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String fieldName = (String) iterator.next();
			SubClassPersister subClassPersister = subclazzs.get(fieldName);
			if (subClassPersister.isInverse()) {
				return;
			} else {
				Field filed = clazz.getDeclaredField(subClassPersister.getName());
				filed.setAccessible(true);
				Object subOjb = filed.get(obj);
				if (event.getEventSource().getPersistenceContext().isCache(subOjb)) {// ����
					updateAssociate(entityPersister,subClassPersister,subOjb);//����ط��Ĺ����ܴ�������������л�ȡ����
				} else {// �ж���û��save-update��all
					throw new IllegalArgumentException(subClassPersister.getName() + "��������̬���ʧ��");
				}
			}
		}
	}

	/**
	 * @param event
	 * @param subOjb
	 * @return: void
	 * @author: huangWenLong
	 * @Description:���������ļ�����������һ��һ��һ�Զ࣬��Զ�
	 * @date: 2017��12��17�� ����9:55:14
	 */
	private void updateAssociate(TableEntityPersister entityPersister
			,SubClassPersister subClassPersister, Object subOjb) {
		
	}

	/**
	 * 
	 * @param entityPersister
	 * @return
	 * @return: StringBuffer
	 * @author: huangWenLong
	 * @Description:����sql �в���
	 * @date: 2017��12��17�� ����7:50:51
	 */
	public StringBuffer createPartSql1(TableEntityPersister entityPersister) {
		StringBuffer sql = new StringBuffer("INSERT INTO ");
		sql.append(entityPersister.getTableName() + " (");
		Map<String, PersisterProperty> propertys = entityPersister.getPropertys();
		Set<String> nameSet = propertys.keySet();
		for (Iterator iterator = nameSet.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			PersisterProperty property = propertys.get(name);
			sql.append(property.getColunm() + ", ");
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 2));
		sql.append(") ");
		return sql;
	}

	/**
	 * 
	 * @param obj
	 * @param entityPersister
	 * @return
	 * @throws Exception
	 * @return: StringBuffer
	 * @author: huangWenLong
	 * @Description:����sqlvalues����
	 * @date: 2017��12��17�� ����7:50:23
	 */
	public StringBuffer createPartSql2(Object obj, TableEntityPersister entityPersister) throws Exception {
		Class<?> clazz = obj.getClass();
		StringBuffer sql = new StringBuffer("  VALUES ( ");
		Map<String, PersisterProperty> propertys = entityPersister.getPropertys();
		Set<String> nameSet = propertys.keySet();
		for (Iterator iterator = nameSet.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			PersisterProperty property = propertys.get(name);
			Field filed = clazz.getDeclaredField(property.getName());
			filed.setAccessible(true);
			switch (property.getType()) {// ���������������
			case "string":// �ӱ�����������
				sql.append(" '" + filed.get(obj) + "' , ");
				break;
			case "boolean":
				sql.append(" " + filed.get(obj) + " , ");
				break;
			case "integer":
				sql.append(" " + filed.get(obj) + " , ");
				break;
			default:
				sql.append(" " + filed.get(obj) + " , ");
				break;
			}
		}
		sql = new StringBuffer(sql.subSequence(0, sql.length() - 2));
		sql.append(") ");
		return sql;
	}

	public void saveToDB(SaveEvent event) throws Exception {
		StringBuffer sqlPart1 = createPartSql1((TableEntityPersister) event.getEntityPersister());
		StringBuffer sqlPart2 = createPartSql2(event.getObject(), (TableEntityPersister) event.getEntityPersister());
		String sql = sqlPart1.append(sqlPart2).toString();
		if (!event.getEventSource().addInsertAction(sql)) {
			Statement sta = connection.createStatement();
			sta.execute(sql);
		}
		// ���ּ��ϻ��ǵ������뻺����
		// EntityKey entityKey = new EntityKey(event.getId(),
		// event.getEntityPersister());
		// event.getEventSource().getPersistenceContext().delectEntityBykey(entityKey);
	}

}
