/*package com.maizuo.seat.service.offer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.RequestLockObj;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.RequestUnlockObj;
import com.maizuo.seat.service.offer.RequestUsedSeatObj;
import com.maizuo.seat.util.SimpleObject;
import com.talkweb.pm.framework.FrameException;
import com.talkweb.wanda.app.AppService;
import com.talkweb.wanda.app.bean.Cinema;
import com.talkweb.wanda.app.bean.City;
import com.talkweb.wanda.app.bean.Film;
import com.talkweb.wanda.app.bean.Hall;
import com.talkweb.wanda.app.bean.Show;
import com.talkweb.wanda.app.bean.ShowPrice;
import com.talkweb.wanda.app.impl.AppServiceProxy;

public class WandaServiceImpl extends Common implements OfferService {
	private static AppService appService;

	public List<CinemaOffer> getCinemas() {
		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();
		CinemaOffer c = new CinemaOffer();
		StringBuffer hallIds = new StringBuffer();
		StringBuffer hallNames = new StringBuffer();
		StringBuffer seatCounts = new StringBuffer();
		StringBuffer vipFlags = new StringBuffer();

		try {
			Map<String, City> map = appService.doQueryCitys();
			for (String cityId : map.keySet()) {
				City city = map.get(cityId);
				Map<String, com.talkweb.wanda.app.bean.Cinema> cmap = appService.doQueryCityCinemas(city.getCityCode());

				for (String cinemaId : cmap.keySet()) {
					com.talkweb.wanda.app.bean.Cinema wdc = cmap.get(cinemaId);
					c = new CinemaOffer();
					c.setCinemaId(0);
					c.setOfferCityId(city.getCityCode());
					c.setCityName(city.getCityName());
					c.setOfferCinemaId(wdc.getCinemaId());
					c.setCinemaName(wdc.getCinemaName());
					c.setAddress(wdc.getAddress());
					c.setPhone(wdc.getTel());
					c.setLinkId("");
					c.setStatus(wdc.getSellState());

					Map<String, Hall> hmap = appService.doQueryHallByCinema(wdc.getCinemaId());
					hallIds.setLength(0);
					hallNames.setLength(0);
					seatCounts.setLength(0);
					vipFlags.setLength(0);
					for (Iterator<Hall> it = hmap.values().iterator(); it.hasNext();) {
						Hall hall = it.next();

						hallIds.append(hall.getHallId());
						hallNames.append(hall.getHallName());
						seatCounts.append(hall.getCapaCity());
						vipFlags.append(((hall.getVipFlag() == null) ? 0 : hall.getVipFlag()));

						if (it.hasNext()) {
							hallIds.append("-");
							hallNames.append("-");
							seatCounts.append("-");
							vipFlags.append("-");
						}
					}
					c.setHalls(hallIds.toString());
					c.setHallNames(hallNames.toString());
					c.setSeatCounts(seatCounts.toString());
					c.setVipFlags(vipFlags.toString());
					cinemaList.add(c);
				}
			}
		} catch (FrameException e) {
			throw new ServiceException(-1, e.getErrorMsg() + "(" + e.getErrorCode() + ")");
		}
		return cinemaList;
	}

	public List<FilmOffer> getFilms(RequestObj obj) {

		List<FilmOffer> filmList = new ArrayList<FilmOffer>();
		try {
			Map<String, String> showCode = new HashMap<String, String>();

			com.talkweb.wanda.app.bean.Cinema c = appService.doQueryCinemaInfo(obj.getOfferCinemaId());
			if (c.getSellState() == null && !c.getSellState().equals(0)) {
				return filmList;
			}

			Map<String, com.talkweb.wanda.app.bean.Show> map = appService.doQueryCinemaDayShows(obj.getOfferCinemaId(), obj.getShowDate());
			for (String id : map.keySet()) {
				com.talkweb.wanda.app.bean.Show sh = map.get(id);
				showCode.put(sh.getFilmPK(), sh.getDimensional());
			}

			for (String code : showCode.keySet()) {
				Film f = null;
				try {
					f = appService.doQueryFilmInfo(code);
					FilmOffer film = new FilmOffer();
					film.setFilmDuration(f.getDeration());
					film.setFilmDimesional(showCode.get(code));
					film.setFilmTitle(f.getFilmName());
					film.setFilmLanguage(f.getLang());
					film.setFilmName(f.getFilmName());
					film.setOfferFilmId(code);
					film.setCinemaId(obj.getOfferCinemaId());
					film.setFilmId(0);
					filmList.add(film);
				} catch (FrameException e) {
					e.printStackTrace();
					errorMsg = e.getErrorMsg() + "(" + e.getErrorCode() + ")";
				}
			}
			result = true;
		} catch (FrameException e) {
			e.printStackTrace();
			errorCode = -1;
			errorMsg = e.getErrorMsg() + "(" + e.getErrorCode() + ")";
		}
		return filmList;
	}

	public List<ShowOffer> getForetell(RequestObj obj) {
		List<ShowOffer> showList = new ArrayList<ShowOffer>();
		Map<String, String> kv = new HashMap<String, String>();
		Map<String, String> hallNames = new HashMap<>();
		// mm.getHallNames(offerCinemaId, true);
		try {

			Cinema c = appService.doQueryCinemaInfo(obj.getOfferCinemaId());
			if (c.getSellState() == null && !c.getSellState().equals(0)) {
				return showList;
			}

			Map<String, Show> map = appService.doQueryCinemaDayShows(obj.getOfferCinemaId(), obj.getShowDate());
			for (String id : map.keySet()) {

				com.talkweb.wanda.app.bean.Show sh = map.get(id);
				if (sh.getAvailchannel() != null && sh.getAvailchannel().equalsIgnoreCase("N")) {
					continue;
				}
				List<ShowPrice> splist = sh.getShowPriceList();

				if (splist == null) {
					continue;
				}
				for (ShowPrice sp : splist) {
					if (sp.getTicketId().equalsIgnoreCase(offerInfo.getType())) {
						ShowOffer show = new ShowOffer();
						show.setHallId(sh.getHallId());
						show.setHallName(hallNames.get(sh.getHallId()));
						show.setShowSeqNo(sh.getShowPK());
						show.setSeqNo(sh.getShowPK());
						show.setTime(sh.getShowTime());
						show.setCarrier(sh.getCarrier());
						if (show.getTime().length() == 3) { // 处理返回时间3位情况
							show.setTime("0" + show.getTime());
						} else if (show.getTime().length() == 1) { // 处理返回时间1位情况
							show.setTime("000" + show.getTime());
						} else if (show.getTime().length() == 2) { // 处理返回时间2位情况
							show.setTime("00" + show.getTime());
						}
						show.setOfferFilmId(sh.getFilmPK());
						show.setFilmDimesional(sh.getDimensional());
						show.setPriceStandard(sh.getPrice());// 市场价
						show.setSectionId(sp.getSectionId());
						show.setPriceLowest(sp.getPrice());

						String key = show.getTime() + show.getOfferFilmId() + show.getSectionId();
						if (kv.get(key) == null) {
							showList.add(show);
							kv.put(key, "seted");
						}
					}
				}
			}
			result = true;
		} catch (FrameException e) {
			e.printStackTrace();
			errorCode = -1;
			errorMsg = e.getErrorMsg() + "(" + e.getErrorCode() + ")";
		}
		return showList;
	}

	public boolean lockSeat(String orderId) {
		result = true;
		return true;
	}

	public boolean unLockSeat(String offerorderId) {

		boolean cancel = false;
		try {
			cancel = appService.doCancelOrder(offerorderId);
			result = true;
		} catch (FrameException e) {
			e.printStackTrace();
			errorCode = -1;
			errorMsg = e.getErrorMsg() + "(" + e.getErrorCode() + ")";
		}
		return cancel;
	}

	public List<SeatOffer> getSeats(RequestObj obj) {

		return null;
	}

	@Override
	public boolean getResult() {
		return false;
	}

	@Override
	public void setIsShowLog(boolean isShowLog) {

	}

	public void init() {
		offerInfo = OfferInfo.ins().get(4);
		appService = AppServiceProxy.init(offerInfo.getUrl(), offerInfo.getAccount(), offerInfo.getPassword(), offerInfo.getKey());
	}

	@Override
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unlockSeats(RequestUnlockObj obj) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void lockSeat(RequestLockObj obj) throws ServiceException {
	}
}
*/