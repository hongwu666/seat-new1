package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemCacheDao;
import com.maizuo.seat.dao.HallsDao;
import com.maizuo.seat.entity.Halls;

public class HallsDaoCache extends BaseSystemCacheDao<Halls> implements HallsDao {

	@Override
	public List<Halls> getList(int cinemaId) {
		return super.getList(String.valueOf(cinemaId));
	}

	@Override
	public Halls get(int hassId) {
		return super.get(String.valueOf(hassId));
	}

}
