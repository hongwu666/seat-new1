package com.maizuo.seat.service.offer.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.maizuo.seat.helper.StringHelper;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.RequestObj;
import com.maizuo.seat.util.UrlRequestUtils;

/**
 * 火凤凰
 * 
 * @author Administrator
 * 
 */
public class FirePhenixServiceImpl extends Common implements OfferService {

	private LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<>();

	private Map<String, Object> analyst(String method, LinkedHashMap<String, Object> params) {

		String documentStr = "";
		Document document = null;
		SAXReader saxReader = new SAXReader();
		Map<String, Object> map = new HashMap<String, Object>();
		String urlStr = offerInfo.getUrl();

		LinkedHashMap<String, Object> linkMap = new LinkedHashMap<>();
		linkMap.put("account", offerInfo.getAccount());
		linkMap.put("password", offerInfo.getPassword());
		if (params != null && params.size() > 0) {
			linkMap.putAll(params);
		}
		try {
			documentStr = UrlRequestUtils.xfireCall(urlStr, method, linkMap);

			if (StringUtils.isEmpty(documentStr)) {
				errorCode = response_null_error;
			} else {
				document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("gb2312")));
				if (document == null) {
					errorCode = response_null_error;
					errorMsg = documentStr;
				}
			}
		} catch (Exception e1) {
			errorCode = response_null_error;
			errorMsg = documentStr;
		}
		map.put("DOCUMENT", document);
		map.put("ERROR_CODE", errorCode);
		map.put("ERROR_MSG", errorMsg);
		return map;
	}

	@SuppressWarnings("rawtypes")
	public List<CinemaOffer> getCinemas() {

		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();
		Map<String, Object> map = this.analyst("qryCinema", null);

		if (check(map)) {

			Document document = (Document) map.get("DOCUMENT");
			Element cinemasEl = document.getRootElement();
			Iterator iterator = cinemasEl.elementIterator();

			CinemaOffer cinema = null;
			while (iterator.hasNext()) {
				Element cinemaEl = (Element) iterator.next();

				cinema = new CinemaOffer(cinemaEl);

				List hallNodes = cinemaEl.elements("hall");
				String hallIds = "", hallNames = "", seatCounts = "", vipFlags = "";
				for (int i = 0; i < hallNodes.size(); i++) {
					Element hel = (Element) hallNodes.get(i);
					hallIds += "-" + hel.attributeValue("id");
					hallNames += "-" + hel.attributeValue("name");
					seatCounts += "-" + hel.attributeValue("seatcount");
					vipFlags += "-" + (hel.attributeValue("vipflag").equals("Y") ? 1 : 0);
				}
				cinema.setHalls(hallIds.substring(1));
				cinema.setHallNames(hallNames.substring(1));
				cinema.setSeatCounts(seatCounts.substring(1));
				cinema.setVipFlags(vipFlags.substring(1));
				cinemaList.add(cinema);
			}
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<FilmOffer> getFilms(RequestObj obj) {

		List<FilmOffer> filmList = new ArrayList<FilmOffer>();
		paramsMap.clear();
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());
		paramsMap.put("showDate", obj.getShowDate());
		Map<String, Object> map = this.analyst("qryFilm", paramsMap);
		if (check(map)) {
			Document document = (Document) map.get("DOCUMENT");

			Element filmsEl = document.getRootElement();
			Iterator iterator = filmsEl.elementIterator();
			String _cinemaId = filmsEl.attributeValue("cinemaId");

			while (iterator.hasNext()) {
				Element filmEl = (Element) iterator.next();

				FilmOffer filmOffer = new FilmOffer(filmEl);
				filmOffer.setCinemaId(_cinemaId);

				filmList.add(filmOffer);
			}
		}
		return filmList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ShowOffer> getForetell(RequestObj obj) {

		String updateLevel = "0";
		List<ShowOffer> showList = new ArrayList<ShowOffer>();
		paramsMap.clear();
		paramsMap.put("updateLevel", updateLevel);
		paramsMap.put("showDate", obj.getShowDate());
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());
		Map<String, Object> map = this.analyst("qryShow", paramsMap);
		if (check(map)) {
			Document document = (Document) map.get("DOCUMENT");
			Element showsEl = document.getRootElement();
			Map<String, String> map0 = null;
			// Map<String, String> map0 = mm.getHallNames(cinemaId, true);
			Element resultEl = showsEl.element("result");
			if (resultEl != null) {
				int _result = Integer.parseInt(resultEl.getTextTrim());
				Element msgsEl = showsEl.element("messages");
				Element msgEl = msgsEl.element("message");
				if (_result != 0) {
					errorCode = _error1;
					this.errorMsg = msgEl.getText();
					return null;
				}
			}

			String _cinemaId = showsEl.attributeValue("cinemaId");
			for (Iterator i = showsEl.elementIterator(); i.hasNext();) {
				Element showEl = (Element) i.next();
				Element filmEl = showEl.element("film");

				if (filmEl == null) {
					continue;
				}
				String _hallId = showEl.attributeValue("hallId");
				String _seqNo = showEl.attributeValue("seqNo");
				String _showSeqNo = showEl.attributeValue("showSeqNo");
				String _date = showEl.attributeValue("date");
				String _time = showEl.attributeValue("time");
				String _updateLevel = showEl.attributeValue("updateLevel");
				String _updateType = showEl.attributeValue("updateType");
				Element priceEl = showEl.element("price");
				Element throughEl = showEl.element("through");

				String _throughFlag = throughEl.attributeValue("flag");

				String _priceLowest = priceEl.attributeValue("lowest");

				for (Iterator ii = priceEl.elementIterator(); ii.hasNext();) {
					ShowOffer showOffer = new ShowOffer(filmEl);
					showOffer.setThroughFlag(_throughFlag);
					showOffer.setFilmId(0);
					showOffer.setUpdateType(_updateType);
					showOffer.setUpdateLevel(_updateLevel);
					showOffer.setTime(_time);
					showOffer.setDate(_date);
					showOffer.setShowSeqNo(_showSeqNo);
					showOffer.setSeqNo(_seqNo);
					showOffer.setHallId(_hallId);

					if (map0 != null) {
						showOffer.setHallName(map0.get(showOffer.getHallId()));
					} else {
						showOffer.setHallName(showOffer.getHallId());
					}
					showOffer.setOfferCinemaId(_cinemaId);
					showOffer.setCinemaLinkId(obj.getLinkId());
					String time = showOffer.getTime();
					showOffer.setTime(StringHelper.get(time, 4));
					if (StringHelper.timeCompare(showOffer.getDate(), showOffer.getTime())) {
						showOffer.setTimeoutFlag(1);
					} else {
						showOffer.setTimeoutFlag(0);
					}
					showOffer.setPriceLowest(_priceLowest);

					Element sectionEl = (Element) ii.next();
					showOffer.setSectionId(sectionEl.attributeValue("id"));
					showOffer.setPriceStandard(sectionEl.attributeValue("standard"));

					showList.add(showOffer);
				}
			}
		}
		return showList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SeatOffer> getSeats(RequestObj obj) {

		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		paramsMap.clear();
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());
		paramsMap.put("hallId", obj.getHallId());

		Map<String, Object> map = this.analyst("qrySeat", paramsMap);
		if (check(map)) {
			Document document = (Document) map.get("DOCUMENT");
			Element seatplansEl = document.getRootElement();
			for (Iterator i = seatplansEl.elementIterator(); i.hasNext();) {

				Element effectiveEl = (Element) i.next();
				List sectionNodes = effectiveEl.elements("section");
				for (Iterator itt = sectionNodes.iterator(); itt.hasNext();) {
					Element sectionEl = (Element) itt.next();
					List seatNodes = sectionEl.elements("seat");

					for (Iterator ittt = seatNodes.iterator(); ittt.hasNext();) {
						Element seatEl = (Element) ittt.next();

						SeatOffer seatOffer = new SeatOffer(seatEl);
						seatOffer.setSectionId(sectionEl.attributeValue("id"));
						seatOffer.setCinemaId(seatplansEl.attributeValue("cinemaId"));
						seatOffer.setHallId(seatplansEl.attributeValue("hallId"));
						seatOffer.setEffectiveDate(effectiveEl.attributeValue("date"));

						seatplanList.add(seatOffer);
					}
				}
			}
		}
		return seatplanList;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(1);
	}
}
