package com.maizuo.seat.service.offer.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.service.offer.RequestUsedSeatObj;
import com.maizuo.seat.util.DateUtils;
import com.maizuo.seat.util.MD5;
import com.maizuo.seat.util.UrlRequestUtils;

/**
 * 鼎新
 * 
 * @author Administrator
 * 
 */
public class DingXinServiceImpl extends Common implements OfferService {

	private String getDingXinSign(String paramStr) {
		String str = offerInfo.getAuthcode() + paramStr;
		String str2 = MD5.getVal(str) + offerInfo.getAuthcode();
		String secondMD5 = MD5.getVal(str2);
		return secondMD5;
	}

	private String dxHttpGet(String url) {
		Map<String, String> map = UrlRequestUtils.gzipGet(url);
		try {
			if (map.get("STATUS").equalsIgnoreCase("ERROR")) {
				String content = map.get("CONTENT");
				if (content.indexOf("<?") != 0) {
					content = "<?" + content;
				}
				Document doc = DocumentHelper.parseText(content);
				Element root = doc.getRootElement();
				int status = Integer.parseInt(root.elementText("status"));
				if (status == 0) {
					this.errorCode = Integer.parseInt(root.elementTextTrim("errorCode"));
					this.errorMsg = root.elementTextTrim("errorMessage");
					return "ERROR";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.errorCode = 1000;
			this.errorMsg = map.get("CONTENT");
			return "ERROR";
		}
		return map.get("CONTENT");
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getHallMsg(String cinemaId) {

		Map<String, String> map = new HashMap<String, String>();
		String params = "cid=" + cinemaId + "&format=xml&pid=" + offerInfo.getPid();
		String sign = getDingXinSign(params);
		StringBuffer sf = new StringBuffer();
		sf.append(offerInfo.getUrl()).append("/cinema/halls").append("/format/xml/cid/").append(cinemaId).append("/pid/").append(offerInfo.getPid()).append("/_sig/").append(sign);
		String hallUrl = sf.toString();

		String docStr = dxHttpGet(hallUrl);
		if (docStr.equalsIgnoreCase("ERROR")) {
			return null;
		}
		try {
			Document doc = DocumentHelper.parseText(docStr);
			Element root = doc.getRootElement();
			String status = root.elementText("status");

			if ("1".equals(status)) {
				Element data = root.element("data");
				Iterator<Element> items = data.elementIterator("item");

				String hallIds = "", hallNames = "", seatNums = "";
				while (items.hasNext()) {
					Element item = items.next();
					hallIds += "-" + item.elementText("id");
					hallNames += "-" + item.elementText("name");
					seatNums += "-" + item.elementText("seatNum");
				}
				if (hallIds.length() > 0) {
					hallIds = hallIds.substring(1);
					hallNames = hallNames.substring(1);
					seatNums = seatNums.substring(1);
				}
				map.put("ID", hallIds);
				map.put("NAME", hallNames);
				map.put("NUM", seatNums);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CinemaOffer> getCinemas() {

		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();
		StringBuffer sf = new StringBuffer();

		String params = "format=xml&pid=" + offerInfo.getPid();
		String sign = getDingXinSign(params);

		sf.append(offerInfo.getUrl()).append("/partner/cinemas").append("/format/xml/pid/").append(offerInfo.getPid()).append("/_sig/").append(sign);
		String cinemaUrl = sf.toString();

		String docStr = dxHttpGet(cinemaUrl);
		if (docStr.equalsIgnoreCase("ERROR")) {
			return null;
		}
		try {
			Document doc = DocumentHelper.parseText(docStr);
			Element root = doc.getRootElement();
			String status = root.elementText("status");

			if ("1".equals(status)) {
				Element data = root.element("data");
				Iterator<Element> items = data.elementIterator("item");
				while (items.hasNext()) {
					Element item = items.next();
					CinemaOffer cinema = new CinemaOffer();
					int cinemaId = Integer.parseInt(item.element("cinemaId").getText());
					String cinemaName = item.element("cinemaName").getText();

					Map<String, String> map = this.getHallMsg(item.element("cinemaId").getText());

					cinema.setOfferCinemaId(cinemaId + "");
					cinema.setCinemaName(cinemaName);
					if (map != null) {
						cinema.setHalls(map.get("ID") == null ? "" : map.get("ID"));
						cinema.setHallNames(map.get("NAME") == null ? "" : map.get("NAME"));
						cinema.setSeatCounts(map.get("NUM") == null ? "" : map.get("NUM"));
					}
					cinemaList.add(cinema);
				}
				result = true;
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return cinemaList;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<FilmOffer> getFilms(RequestObj obj) {

		List<FilmOffer> list = new ArrayList<FilmOffer>();
		String beginDate = obj.getShowDate();
		String endDate = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = df.parse(obj.getShowDate());
			date = DateUtils.add(date, Calendar.DATE, 1);
			endDate = df.format(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		StringBuffer sf = new StringBuffer();
		String params = "cid=" + obj.getOfferCinemaId() + "&end=" + endDate + "&format=xml&pid=" + offerInfo.getPid() + "&start=" + beginDate;
		String sign = getDingXinSign(params);

		sf.append(offerInfo.getUrl()).append("/cinema/plays").append("/format/xml/cid/").append(obj.getOfferCinemaId()).append("/pid/").append(offerInfo.getPid())
				.append("/start/").append(beginDate).append("/end/").append(endDate).append("/_sig/").append(sign);

		String docStr = dxHttpGet(sf.toString());
		if (docStr.equalsIgnoreCase("ERROR")) {
			return null;
		}
		try {
			Document doc = DocumentHelper.parseText(docStr);
			Element root = doc.getRootElement();
			String status = root.elementText("status");
			Map<String, String> films = new HashMap<String, String>();

			if ("1".equals(status)) {
				Element data = root.element("data");
				Iterator<Element> items = data.elementIterator("item");
				while (items.hasNext()) {
					Element movieItem = items.next();
					Element movieInfo = movieItem.element("movieInfo");
					FilmOffer film = null;
					Iterator<Element> movies = movieInfo.elementIterator("item");
					while (movies.hasNext()) {
						Element movieItemEle = movies.next();
						if (films.get(movieItemEle.elementText("cineMovieNum")) == null) {
							film = new FilmOffer();
							film.setFilmDuration("0");
							film.setFilmDimesional(movieItemEle.elementText("movieDimensional"));
							film.setFilmType("IMAX".equalsIgnoreCase(movieItemEle.elementText("movieSize")) ? "1" : "0");
							film.setFilmTitle(movieItemEle.elementText("movieName"));
							film.setFilmLanguage(movieItemEle.elementText("movieLanguage"));
							film.setFilmName(movieItemEle.elementText("movieName"));
							film.setOfferFilmId(movieItemEle.elementText("cineMovieNum"));
							film.setCinemaId(obj.getOfferCinemaId());
							film.setFilmId(0);
							list.add(film);

							films.put(movieItemEle.elementText("cineMovieNum"), "");
						}
					}
				}
				result = true;
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShowOffer> getForetell(RequestObj obj) {

		List<ShowOffer> showList = new ArrayList<ShowOffer>();
		String beginDate = obj.getShowDate();
		String endDate = "";

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = df.parse(obj.getShowDate());
			date = DateUtils.add(date, Calendar.DATE, 1);
			endDate = df.format(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		StringBuffer sf = new StringBuffer();
		String params = "cid=" + obj.getOfferCinemaId() + "&end=" + endDate + "&format=xml&pid=" + offerInfo.getPid() + "&start=" + beginDate;
		String sign = getDingXinSign(params);

		sf.append(offerInfo.getUrl()).append("/cinema/plays").append("/format/xml/cid/").append(obj.getOfferCinemaId()).append("/pid/").append(offerInfo.getPid())
				.append("/start/").append(beginDate).append("/end/").append(endDate).append("/_sig/").append(sign);

		String docStr = dxHttpGet(sf.toString());
		if (docStr.equalsIgnoreCase("ERROR")) {
			return null;
		}
		try {
			Document doc = DocumentHelper.parseText(docStr);
			Element root = doc.getRootElement();
			String status = root.elementText("status");

			if ("1".equals(status)) {
				Element data = root.element("data");
				Iterator<Element> items = data.elementIterator("item");
				while (items.hasNext()) {
					Element movieItem = items.next();
					Element movieInfo = movieItem.element("movieInfo");
					ShowOffer show = new ShowOffer();
					Iterator<Element> movies = movieInfo.elementIterator("item");
					while (movies.hasNext()) {
						Element movieItemEle = movies.next();
						show.setOfferFilmId(movieItemEle.elementText("cineMovieNum"));
						show.setFilmDimesional(movieItemEle.elementText("movieDimensional"));
					}
					show.setHallId(movieItem.elementText("hallId"));
					show.setHallName(movieItem.elementText("hallName"));
					show.setShowSeqNo(movieItem.elementText("id"));
					show.setSeqNo(movieItem.elementText("id"));
					String time = movieItem.elementText("startTime");
					time = movieItem.elementText("startTime").substring(11, 16).replace(":", "");
					show.setTime(time);
					show.setPriceStandard(movieItem.elementText("marketPrice"));// 市场价
					show.setSectionId("01");
					show.setPriceLowest(movieItem.elementText("lowestPrice"));
					show.setOfferLastUpdatetime(movieItem.elementText("cineUpdateTime") == null ? "" : movieItem.elementText("cineUpdateTime"));
					if ("1".equals(movieItem.elementText("allowBook"))) {
						showList.add(show);
					}
				}
				result = true;
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return showList;

	}

	@Override
	public List<SeatOffer> getSeats(RequestObj obj) {
		return null;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(5);
	}

	@Override
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
