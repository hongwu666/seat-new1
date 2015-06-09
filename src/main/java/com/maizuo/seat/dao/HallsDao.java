package com.maizuo.seat.dao;

import java.util.List;

import com.maizuo.seat.entity.Halls;

public interface HallsDao {

	public List<Halls> getList(int cinemaId);

	public Halls get(int hassId);
}
