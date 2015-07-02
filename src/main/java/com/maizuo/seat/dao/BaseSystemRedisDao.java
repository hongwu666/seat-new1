package com.maizuo.seat.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.maizuo.common.jdbc.JdbcImpl;
import com.maizuo.seat.entity.DefaultSystemRedisMode;
import com.maizuo.seat.util.FastJsonUtils;
import com.maizuo.seat.util.JedisUtils;

public class BaseSystemRedisDao<T extends DefaultSystemRedisMode<?>> extends BasePreloadAble implements SystemRedisDao<T> {

	@Autowired
	private JdbcImpl jdbcImpl;

	private Class<T> entityClass;

	private List<T> listmode;
	private Map<String, String> objmap;
	private Map<String, List<Object>> objlistmap;
	private Map<String, String> strlistomap;
	private String obj;
	private String list;

	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public BaseSystemRedisDao() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class) params[0];
		obj = entityClass.getSimpleName() + "_obj";
		list = entityClass.getSimpleName() + "_list";
	}

	private List<T> getAll() {
		String table = className2TableName(entityClass.getSimpleName());
		String sql = "SELECT * FROM " + table;
		return this.jdbcImpl.getList(sql, entityClass);
	}

	private String className2TableName(String className) {
		char[] ch = className.toCharArray();
		StringBuffer buf = new StringBuffer();
		buf.append(ch[0]);
		for (int i = 1; i < ch.length; i++) {
			byte by = (byte) ch[i];
			if (by >= 65 && by < 90) {
				buf.append("_");
			}
			buf.append(ch[i]);
		}
		return buf.toString().toLowerCase();
	}

	public void initDate() {
		listmode = getAll();
		objmap = new HashMap<String, String>();
		objlistmap = new HashMap<String, List<Object>>();
		strlistomap = new HashMap<String, String>();
		for (T model : listmode) {
			String objKey = model.getObjKey();
			String listKey = model.getListKey();
			objmap.put(objKey, FastJsonUtils.toJson(model));
			List<Object> oo = objlistmap.get(listKey);
			if (oo == null) {
				oo = new ArrayList<Object>();
				objlistmap.put(listKey, oo);
			}
			oo.add(model);
		}
		if (JedisUtils.exists(obj)) {
			JedisUtils.delete(obj);
		}
		JedisUtils.setFieldsToObject(obj, objmap);

		if (JedisUtils.exists(list)) {
			JedisUtils.delete(list);
		}
		for (Iterator<Entry<String, List<Object>>> it = objlistmap.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<Object>> entry = it.next();
			String value = FastJsonUtils.toJson(entry.getValue());
			strlistomap.put(entry.getKey(), value);
		}
		JedisUtils.setFieldsToObject(list, strlistomap);
	}

	@Override
	public T get(String objKey) {
		if (JedisUtils.exists(obj)) {
			String jsonStr = JedisUtils.getFieldFromObject(obj, objKey);
			return FastJsonUtils.getBean(jsonStr, entityClass);
		}
		return null;
	}

	@Override
	public T get(int objKey) {
		return get(String.valueOf(objKey));
	}

	@Override
	public List<T> getList(String listKey) {
		if (JedisUtils.exists(list)) {
			String jsonStr = JedisUtils.getFieldFromObject(list, listKey);
			return FastJsonUtils.getBeans(jsonStr, entityClass);
		}
		return null;
	}

	@Override
	public List<T> getList(int listKey) {
		return getList(String.valueOf(listKey));
	}

	@Override
	public List<T> getList() {
		if (JedisUtils.exists(obj)) {
			List<String> jsonStr = JedisUtils.getMapValues(obj);
			return FastJsonUtils.toList(jsonStr, entityClass);
		}
		return null;
	}

}
