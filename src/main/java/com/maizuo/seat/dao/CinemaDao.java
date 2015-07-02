package com.maizuo.seat.dao;

import java.util.List;

import com.maizuo.seat.entity.Cinema;

public interface CinemaDao {

	public List<Cinema> getList(int offerId);

	public Cinema get(int offerId, int mzCinemaId);
	
	public Cinema get(String cinemaId);
}
