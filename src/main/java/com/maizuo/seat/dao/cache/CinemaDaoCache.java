package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemCacheDao;
import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.entity.Cinema;

public class CinemaDaoCache extends BaseSystemCacheDao<Cinema> implements CinemaDao {

	public List<Cinema> getList(int offerId) {
		return super.getList(String.valueOf(offerId));
	}

	public Cinema get(int cinemaId) {
		return super.get(String.valueOf(cinemaId));
	}
}
