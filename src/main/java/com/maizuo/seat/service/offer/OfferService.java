package com.maizuo.seat.service.offer;

import java.util.List;

import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;

public interface OfferService {
	/**
	 * 请求获得的数据空
	 */
	public static final int response_null_error = 1092;

	/**
	 * 
	 */
	public static final int _error1 = 1041;
	
	

	public List<CinemaOffer> getCinemas();

	public boolean getResult();

	public int getErrorCode();

	public String getErrorMsg();

	public void setIsShowLog(boolean isShowLog);

	public List<FilmOffer> getFilms(RequestObj obj);

	public List<ShowOffer> getForetell(RequestObj obj);

	public List<SeatOffer> getSeats(RequestObj obj);
}
