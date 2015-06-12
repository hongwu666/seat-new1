package com.maizuo.seat.service;

import com.maizuo.seat.bo.xml.CinemaBO;
import com.maizuo.seat.entity.Cinema;

public interface CinemaService {
	/**
	 * 
	 * @param offerId
	 * @param datetime
	 * @param sign
	 * @return
	 */
	public CinemaBO getCinemas(int thirdId, String datetime, String sign);

	/**
	 * 根据卖座影院id获得影院
	 * 
	 * @param mzCinemaId
	 * @return
	 */
	public Cinema getCinema(int offerId, int mzCinemaId);
}
