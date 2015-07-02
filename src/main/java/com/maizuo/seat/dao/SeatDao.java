package com.maizuo.seat.dao;

import com.maizuo.seat.entity.Seat;

public interface SeatDao {

	public Seat get(String offerCinemaId, String hallId, String sectionId, String rowId, String columnId);
}
