package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemRedisDao;
import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.entity.Cinema;

public class CinemaDaoCache extends BaseSystemRedisDao<Cinema> implements CinemaDao {

	public List<Cinema> getList(int offerId) {
		return super.getList(String.valueOf(offerId));
	}

	public Cinema get(int cinemaId) {
		return super.get(String.valueOf(cinemaId));
	}

	@Override
	public Cinema get(int offerId, int mzCinemaId) {
		for (Cinema cinema : getList()) {
			if (cinema.getMzCinemaId() == mzCinemaId) {
				return cinema;
			}
		}
		return null;
	}
}
