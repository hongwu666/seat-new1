package com.maizuo.seat.web.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maizuo.seat.factory.OfferServiceFactory;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.ReslutBO;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;

/**
 * 影院相关action
 * 
 * @author Administrator
 * 
 */
@Controller
public class OfferController extends BaseController {
	@Autowired
	private OfferServiceFactory offerServiceFactory;

	/**
	 * 拉取影院及城市列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/cinemas" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatCinemas() {
		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		ReslutBO bo = new ReslutBO(service);
		bo.setList(service.getCinemas());
		return bo;
	}

	/**
	 * 拉取排期列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/foretells" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatForetells() {
		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId("31051201");
		obj.setLinkId("492");
		obj.setShowDate("2015-06-05");
		List<ShowOffer> showOffer = service.getForetell(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setShowOffers(showOffer);
		return bo;
	}

	/**
	 * 拉取影厅座位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/halls" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatHalls() {
		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId("31051201");
		obj.setLinkId("492");
		obj.setHallId("2");
		List<SeatOffer> list = service.getSeats(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setSeatOffers(list);
		return bo;
	}

	/**
	 * 获取当日影院上映电影列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/movies" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatMovies() {
		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId("31051201");
		obj.setLinkId("492");
		obj.setShowDate("2015-06-05");
		List<FilmOffer> list = service.getFilms(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setFilmOffers(list);
		return bo;

	}
}
