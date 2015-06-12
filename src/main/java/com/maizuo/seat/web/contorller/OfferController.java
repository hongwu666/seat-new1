package com.maizuo.seat.web.contorller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.factory.OfferServiceFactory;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.ReslutBO;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.CinemaService;
import com.maizuo.seat.service.ForetellService;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.RequestUsedSeatObj;
import com.maizuo.seat.util.DateUtils;
import com.maizuo.seat.util.DocumentUtil;
import com.maizuo.seat.web.render.ViewRender;

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
	@Autowired
	private CinemaService cinemaService;
	@Autowired
	private ForetellService foretellService;
	private DocumentUtil du = new DocumentUtil();
	private Logger logger = Logger.getLogger(OfferController.class);

	/**
	 * 拉取影院及城市列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/cinemas" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatCinemas() {
		long start = System.currentTimeMillis();
		int offerId = this.getInt("offerId", 1);
		OfferService service = offerServiceFactory.getBean(offerId);
		ReslutBO bo = new ReslutBO(service);
		//List<CinemaOffer> cinemaList = service.getCinemas();
		bo.setList(service.getCinemas());
		/*
		 * du.setDocumentStartPot(); du.setRootElemnt(STATUS_NODE_NAME, 0);
		 * du.setRootElemnt(RESULTMSG_NODE_NAME, 0);
		 * du.setRootElemnt(OfferId_NODE_NAME, offerId); Element cnsElement =
		 * du.getRootElement().addElement("CinemaList"); for (int i = 0; i <
		 * cinemaList.size(); i++) { CinemaOffer c = cinemaList.get(i); Element
		 * cnElement = cnsElement.addElement("Cinema");
		 * cnElement.addAttribute("offerCinemaId", c.getOfferCinemaId());
		 * cnElement.addAttribute("offerCityId", c.getOfferCityId());
		 * cnElement.addAttribute("cityName", c.getCityName());
		 * cnElement.addAttribute("cinemaName", c.getCinemaName());
		 * cnElement.addAttribute("address", c.getAddress());
		 * cnElement.addAttribute("region", c.getRegion());
		 * cnElement.addAttribute("phone", c.getPhone());
		 * cnElement.addAttribute("halls", c.getHalls());
		 * cnElement.addAttribute("hallNames", c.getHallNames());
		 * cnElement.addAttribute("seatCounts", c.getSeatCounts());
		 * cnElement.addAttribute("vipflags", c.getVipFlags());
		 * cnElement.addAttribute("cinemaLinkId", c.getLinkId()); } try {
		 * ViewRender.writeXml(du.getDocument(), response.getOutputStream()); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
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
		long start = System.currentTimeMillis();
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDate", DateUtils.getDate());
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);
		List<FilmOffer> list = service.getFilms(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setFilmOffers(list);
		long end = System.currentTimeMillis();
		logger.error("seatMovies-----offerId---" + offerId + "----" + (end - start));
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
		long start = System.currentTimeMillis();
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDafate", DateUtils.getDate());
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setShowDate(showDate);
		List<ShowOffer> showOffer = service.getForetell(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setShowOffers(showOffer);
		long end = System.currentTimeMillis();
		logger.error("seatForetells-----offerId---" + offerId + "----" + (end - start));
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
		long start = System.currentTimeMillis();
		int offerId = this.getInt("offerId", 1);
		String hallId = this.getString("hallId", "1");
		int mzCinemaId = this.getInt("cinemaId", 1);
		String showDate = this.getString("showDate", DateUtils.getDate());
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestObj obj = new RequestObj();
		obj.setOfferCinemaId(cinema.getCinemaId());
		obj.setLinkId(cinema.getCinemaLinkId());
		obj.setHallId(hallId);
		List<SeatOffer> list = service.getSeats(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setSeatOffers(list);
		long end = System.currentTimeMillis();
		logger.error("seatHalls-----offerId---" + offerId + "----" + (end - start));
		return bo;
	}

	/**
	 * 拉取影厅座位列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/seat/uhalls" }, method = RequestMethod.GET)
	@ResponseBody
	public ReslutBO seatUseHalls() {
		long start = System.currentTimeMillis();
		int offerId = this.getInt("offerId", 1);
		int mzCinemaId = this.getInt("cinemaId", 1);
		Foretell foretell = foretellService.get("1a705cb6ec3e42e5960a743814bb4567");
		Cinema cinema = cinemaService.getCinema(offerId, mzCinemaId);
		OfferService service = offerServiceFactory.getBean(offerId);
		RequestUsedSeatObj obj = new RequestUsedSeatObj();
		obj.setCinemaId(foretell.getOfferCinemaId());
		obj.setCinemaLinkID(cinema.getCinemaLinkId());
		obj.setHallId(foretell.getHallId() + "");
		obj.setFilmId(foretell.getOfferMovieId());
		obj.setSectionId(foretell.getSectionId());
		obj.setShowSeqNo(foretell.getShowSeqNo());
		obj.setShowDate(foretell.getShowDate());
		obj.setShowTime(foretell.getShowTime());
		List<SeatOffer> list = service.getUsedSeats(obj);
		ReslutBO bo = new ReslutBO(service);
		bo.setSeatOffers(list);
		long end = System.currentTimeMillis();
		logger.error("seatHalls-----offerId---" + offerId + "----" + (end - start));
		return bo;
	}
}
