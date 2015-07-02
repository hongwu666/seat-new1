package com.maizuo.seat.web.contorller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maizuo.seat.constant.Constant;
import com.maizuo.seat.dao.reload.ReloadManager;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.factory.OfferServiceFactory;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.ReslutBO;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.CinemaService;
import com.maizuo.seat.service.ForetellService;
import com.maizuo.seat.service.RequestLockObj;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.request.RequestObj;
import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;
import com.maizuo.seat.service.offer.request.RequestUnlockObj;
import com.maizuo.seat.service.offer.request.RequestUsedSeatObj;
import com.maizuo.seat.util.DateUtils;

/**
 * 影院相关action
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = { "/offer" })
public class OfferController extends BaseController {
	@Autowired
	private OfferServiceFactory offerServiceFactory;
	@Autowired
	private CinemaService cinemaService;
	@Autowired
	private ForetellService foretellService;

	/**
	 * 拉取影院及城市列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/cinemas" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatCinemas() {
		int offerId = this.getInt("offerId", 1);

		OfferService service = offerServiceFactory.getBean(offerId);

		List<CinemaOffer> cinemaList = null;
		ReslutBO bo = null;

		try {
			cinemaList = service.getCinemas();
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
		bo.setList(cinemaList);

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
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 5);
		String showDate = this.getString("showDate", DateUtils.getDate());

		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);

		List<FilmOffer> list = new ArrayList<FilmOffer>();
		ReslutBO bo = null;
		try {
			list = service.getFilms(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
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
		int mzCinemaId = this.getInt("cinemaId", 5);
		String showDate = this.getString("showDate", DateUtils.getDate());

		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);

		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);

		List<ShowOffer> showOffer = null;
		ReslutBO bo = null;
		try {
			showOffer = service.getForetell(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
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
		String hallId = this.getString("hallId", "01");
		int mzCinemaId = this.getInt("cinemaId", 5);

		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);

		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setHallId(hallId);

		List<SeatOffer> list = null;
		ReslutBO bo = null;
		try {
			list = service.getSeats(obj);
		} catch (ServiceException e) {
			e.printStackTrace();
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
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
		int mzCinemaId = this.getInt("cinemaId", 5);

		Foretell foretell = foretellService.get("16ffdd288f584115817f12b6d0c91eaa");
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);

		RequestUsedSeatObj obj = new RequestUsedSeatObj(foretell);
		obj.setCinemaLinkID(cinema.getCinemaLinkId());

		List<SeatOffer> list = null;
		ReslutBO bo = null;
		try {
			list = service.getUsedSeats(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
		bo.setSeatOffers(list);
		return bo;
	}

	/**
	 * 解锁訂單座位
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/unlock" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO unlock() {
		int offerId = this.getInt("offerId", 1);
		String orderId = this.getString("orderId", "111111111");
		int mzCinemaId = this.getInt("cinemaId", 5);

		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);

		RequestUnlockObj obj = new RequestUnlockObj();
		obj.setOrderId(orderId);

		if (cinema != null) {
			obj.setCinemaLinkId(cinema.getCinemaLinkId());
			obj.setCinemaId(cinema.getCinemaId());

		}
		obj.setTicketCount("1");
		obj.setRandKey("11231231");

		ReslutBO bo = null;
		try {
			service.unlockSeats(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
		return bo;
	}

	/**
	 * 锁座位
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lock" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO lock() {
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 5);
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);

		Foretell foretell = foretellService.get("16ffdd288f584115817f12b6d0c91eaa");
		OfferService service = offerServiceFactory.getBean(offerId);

		RequestLockObj obj = new RequestLockObj(foretell);
		obj.setTicketCount("1");
		obj.setCinemaId(cinema.getCinemaId());
		obj.setCinemaLinkId(cinema.getCinemaLinkId());
		obj.setMobilePhone(Constant.MOBILE_PHONE);
		obj.setPayType(Constant.SKYSTART_PAYTYPE_NEW);
		obj.setMzCinemaId(mzCinemaId + "");
		obj.setRandKey("111111111");
		obj.setOrderNo("111111111");
		obj.setSeatId("1:3");

		ReslutBO bo = null;
		try {
			service.lockSeat(obj);
		} catch (ServiceException e) {
			bo = new ReslutBO(e);
			return bo;
		}
		bo = new ReslutBO();
		return bo;
	}

	@RequestMapping(value = { "/reset" }, method = RequestMethod.GET)
	public void reset() {
		
		ReloadManager.inc().reload("all");
		
	}

	@RequestMapping(value = { "/verify/order" }, method = RequestMethod.GET)
	public void verifyOrder() {

	}

	@RequestMapping(value = { "/query/order" }, method = RequestMethod.GET)
	public void queryOrder() {

		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestQueryOrderObj obj = new RequestQueryOrderObj();
		// obj.s
		service.queryOrder(obj);
	}
}
