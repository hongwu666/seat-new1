package com.maizuo.seat.dao.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.util.FastJsonUtils;
import com.maizuo.seat.util.JedisKey;
import com.maizuo.seat.util.JedisUtils;

@Repository
public class CinemaDaoRedis implements CinemaDao {

	public String getOfferId(String mzCinameId) {
		String key = JedisKey.mzCinma_offer;
		if (JedisUtils.exists(key)) {
			return JedisUtils.getFieldFromObject(key, mzCinameId);
		}
		return null;
	}

	public void add(List<Cinema> list) {
		Map<String, String> map = new HashMap<>();
		for (Cinema cinema : list) {
			map.put(String.valueOf(cinema.getId()), FastJsonUtils.toJson(cinema));
		}
		String key = JedisKey.getCinemas();

		JedisUtils.setObject(key, map);

	}

	public void addMzCinmaoffer(Map<String, String> map) {
		String key = JedisKey.mzCinma_offer;
		JedisUtils.setFieldsToObject(key, map);
		JedisUtils.setExpireKey(key, 24 * 60 * 60);
	}

	@Override
	public List<Cinema> getList(int offerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cinema get(int cinemaId) {
		// TODO Auto-generated method stub
		return null;
	}
}
