package com.maizuo.seat.dao.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.maizuo.seat.dao.BaseSystemCacheDao;
import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.dao.mysql.CinemaDaoMysql;
import com.maizuo.seat.entity.Cinema;

public class CinemaDaoCache extends BaseSystemCacheDao<Cinema> implements CinemaDao {
	@Autowired
	private CinemaDaoMysql cinemaDaoMysql;

	public List<Cinema> getList(int offerId) {
		return super.getList(String.valueOf(offerId));
	}

	public Cinema get(int cinemaId) {
		return super.get(String.valueOf(cinemaId));
	}

	@Override
	public Cinema get(int offerId, int mzCinemaId) {

		return cinemaDaoMysql.get(offerId, mzCinemaId);
	}
}
