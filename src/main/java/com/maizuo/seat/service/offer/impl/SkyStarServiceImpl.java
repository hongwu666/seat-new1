package com.maizuo.seat.service.offer.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.RequestUsedSeatObj;
import com.maizuo.seat.util.MD5;
import com.maizuo.seat.util.UrlRequestUtils;

/**
 * 满天星
 * 
 * @author Administrator
 * 
 */
public class SkyStarServiceImpl extends Common implements OfferService {

	private static final String TOKEN_ID = "TOKEN_ID";

	private static final String TOKEN = "TOKEN";

	private String getMD5Params(LinkedHashMap<String, Object> ps) {
		return getMD5Params(ps, null);
	}

	private String getMD5Params(LinkedHashMap<String, Object> ps, Map<String, Object> token) {
		String str = offerInfo.getAccount();
		for (String key : ps.keySet()) {
			if (!key.equals("pAppCode") && !key.equals("pTokenID")) {
				str += ps.get(key);
			}
		}
		if (token != null) {
			str += token.get(TOKEN_ID);
			str += token.get(TOKEN);
		}
		str += offerInfo.getPassword();
		return MD5.getHEXVal(str.toLowerCase()).toLowerCase();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CinemaOffer> getCinemas() {
		reset();
		Map<String, Object> token = this.getToken();

		Document document = null;
		SAXReader saxReader = new SAXReader();
		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		String documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), "GetCinema", ps);

		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			errorCode = response_null_error;
			errorMsg = documentStr;
		}

		if (document == null) {
			errorCode = response_null_error;
		} else if (errorCode == 0) {

			Element rootE = document.getRootElement();
			errorCode = Integer.parseInt(rootE.element("ResultCode").getText());
			if (errorCode == 0) {
				result = true;
				Element cinemasE = rootE.element("Cinemas");
				Iterator iterator = cinemasE.elementIterator();
				while (iterator.hasNext()) {
					Element cinemaEl = (Element) iterator.next();

					CinemaOffer cinema = new CinemaOffer();
					cinema.setOfferCinemaId(cinemaEl.element("PlaceNo").getText());
					cinema.setCinemaName(cinemaEl.element("PlaceName").getText());
					cinema.setOfferCityId(cinemaEl.element("CityNo").getText());

					ps = new LinkedHashMap<String, Object>();
					ps.put("pAppCode", offerInfo.getAccount());
					ps.put("pCinemaID", cinema.getOfferCinemaId());
					ps.put("pTokenID", token.get(TOKEN_ID));
					verifyInfo = getMD5Params(ps, token);
					ps.put("pVerifyInfo", verifyInfo);

					documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), "GetHall", ps);

					try {
						document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
						Element srE = document.getRootElement();
						Element hallsE = srE.element("Halls");
						Iterator hi = hallsE.elementIterator();
						String hallIds = "", hallNames = "";
						while (hi.hasNext()) {
							Element hall = (Element) hi.next();
							hallIds += "-" + hall.elementText("HallNo");
							hallNames += "-" + hall.elementText("HallName");
						}
						if (hallIds.length() > 1) {
							hallIds = hallIds.substring(1);
							hallNames = hallNames.substring(1);
						}
						cinema.setHalls(hallIds);
						cinema.setHallNames(hallNames);
					} catch (Exception e1) {
					}
					cinemaList.add(cinema);
				}
			}
		}
		return cinemaList;
	}

	public Map<String, Object> getToken() {
		reset();
		Document document = null;
		SAXReader saxReader = new SAXReader();
		Map<String, Object> map = new HashMap<String, Object>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		String verifyInfo = getMD5Params(ps);
		ps.put("pVerifyInfo", verifyInfo);
		String documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), "GetToken", ps);

		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			errorCode = OfferService.response_null_error;
			errorMsg = documentStr;
		}

		if (document == null) {
			errorCode = OfferService.response_null_error;
		} else if (errorCode == 0) {

			Element rootE = document.getRootElement();
			errorCode = Integer.parseInt(rootE.element("ResultCode").getText());
			if (errorCode == 0) {
				result = true;
				map.put(TOKEN_ID, rootE.element("TokenID").getText());
				map.put(TOKEN, rootE.element("Token").getText());
			}
		}
		return map;
	}

	@Override
	public boolean getResult() {
		return result;
	}

	@Override
	public int getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public void setIsShowLog(boolean isShowLog) {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<FilmOffer> getFilms(RequestObj obj) {
		reset();
		Map<String, Object> token = this.getToken();

		List<FilmOffer> filmList = new ArrayList<FilmOffer>();
		Document document = null;
		Map<String, FilmOffer> map = new HashMap<String, FilmOffer>();
		SAXReader saxReader = new SAXReader();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pPlanDate", obj.getShowDate());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);
		String documentStr = UrlRequestUtils.xfireCall(obj.getShowDate(), "GetCinemaPlan", ps);

		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			errorCode = response_null_error;
			errorMsg = documentStr;
		}

		if (document == null) {
			errorCode = response_null_error;
		} else if (errorCode == 0) {

			Element rootE = document.getRootElement();
			errorCode = Integer.parseInt(rootE.element("ResultCode").getText());
			if (errorCode == 0) {
				result = true;
				Element filmsEl = rootE.element("CinemaPlans");
				Iterator iterator = filmsEl.elementIterator();
				while (iterator.hasNext()) {
					Element filmEl = (Element) iterator.next();
					FilmOffer film = new FilmOffer();
					film.setFilmType(filmEl.elementText("CopyType"));
					film.setFilmTitle(filmEl.elementText("FilmName"));
					film.setFilmLanguage(filmEl.elementText("CopyLanguage"));
					film.setFilmName(filmEl.elementText("FilmName"));
					film.setOfferFilmId(filmEl.elementText("FilmNo"));
					film.setCinemaId(obj.getOfferCinemaId());
					film.setFilmId(0);
					map.put(film.getOfferFilmId(), film);
				}
			}

			filmList.addAll(map.values());
		}
		return filmList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ShowOffer> getForetell(RequestObj obj) {
		reset();
		Map<String, Object> token = this.getToken();

		List<ShowOffer> showList = new ArrayList<ShowOffer>();
		Document document = null;
		SAXReader saxReader = new SAXReader();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pPlanDate", obj.getShowDate());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);
		String documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), "GetCinemaPlan", ps);

		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			errorCode = 1092;
			errorMsg = documentStr;
		}

		if (document == null) {
			errorCode = response_null_error;
		} else if (errorCode == 0) {

			Element rootE = document.getRootElement();
			errorCode = Integer.parseInt(rootE.element("ResultCode").getText());
			if (errorCode == 0) {
				result = true;
				Element filmsEl = rootE.element("CinemaPlans");
				Iterator iterator = filmsEl.elementIterator();
				while (iterator.hasNext()) {
					Element filmEl = (Element) iterator.next();

					int useSign = Integer.parseInt(filmEl.elementText("UseSign"));
					int set = Integer.parseInt(filmEl.elementText("SetClose"));
					if (useSign == 0 && set == 1) {
						// if(useSign == 0){
						ShowOffer show = new ShowOffer();
						show.setHallId(filmEl.elementText("HallNo"));
						show.setHallName(filmEl.elementText("HallName"));
						show.setShowSeqNo(filmEl.elementText("FeatureAppNo"));
						show.setSeqNo(filmEl.elementText("FeatureAppNo"));
						show.setTime(filmEl.elementText("FeatureTime").replaceAll("\\:", ""));
						show.setOfferFilmId(filmEl.elementText("FilmNo"));
						show.setPriceStandard(filmEl.elementText("StandPric"));
						show.setPriceLowest(filmEl.elementText("AppPric"));
						showList.add(show);
					}
				}
			}

		}
		return showList;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SeatOffer> getSeats(RequestObj obj) {
		reset();
		Map<String, Object> token = this.getToken();

		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		SeatOffer seat = null;
		Document document = null;
		SAXReader saxReader = new SAXReader();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pHallID", obj.getHallId());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);
		String documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), "GetHallSite", ps);

		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			errorCode = response_null_error;
			errorMsg = documentStr;

		}

		if (document == null) {
			errorCode = response_null_error;
			return null;
		} else {
			Element rootE = document.getRootElement();
			errorCode = Integer.parseInt(rootE.element("ResultCode").getText());
			if (errorCode == 0) {
				result = true;
				Element seatsEl = rootE.element("HallSites");
				Iterator iterator = seatsEl.elementIterator();
				while (iterator.hasNext()) {
					Element seatEl = (Element) iterator.next();
					seat = new SeatOffer();
					seat.setOfferSeatId(seatEl.elementText("SeatNo"));
					seat.setRowId(seatEl.elementText("SeatRow"));
					seat.setColumnId(seatEl.elementText("SeatCol"));
					seat.setRowNum(seatEl.elementText("GraphRow"));
					seat.setColumnNum(seatEl.elementText("GraphCol"));
					seat.setSectionId(seatEl.elementText("SeatPieceNo"));
					seatplanList.add(seat);
				}
			}
		}
		if (errorCode == 0) {
			result = true;
		}
		return seatplanList;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(2);
	}

	@Override
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
