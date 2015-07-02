package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemRedisDao;
import com.maizuo.seat.dao.HallsDao;
import com.maizuo.seat.entity.Halls;

public class HallsDaoCache extends BaseSystemRedisDao<Halls> implements HallsDao {

	@Override
	public List<Halls> getList(String cinemaId) {
		return super.getList(cinemaId);
	}

	@Override
	public Halls get(int hassId) {
		return super.get(String.valueOf(hassId));
	}

}
