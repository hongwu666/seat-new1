package com.maizuo.seat.web.contorller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maizuo.seat.bo.xml.CinemaBO;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.handler.QueryOrderObject;
import com.maizuo.seat.handler.QueryOrderPool;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.ReslutBO;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.CinemaService;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.request.RequestObj;
import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;
import com.maizuo.seat.service.offer.request.RequestUnlockObj;
import com.maizuo.seat.service.offer.request.RequestUsedSeatObj;
import com.maizuo.seat.state.error.ErrorFactory;
import com.maizuo.seat.util.DateUtils;

/**
 * 座位相关action
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = { "/seat" })
public class SeatController extends BaseController {

	private Logger logger = Logger.getLogger(SeatController.class);
	@Autowired
	private CinemaService cinemaService;

	/**
	 * 拉取影院及城市列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/cinemas" }, method = RequestMethod.GET)
	@ResponseBody
	public CinemaBO seatCinemas() {
		// int thirdId = this.getInt("bid", 1);
		// String dateTime = this.getString("datetime", DateUtils.getDate());
		// // String key = Constant.LOGIN_TO_URL;
		// String sign = this.getString("sign", "");
		CinemaBO bo = null;
		int offerId = this.getInt("offerId", 1);
		QueryOrderObject obj = new QueryOrderObject();
		obj.setTime(System.currentTimeMillis());
		obj.setOfferId(offerId);
		RequestQueryOrderObj queryOrderObj = new RequestQueryOrderObj();
		queryOrderObj.setCinemaId("sd");
		obj.setObj(queryOrderObj);
		QueryOrderPool pool = QueryOrderPool.getInstance();
		pool.add(obj);
		return bo;
	}

	/**
	 * 获取当日影院上映电影列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/movies" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatMovies() {
		int offerId = this.getInt("bid", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDate", DateUtils.getDate());
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = null;
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);

		List<FilmOffer> list = new ArrayList<FilmOffer>();
		ReslutBO bo = new ReslutBO();
		try {
			list = service.getFilms(obj);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		bo.setFilmOffers(list);
		return bo;

	}

	/**
	 * 拉取排期列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/foretells" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatForetells() {
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDate", DateUtils.getDate());
		Cinema cinema = null;
		OfferService service = null;
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);
		List<ShowOffer> showOffer = null;
		try {
			showOffer = service.getForetell(obj);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		ReslutBO bo = new ReslutBO();
		bo.setShowOffers(showOffer);
		return bo;
	}

	/**
	 * 拉取影厅座位列表
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = { "/halls" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatHalls() throws ServiceException {
		int offerId = this.getInt("offerId", 1);
		String hallId = this.getString("hallId", "1");
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDate", DateUtils.getDate());
		Cinema cinema = null;
		OfferService service = null;
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setHallId(hallId);
		List<SeatOffer> list = service.getSeats(obj);
		ReslutBO bo = new ReslutBO();
		bo.setSeatOffers(list);
		return bo;
	}

	/**
	 * 拉取影厅座位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/uhalls" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatUseHalls() {
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		Foretell foretell = null;
		Cinema cinema = null;
		OfferService service = null;
		RequestUsedSeatObj obj = new RequestUsedSeatObj();
		obj.setCinemaId(foretell.getOfferCinemaId());
		obj.setCinemaLinkID(cinema.getCinemaLinkId());
		obj.setHallId(foretell.getHallId());
		obj.setFilmId(foretell.getOfferMovieId());
		obj.setSectionId(foretell.getSectionId());
		obj.setShowSeqNo(foretell.getShowSeqNo());
		obj.setShowDate(foretell.getShowDate());
		obj.setShowTime(foretell.getShowTime());
		List<SeatOffer> list = null;
		ReslutBO bo = new ReslutBO();
		try {
			list = service.getUsedSeats(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
		}
		bo.setSeatOffers(list);
		return bo;
	}

	/**
	 * 拉取影厅座位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/unlock" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO unlock() {
		int offerId = this.getInt("offerId", 1);
		String orderId = this.getString("orderId", "11111111");
		int mzCinemaId = this.getInt("cinemaId", 5);
		Cinema cinema = null;
		OfferService service = null;
		RequestUnlockObj obj = new RequestUnlockObj();
		obj.setOrderId(orderId);
		if (cinema != null) {
			obj.setCinemaLinkId(cinema.getCinemaLinkId());
			obj.setCinemaId(cinema.getCinemaId());
		}
		obj.setTicketCount("1");
		obj.setRandKey("11231231");
		ReslutBO bo = new ReslutBO();
		try {
			service.unlockSeats(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
		}
		return bo;
	}

	/**
	 * 锁座位
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lock" }, method = RequestMethod.GET)
	public String lock() {

		return "view/index";
	}

}
