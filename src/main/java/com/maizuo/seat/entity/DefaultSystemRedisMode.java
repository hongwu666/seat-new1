package com.maizuo.seat.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class DefaultSystemRedisMode<T extends DefaultSystemRedisMode<?>> implements SystemRedisMode {

	private Class<T> entityClass;

	@SuppressWarnings("unused")
	private transient String obj;
	@SuppressWarnings("unused")
	private transient String list;
	@SuppressWarnings("unused")
	private transient String objKey;
	@SuppressWarnings("unused")
	private transient String listKey;
	@SuppressWarnings("unused")
	private transient String className;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClassName() {
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		entityClass = (Class) params[0];
		return entityClass.getSimpleName();
	}

	@Override
	public String getObj() {
		return getClassName() + "_obj";
	}

	@Override
	public String getList() {
		return getClassName() + "_list";
	}

}
