package com.maizuo.seat.dao.mysql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.maizuo.common.jdbc.JdbcImpl;
import com.maizuo.common.jdbc.SqlParameter;
import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.ThirdCinameRelation;

@Repository
public class CinemaDaoMysql implements CinemaDao {

	@Autowired
	private JdbcImpl jdbcImpl;

	public String getOfferId(String mzCinameId) {

		return null;
	}

	/**
	 * 獲得賣座影院id與offerid <BR>
	 * time:2015-5-15 上午11:36:03
	 * 
	 * @author: sndy
	 * @return
	 */
	public List<Cinema> getList() {
		String sql = "SELECT syncCinemaId AS cinemaId,offerId FROM tDingZuoCenter_cinema";

		return this.jdbcImpl.getList(sql, Cinema.class);
	}

	public List<ThirdCinameRelation> getThirdCinema() {
		String sql = "SELECT thirdId ,cinameId FROM thirdCinameRelation ";
		return this.jdbcImpl.getList(sql, ThirdCinameRelation.class);
	}

	public List<Integer> getCinemaIds(int thirdId) {
		String sql = "SELECT cinameId FROM thirdCinameRelation WHERE thirdId = ?";

		SqlParameter parameter = new SqlParameter();
		parameter.setInt(thirdId);

		return this.jdbcImpl.getList(sql, Integer.class, parameter);
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
