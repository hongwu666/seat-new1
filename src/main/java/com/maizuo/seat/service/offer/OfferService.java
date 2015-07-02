package com.maizuo.seat.service.offer;

import java.util.List;

import com.maizuo.seat.entity.Order;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.RequestLockObj;
import com.maizuo.seat.service.offer.request.RequestObj;
import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;
import com.maizuo.seat.service.offer.request.RequestUnlockObj;
import com.maizuo.seat.service.offer.request.RequestUsedSeatObj;
import com.maizuo.seat.service.offer.request.RequestVerifyOrderObj;

public interface OfferService {
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
	public void lockSeat(RequestLockObj obj) throws ServiceException;

	/**
	 * 释放座位，解锁座位
	 * 
	 * @param obj
	 * @throws ServiceException
	 */
	public void unlockSeats(RequestUnlockObj obj) throws ServiceException;

	/**
	 * 拉电影，影片
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<FilmOffer> getFilms(RequestObj obj) throws ServiceException;

	/**
	 * 获取排期
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<ShowOffer> getForetell(RequestObj obj) throws ServiceException;

	/**
	 * 获取影厅座位
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getSeats(RequestObj obj) throws ServiceException;

	/**
	 * 获取排期座位图
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) throws ServiceException;

	/**
	 * 查询订单
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public Order queryOrder(RequestQueryOrderObj obj) throws ServiceException;

	/**
	 * 确认订单
	 * 
	 * @param obj
	 * @return
	 * @throws ServiceException
	 */
	public Order verifyOrder(RequestVerifyOrderObj obj) throws ServiceException;

	public void init();
}
