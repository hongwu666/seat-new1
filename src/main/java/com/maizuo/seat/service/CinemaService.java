package com.maizuo.seat.service;

import com.maizuo.seat.bo.xml.CinemaBO;


public interface CinemaService {
	/**
	 * 
	 * @param offerId
	 * @param datetime
	 * @param sign
	 * @return
	 */
	public CinemaBO getCinemas(int thirdId, String datetime, String sign);

}
