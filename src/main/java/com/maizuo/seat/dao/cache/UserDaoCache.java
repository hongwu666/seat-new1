package com.maizuo.seat.dao.cache;

import com.maizuo.seat.dao.BaseSystemRedisDao;
import com.maizuo.seat.dao.UserDao;
import com.maizuo.seat.entity.User;

public class UserDaoCache extends BaseSystemRedisDao<User> implements UserDao {

}
