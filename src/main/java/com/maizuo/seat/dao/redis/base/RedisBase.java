package com.maizuo.seat.dao.redis.base;

import java.lang.reflect.ParameterizedType;


import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;

import com.maizuo.seat.util.FastJsonUtils;
import com.maizuo.seat.util.JedisUtils;

public abstract class RedisBase<T> implements IRedisBean<T> {

	private Class<T> entityClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RedisBase() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	@Override
	public void initEntry(String userId, T t) {
		if (t == null) {
			return;
		}
		String key = getCacheKey(userId);
		String json = FastJsonUtils.toJson(t);
		JedisUtils.setString(key, json);
	}

	@Override
	public void updateEntry(String userId, T t) {
		if (t == null) {
			return;
		}
		String key = getCacheKey(userId);
		long start = System.currentTimeMillis();
		//String json = Json.toJson(t);
		String json = FastJsonUtils.toJson(t);
		System.out.println(System.currentTimeMillis() - start + "jackson");
		JedisUtils.setString(key, json);
	}

	@Override
	public void delEntry(String userId) {
		String key = getCacheKey(userId);
		if (JedisUtils.exists(key)) {
			JedisUtils.delete(key);
		}
	}

	@Override
	public boolean existUserId(String userId) {
		String key = getCacheKey(userId);
		return JedisUtils.exists(key);
	}

	@Override
	public T getEntry(String userId) {
		String value = JedisUtils.get(getCacheKey(userId));
		if (StringUtils.isEmpty(value)==false) {
			long start = System.currentTimeMillis();
			//T t = Json.toObject(value, entityClass);
			T t =FastJsonUtils.getBean(value, entityClass);
			System.out.println(System.currentTimeMillis() - start + "jackson1");
			return t;
		}

		return null;
	}

	private String getCacheKey(String userId) {
		return this.getPreKey() + "_" + userId;
	}
}
