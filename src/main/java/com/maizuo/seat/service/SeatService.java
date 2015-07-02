package com.maizuo.seat.service;

import javax.servlet.http.HttpServletRequest;

import com.maizuo.seat.entity.Seat;

/**
 * 座位服务器
 * 
 * @author Administrator
 * 
 */
public interface SeatService {

	public String forwarderServer(String bufUrl, HttpServletRequest request);

	public Seat get(String offerCinemaId, String hallId, String sectionId, String rowId, String columnId);
}
