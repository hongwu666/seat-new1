package com.maizuo.seat.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.maizuo.common.jdbc.JdbcImpl;
import com.maizuo.seat.entity.SystemMode;

public class BaseSystemCacheDao<T extends SystemMode> extends BasePreloadAble implements SystemCacheDao<T> {

	private List<T> list;

	private Map<String, T> objCache;

	private Map<String, List<T>> listCache;
	@Autowired
	private JdbcImpl jdbcImpl;

	private Class<T> entityClass;

	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public BaseSystemCacheDao() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	@Override
	public T get(String objKey) {
		if (objCache != null) {
			return objCache.get(objKey);
		}
		return null;
	}

	@Override
	public T get(int objKey) {
		return this.get(String.valueOf(objKey));
	}

	@Override
	public List<T> getList() {
		return list;
	}

	@Override
	public List<T> getList(String listKey) {
		if (listCache != null) {
			List<T> ll = listCache.get(listKey);
			return ll == null ? new ArrayList<T>() : ll;
		}
		return new ArrayList<T>();
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

	@Override
	public void initDate() {
		if (listCache != null) {
			listCache.clear();
		}

		if (objCache != null) {
			objCache.clear();
		}

		list = this.getAll();

		for (T model : list) {
			String objKey = model.getObjKey();
			String listKey = model.getListKey();
			if (StringUtils.isNotEmpty(listKey)) {
				if (listCache == null) {
					listCache = new ConcurrentHashMap<String, List<T>>();
				}
				List<T> l = listCache.get(listKey);
				if (l == null) {
					l = new ArrayList<T>();
				}
				l.add(model);
				listCache.put(listKey, l);
			}

			if (StringUtils.isNotEmpty(objKey)) {
				if (objCache == null) {
					objCache = new ConcurrentHashMap<String, T>();
				}
				objCache.put(objKey, model);
			}
		}
	}

}
