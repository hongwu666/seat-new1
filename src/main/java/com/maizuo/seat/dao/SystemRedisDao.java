package com.maizuo.seat.dao;

import java.util.List;

public interface SystemRedisDao<T> {

	public T get(String objKey);

	public T get(int objKey);

	public List<T> getList();

	public List<T> getList(String listKey);

	public List<T> getList(int listKey);
}
