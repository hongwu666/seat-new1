package com.maizuo.seat.dao.redis.base;

import java.lang.reflect.ParameterizedType;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.maizuo.seat.util.FastJsonUtils;
import com.maizuo.seat.util.JedisUtils;

/**
 * map类型redis的实现基类
 * 
 * @author foxwang
 * 
 * @param <T>
 */
public abstract class RedisMapBase<T> implements IRedisMap<Map<String, T>, T> {

	private Class<T> entityClass;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RedisMapBase() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	@Override
	public void initEntry(String userId, Map<String, T> t) {
		if (t != null && !t.isEmpty()) {
			String key = getMyKey(userId);
			Map<String, String> mapTemp = new HashMap<String, String>();
			Iterator<Entry<String, T>> it = t.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, T> entry = it.next();
				mapTemp.put(entry.getKey(), FastJsonUtils.toJson(entry.getValue()));
			}
			JedisUtils.setObject(key, mapTemp);
		}
	}

	@Override
	public void updateEntry(String userId, Map<String, T> t) {
		initEntry(userId, t);
	}

	@Override
	public void delEntry(String userId) {
		String key = getMyKey(userId);
		JedisUtils.delete(key);
	}

	@Override
	public Map<String, T> getEntry(String userId) {
		String key = getMyKey(userId);
		Map<String, String> map = JedisUtils.getMap(key);
		Map<String, T> result = new HashMap<String, T>();
		if (map != null && map.size() > 0) {
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				result.put(entry.getKey(), FastJsonUtils.getBean(entry.getValue(), entityClass));
			}
			return result;
		}
		return null;
	}

	@Override
	public void delEntryEntry(String userId, String entryKey) {
		String key = getMyKey(userId);
		JedisUtils.delFieldFromObject(key, entryKey);
	}

	@Override
	public T getEntryEntry(String userId, String entryKey) {
		String key = getMyKey(userId);
		String value = JedisUtils.getFieldFromObject(key, entryKey);
		if (StringUtils.isEmpty(value) == false) {
			T result = FastJsonUtils.getBean(value, entityClass);
			return result;
		}
		return null;
	}

	@Override
	public boolean updateEntryEntry(String userId, String entryKey, T t) {
		if (t == null) {
			return false;
		}
		String key = getMyKey(userId);
		JedisUtils.setFieldToObject(key, entryKey, FastJsonUtils.toJson(t));
		return true;
	}

	@Override
	public boolean existUserId(String userId) {
		String key = getMyKey(userId);
		return JedisUtils.exists(key);
	}

	@Override
	public List<T> getAllEntryValue(String userId) {
		String key = getMyKey(userId);
		List<String> listResult = JedisUtils.getMapValues(key);
		if (listResult != null && listResult.size() > 0) {
			List<T> result = FastJsonUtils.toList(listResult, entityClass);
			return result;
		}
		return null;
	}

	private String getMyKey(String userId) {
		return this.getPreKey() + "_" + userId;
	}
}
