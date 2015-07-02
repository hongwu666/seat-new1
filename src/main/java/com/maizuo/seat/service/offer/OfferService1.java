package com.maizuo.seat.service.offer;

import java.util.List;

import com.maizuo.seat.entity.Order;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;

public interface OfferService1 {
	/**
	 * 请求获得的数据空
	 */
	public static final int response_null_error = 1092;

	/**
	 * 
	 */
	public static final int _error1 = 1041;
	/**
	 * 锁座位数量有误
	 */
	public static final int ticket_count = 1042;
	/**
	 * 远程访问请求失败
	 */
	public static final int request_fail = 1043;

	/**
	 * 拉取影院和城市
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<CinemaOffer> getCinemas() throws ServiceException;

	/**
	 * 锁座位
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public void lockSeat() throws ServiceException;

	/**
	 * 释放座位，解锁座位
	 * 
	 * @param obj
	 * @throws ServiceException
	 */
	public void unlockSeats() throws ServiceException;

	/**
	 * 拉电影，影片
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<FilmOffer> getFilms() throws ServiceException;

	/**
	 * 获取排期
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<ShowOffer> getForetell() throws ServiceException;

	/**
	 * 获取影厅座位
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getSeats() throws ServiceException;

	/**
	 * 获取排期座位图
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getUsedSeats() throws ServiceException;

	/**
	 * 查询订单
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public Order queryOrder() throws ServiceException;

	public Order verifyOrder() throws ServiceException;

	public void init();
}
