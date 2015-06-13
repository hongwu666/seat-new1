package com.maizuo.seat.dao;

import java.util.List;

import com.maizuo.seat.entity.User;

public interface UserDao {

	public User get(String objKey);

	public User get(int objKey);

	public List<User> getList(String listKey);

	public List<User> getList(int listKey);

	public List<User> getList();

}
