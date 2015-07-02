package com.maizuo.seat.service.offer.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.maizuo.common.jdbc.JdbcImpl;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.entity.Halls;
import com.maizuo.seat.entity.Order;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.helper.FirePhenixHelper;
import com.maizuo.seat.helper.RegetPaiQiThread;
import com.maizuo.seat.helper.StringHelper;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.RequestLockObj;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.request.RequestObj;
import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;
import com.maizuo.seat.service.offer.request.RequestUnlockObj;
import com.maizuo.seat.service.offer.request.RequestUsedSeatObj;
import com.maizuo.seat.service.offer.request.RequestVerifyOrderObj;
import com.maizuo.seat.util.DocumentUtil;
import com.maizuo.seat.util.IDGenerator;
import com.maizuo.seat.util.MD5;
import com.maizuo.seat.util.SimpleObject;
import com.maizuo.seat.util.UrlRequestUtils;

/**
 * 火凤凰
 * 
 * @author Administrator
 * 
 */
public class FirePhenixServiceImpl extends Common implements OfferService {

	@Autowired
	private JdbcImpl jdbcImpl;
	private final String className = getClass().getSimpleName();
	private Logger logger = Logger.getLogger(FirePhenixServiceImpl.class);

	/**
	 * 請求遠程
	 * 
	 * @param method
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	private Element analyst(String method, LinkedHashMap<String, Object> params) throws ServiceException {

		String documentStr = "";
		Document document = null;
		SAXReader saxReader = new SAXReader();

		String urlStr = offerInfo.getUrl();

		LinkedHashMap<String, Object> linkMap = new LinkedHashMap<String, Object>();
		linkMap.put("account", offerInfo.getAccount());
		linkMap.put("password", offerInfo.getPassword());

		if (params != null && params.size() > 0) {
			linkMap.putAll(params);
		}
		try {
			documentStr = UrlRequestUtils.xfireCall(urlStr, method, linkMap);
			if (StringUtils.isEmpty(documentStr)) {
				throw new ServiceException(className, response_null_error, "");
			}
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("gb2312")));
		} catch (Exception e) {
			throw new ServiceException(className, request_fail, "请求失败,超时");
		}
		return document.getRootElement();
	}

	@SuppressWarnings("rawtypes")
	public List<CinemaOffer> getCinemas() throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();
		long start1 = System.currentTimeMillis();
		Element cinemasEl = this.analyst("qryCinema", null);
		long start2 = System.currentTimeMillis();
		logger.info(start2 - start1);
		Iterator iterator = cinemasEl.elementIterator();

		StringBuffer hallIds = new StringBuffer();
		StringBuffer hallNames = new StringBuffer();
		StringBuffer seatCounts = new StringBuffer();
		StringBuffer vipFlags = new StringBuffer();
		CinemaOffer cinema = null;

		while (iterator.hasNext()) {
			Element cinemaEl = (Element) iterator.next();

			cinema = new CinemaOffer(cinemaEl);
			hallIds.setLength(0);
			hallNames.setLength(0);
			seatCounts.setLength(0);
			vipFlags.setLength(0);

			List hallNodes = cinemaEl.elements("hall");
			Iterator it = hallNodes.iterator();
			while (it.hasNext()) {
				Element hel = (Element) it.next();
				hallIds.append(hel.attributeValue("id"));
				hallNames.append(hel.attributeValue("name"));
				seatCounts.append(hel.attributeValue("seatcount"));
				vipFlags.append((hel.attributeValue("vipflag").equals("Y") ? 1 : 0));
				if (it.hasNext()) {
					hallIds.append("-");
					hallNames.append("-");
					seatCounts.append("-");
					vipFlags.append("-");
				}
			}
			cinema.setHalls(hallIds.toString());
			cinema.setHallNames(hallNames.toString());
			cinema.setSeatCounts(seatCounts.toString());
			cinema.setVipFlags(vipFlags.toString());
			cinemaList.add(cinema);
		}

		/*
		 * List<Cinema> list = new ArrayList<>(); List<Halls> list1 = new
		 * ArrayList<>();
		 * 
		 * for (CinemaOffer cinemaOffer : cinemaList) { Cinema cinema1 = new
		 * Cinema(); cinema1.setId(IDGenerator.getID()); cinema1.setOfferId(1);
		 * String[] ids = cinemaOffer.getHalls().split("-"); String[] names =
		 * cinemaOffer.getHallNames().split("-"); String[] counts =
		 * cinemaOffer.getSeatCounts().split("-"); String[] vips =
		 * cinemaOffer.getVipFlags().split("-"); for (int j = 0; j < ids.length;
		 * j++) { Halls halls = new Halls(); halls.setCinameId(cinema1.getId());
		 * halls.setName(names[j]);
		 * halls.setSeatNum(Integer.valueOf(counts[j]));
		 * halls.setVipFlag(Integer.valueOf(vips[j]));
		 * halls.setCode(Integer.valueOf(ids[j])); list1.add(halls); }
		 * cinema1.setCinemaId(cinemaOffer.getOfferCinemaId());
		 * cinema1.setCinemaCity(cinemaOffer.getCityName());
		 * cinema1.setCinemaLinkId(cinemaOffer.getLinkId());
		 * cinema1.setCinemaName(cinemaOffer.getCinemaName());
		 * cinema1.setMzCinemaId(mzCinemaId.getAndIncrement());
		 * 
		 * list.add(cinema1); } jdbcImpl.insert(list1); jdbcImpl.insert(list);
		 */
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return cinemaList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<FilmOffer> getFilms(RequestObj obj) throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());
		paramsMap.put("showDate", obj.getShowDate());

		Element filmsEl = this.analyst("qryFilm", paramsMap);
		Iterator iterator = filmsEl.elementIterator();
		String _cinemaId = filmsEl.attributeValue("cinemaId");

		List<FilmOffer> filmList = new ArrayList<FilmOffer>();
		while (iterator.hasNext()) {
			Element filmEl = (Element) iterator.next();

			FilmOffer filmOffer = new FilmOffer(filmEl);
			filmOffer.setCinemaId(_cinemaId);

			filmList.add(filmOffer);
		}
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return filmList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ShowOffer> getForetell(RequestObj obj) throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("updateLevel", "0");
		paramsMap.put("showDate", obj.getShowDate());
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());

		Element showsEl = this.analyst("qryShow", paramsMap);

		Element resultEl = showsEl.element("result");
		if (resultEl != null) {
			int _result = Integer.parseInt(resultEl.getTextTrim());
			Element msgsEl = showsEl.element("messages");
			Element msgEl = msgsEl.element("message");
			if (_result != 0) {
				throw new ServiceException(className, _error1, msgEl.getText());
			}
		}

		String _cinemaId = showsEl.attributeValue("cinemaId");
		Map<String, String> map0 = null;
		// Map<String, String> map0 = mm.getHallNames(cinemaId, true);
		List<ShowOffer> showList = new ArrayList<ShowOffer>();
		for (Iterator i = showsEl.elementIterator(); i.hasNext();) {
			Element showEl = (Element) i.next();
			Element filmEl = showEl.element("film");

			if (filmEl == null) {
				continue;
			}
			Element priceEl = showEl.element("price");
			Element throughEl = showEl.element("through");
			String _throughFlag = throughEl.attributeValue("flag");
			String _priceLowest = priceEl.attributeValue("lowest");

			for (Iterator ii = priceEl.elementIterator(); ii.hasNext();) {
				ShowOffer showOffer = new ShowOffer(filmEl, showEl);
				showOffer.setThroughFlag(_throughFlag);
				showOffer.setFilmId(0);
				showOffer.setOfferCinemaId(_cinemaId);
				showOffer.setCinemaLinkId(obj.getLinkId());
				showOffer.setPriceLowest(_priceLowest);

				String hallName = map0 == null ? showOffer.getHallId() : map0.get(showOffer.getHallId());
				showOffer.setHallName(hallName);

				String time = showOffer.getTime();
				showOffer.setTime(StringHelper.get(time, 4));

				int timeoutFlag = StringHelper.timeCompare(showOffer.getDate(), showOffer.getTime()) ? 1 : 0;
				showOffer.setTimeoutFlag(timeoutFlag);
				
				Element sectionEl = (Element) ii.next();
				showOffer.setSectionId(sectionEl.attributeValue("id"));
				showOffer.setPriceStandard(sectionEl.attributeValue("standard"));

				showList.add(showOffer);
			}
		}

		List<Foretell> list = new ArrayList<Foretell>();
		for (ShowOffer show : showList) {
			Foretell foretell = new Foretell();
			foretell.setId(IDGenerator.getID());
			foretell.setHallId(show.getHallId());
			foretell.setOfferMovieId(show.getOfferFilmId());
			foretell.setOfferCinemaId(show.getOfferCinemaId());
			// foretell.setSectionId(show.getSectionId());
			foretell.setShowDate(show.getDate());
			foretell.setShowTime(show.getTime());
			foretell.setSeqNo(show.getSeqNo());
			foretell.setShowSeqNo(show.getShowSeqNo());
			list.add(foretell);
		}
		jdbcImpl.insert(list);
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return showList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SeatOffer> getSeats(RequestObj obj) throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		paramsMap.put("cinemaId", obj.getOfferCinemaId());
		paramsMap.put("cinemaLinkId", obj.getLinkId());
		paramsMap.put("hallId", obj.getHallId());

		Element seatplansEl = this.analyst("qrySeat", paramsMap);
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
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return seatplanList;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(1);
	}

	/**
	 * 按影院场次获取该场次座位状态信息。
	 * 
	 * @param cinemaId
	 * @param cinemaLinkID
	 * @param hallId
	 * @param sectionId
	 * @param filmId
	 * @param showSeqNo
	 * @param showDate
	 * @param showTime
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getUsedSeats(String cinemaId, String cinemaLinkID, String hallId, String sectionId, String filmId, String showSeqNo, String showDate, String showTime)
			throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("cinemaId", cinemaId);
		paramsMap.put("cinemaLinkID", cinemaLinkID);
		paramsMap.put("hallId", hallId);
		paramsMap.put("sectionId", sectionId);
		paramsMap.put("filmId", filmId);
		paramsMap.put("showSeqNo", showSeqNo);
		paramsMap.put("showDate", showDate);
		paramsMap.put("showTime", showTime);

		Element showSeatsEl = this.analyst("qryTicket", paramsMap);

		Element resultEl = showSeatsEl.element("result");
		if (resultEl != null) {
			Element msgsEl = showSeatsEl.element("messages");
			Element msgEl = msgsEl.element("message");
			int errorCode = Integer.parseInt(resultEl.getTextTrim());
			throw new ServiceException(className, errorCode, msgEl.getTextTrim());
		}

		String _cinemaId = showSeatsEl.attributeValue("cinemaId");
		String _hallId = showSeatsEl.attributeValue("hallId");

		List<SeatOffer> ticketList = new ArrayList<SeatOffer>();
		SeatOffer ticket = null;
		for (Iterator it = showSeatsEl.elementIterator(); it.hasNext();) {
			Element sectionEl = (Element) it.next();
			String _sectionId = sectionEl.attributeValue("id");
			for (Iterator ii = sectionEl.elementIterator(); ii.hasNext();) {
				Element seatEl = (Element) ii.next();
				ticket = new SeatOffer();
				ticket.setColumnId(seatEl.attributeValue("columnId"));
				ticket.setRowId(seatEl.attributeValue("rowId"));
				ticket.setSectionId(_sectionId);
				ticket.setHallId(_hallId);
				ticket.setCinemaId(_cinemaId);
				ticketList.add(ticket);
			}
		}
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return ticketList;
	}

	@Override
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) throws ServiceException {
		long mer1 = Runtime.getRuntime().totalMemory();
		// 封装请求参数
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("cinemaId", obj.getCinemaId());
		paramsMap.put("cinemaLinkID", obj.getCinemaLinkID());
		paramsMap.put("hallId", obj.getHallId());
		paramsMap.put("sectionId", obj.getSectionId());
		paramsMap.put("filmId", obj.getFilmId());
		paramsMap.put("showSeqNo", obj.getShowSeqNo());
		paramsMap.put("showDate", obj.getShowDate());
		paramsMap.put("showTime", obj.getShowTime());
		// 请求远程
		Element showSeatsEl = this.analyst("qryTicket", paramsMap);
		Element resultEl = showSeatsEl.element("result");
		if (resultEl != null) {
			Element msgsEl = showSeatsEl.element("messages");
			Element msgEl = msgsEl.element("message");
			int errorCode = Integer.parseInt(resultEl.getTextTrim());
			throw new ServiceException(className, errorCode, msgEl.getTextTrim());
		}

		String _cinemaId = showSeatsEl.attributeValue("cinemaId");
		String _hallId = showSeatsEl.attributeValue("hallId");

		List<SeatOffer> ticketList = new ArrayList<SeatOffer>();
		SeatOffer ticket = null;
		for (Iterator i = showSeatsEl.elementIterator(); i.hasNext();) {
			Element sectionEl = (Element) i.next();
			String _sectionId = sectionEl.attributeValue("id");
			for (Iterator ii = sectionEl.elementIterator(); ii.hasNext();) {
				Element seatEl = (Element) ii.next();
				ticket = new SeatOffer();
				ticket.setColumnId(seatEl.attributeValue("columnId"));
				ticket.setRowId(seatEl.attributeValue("rowId"));
				ticket.setSectionId(_sectionId);
				ticket.setHallId(_hallId);
				ticket.setCinemaId(_cinemaId);
				ticketList.add(ticket);
			}
		}
		long mer2 = Runtime.getRuntime().totalMemory();
		logger.info("内存数量为" + (mer2 - mer1));
		return ticketList;
	}

	/**
	 * 向火凤凰以订单形式提交座位锁定请求，锁定成功的进入支付流程。
	 * 
	 * @param orderNo
	 * @param ticketCount
	 * @param cinemaId
	 * @param cinemaLinkId
	 * @param hallId
	 * @param sectionId
	 * @param filmId
	 * @param showSeqNo
	 * @param showDate
	 * @param showTime
	 * @param seatId
	 * @param randKey
	 * @return
	 * @throws ServiceException
	 */
	public SimpleObject lockSeat(String orderNo, String ticketCount, String cinemaId, String cinemaLinkId, String hallId, String sectionId, String filmId, String showSeqNo,
			String showDate, String showTime, String seatId, String randKey, int mzCinemaId) throws ServiceException {
		// 检核订座数量
		if (NumberUtils.toInt(ticketCount) < 1) {
			throw new ServiceException(className, ticket_count, "ads");
		}
		// 封装请求参数
		StringBuffer _checkBuffer = new StringBuffer();
		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(orderNo)
				.append("&ticketCount=").append(ticketCount).append("&cinemaId=").append(cinemaId).append("&cinemaLinkId=").append(cinemaLinkId).append("&hallId=").append(hallId)
				.append("&sectionId=").append(sectionId).append("&filmId=").append(filmId).append("&showSeqNo=").append(showSeqNo).append("&showDate=").append(showDate)
				.append("&showTime=").append(showTime).append("&seatId=").append(seatId).append("&randKey=").append(randKey);
		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();
		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderNo", orderNo);
		paramsMap.put("ticketCount", ticketCount);
		paramsMap.put("checkValue", _checkValue);
		paramsMap.put("cinemaId", cinemaId);
		paramsMap.put("cinemaLinkId", cinemaLinkId);
		paramsMap.put("hallId", hallId);
		paramsMap.put("sectionId", sectionId);
		paramsMap.put("filmId", filmId);
		paramsMap.put("showSeqNo", showSeqNo);
		paramsMap.put("showDate", showDate);
		paramsMap.put("showTime", showTime);
		paramsMap.put("seatId", seatId);
		paramsMap.put("randKey", randKey);
		// 请求远程
		Element seatLockEl = this.analyst("lockSeat", paramsMap);

		Element resultEl = seatLockEl.element("result");
		Element msgsEl = seatLockEl.element("messages");
		Element msgEl = msgsEl.element("message");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (0 != _result) {
			switch (_result) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 10:
			case 11:
			case 12:
			case 13:
			default:
				_result = 15;
				new RegetPaiQiThread("http://bj.poopi.cn/getPaiQiNew.htm?cinemaId=" + mzCinemaId + "&showDate=" + showDate).start();
				break;
			}
			throw new ServiceException(className, _result + 1000, msgEl.getText());
		}
		String _flag = null;
		SimpleObject so = new SimpleObject();
		so.setKV(_result + "", _flag);
		return so;
	}

	public void lockSeat(RequestLockObj obj) throws ServiceException {
		/**
		 * 检核订座数量
		 */
		if (NumberUtils.toInt(obj.getTicketCount()) < 1) {
			throw new ServiceException(className, ticket_count, "ads");
		}
		StringBuffer _checkBuffer = new StringBuffer();
		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(obj.getOrderNo())
				.append("&ticketCount=").append(obj.getTicketCount()).append("&cinemaId=").append(obj.getCinemaId()).append("&cinemaLinkId=").append(obj.getCinemaLinkId())
				.append("&hallId=").append(obj.getHallId()).append("&sectionId=").append(obj.getSectionId()).append("&filmId=").append(obj.getFilmId()).append("&showSeqNo=")
				.append(obj.getShowSeqNo()).append("&showDate=").append(obj.getShowDate()).append("&showTime=").append(obj.getShowTime()).append("&seatId=")
				.append(obj.getSeatId()).append("&randKey=").append(obj.getRandKey());
		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();

		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderNo", obj.getOrderNo());
		paramsMap.put("ticketCount", obj.getTicketCount());
		paramsMap.put("checkValue", _checkValue);
		paramsMap.put("cinemaId", obj.getCinemaId());
		paramsMap.put("cinemaLinkId", obj.getCinemaLinkId());
		paramsMap.put("hallId", obj.getHallId());
		paramsMap.put("sectionId", obj.getSectionId());
		paramsMap.put("filmId", obj.getFilmId());
		paramsMap.put("showSeqNo", obj.getShowSeqNo());
		paramsMap.put("showDate", obj.getShowDate());
		paramsMap.put("showTime", obj.getShowTime());
		paramsMap.put("seatId", obj.getSeatId());
		paramsMap.put("randKey", obj.getRandKey());

		Element seatLockEl = this.analyst("lockSeat", paramsMap);
		Element resultEl = seatLockEl.element("result");
		Element msgsEl = seatLockEl.element("messages");
		Element msgEl = msgsEl.element("message");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (0 != _result) {

			throw new ServiceException(className, _result, msgEl.getText());
		}

	}

	/**
	 * 向火凤凰以订单形式提交座位释放请求。
	 * 
	 * @param orderNo
	 * @param ticketCount
	 * @param cinemaId
	 * @param cinemaLinkId
	 * @param randKey
	 * @return
	 * @throws ServiceException
	 */
	public SimpleObject unlockSeats(String orderId, String ticketCount, String cinemaId, String cinemaLinkId, String randKey) throws ServiceException {
		StringBuffer _checkBuffer = new StringBuffer();

		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(orderId)
				.append("&ticketCount=").append(ticketCount).append("&cinemaId=").append(cinemaId).append("&cinemaLinkId=").append(cinemaLinkId).append("&randKey=")
				.append(randKey);

		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();

		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		paramsMap.put("ticketCount", ticketCount);
		paramsMap.put("checkValue", _checkValue);
		paramsMap.put("cinemaId", cinemaId);
		paramsMap.put("cinemaLinkId", cinemaLinkId);
		paramsMap.put("randKey", randKey);

		Element seatReleaseEl = this.analyst("unlockSeat", paramsMap);
		Element resultEl = seatReleaseEl.element("result");
		Element msgsEl = seatReleaseEl.element("messages");
		Element msgEl = msgsEl.element("message");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (0 != _result) {
			switch (_result) {
			case 14:
				break;
			case 8:
				break;
			default:
				_result = 15;
				break;
			}
			throw new ServiceException(className, _result, msgEl.getText());
		}
		String _flag = null;
		SimpleObject so = new SimpleObject();
		so.setKV(_result + "", _flag);
		return so;
	}

	/**
	 * 向火凤凰提交订单确认请求。
	 * 
	 * @param orderNo订单编号
	 * @param ticketCount购票数量
	 * @param cinemaId影院编号
	 * @param cinemaLinkId影院连接编码
	 * @param hallId影厅编号
	 * @param filmId影片编码
	 * @param showSeqNo场次内部编号
	 * @param showDate场次日期
	 * @param showTime场次时间
	 * @param priceList影票价格列表
	 * @param feeList影票手续费列表
	 * @param randKey随机串号
	 * @param payment支付方式
	 * @param paymentNo支付号码
	 * @return
	 * @throws ServiceException
	 */
	private Order verifyOrder(String orderId, String ticketCount, String cinemaId, String cinemaLinkId, String hallId, String filmId, String showSeqNo, String showDate,
			String showTime, String prices, String fees, String randKey, String payment, String paymentNo) throws ServiceException {
		prices = FirePhenixHelper.che(prices);
		fees = FirePhenixHelper.che(fees);

		StringBuffer _checkBuffer = new StringBuffer();
		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(orderId)
				.append("&ticketCount=").append(ticketCount).append("&cinemaId=").append(cinemaId).append("&cinemaLinkId=").append(cinemaLinkId).append("&hallId=").append(hallId)
				.append("&filmId=").append(filmId).append("&showSeqNo=").append(showSeqNo).append("&showDate=").append(showDate).append("&showTime=").append(showTime)
				.append("&priceList=").append(prices).append("&feeList=").append(fees).append("&randKey=").append(randKey).append("&payment=").append(payment)
				.append("&paymentNo=").append(paymentNo);

		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();

		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderId", orderId);
		paramsMap.put("ticketCount", ticketCount);
		paramsMap.put("checkValue", _checkValue);
		paramsMap.put("cinemaId", cinemaId);
		paramsMap.put("cinemaLinkId", cinemaLinkId);
		paramsMap.put("hallId", hallId);
		paramsMap.put("filmId", filmId);
		paramsMap.put("showSeqNo", showSeqNo);
		paramsMap.put("showDate", showDate);
		paramsMap.put("showTime", showTime);
		paramsMap.put("prices", prices);
		paramsMap.put("fees", fees);
		paramsMap.put("randKey", randKey);
		paramsMap.put("payment", payment);
		paramsMap.put("paymentNo", paymentNo);

		Element bookingConfirmEl = this.analyst("fixOrder", paramsMap);
		if (bookingConfirmEl.asXML().equalsIgnoreCase("Remote Connection error")) {
			Order o = this.queryOrder(orderId, Integer.parseInt(ticketCount), cinemaId, cinemaLinkId, randKey, "");
			if (o.getStatus() != 0) {
				throw new ServiceException(className, response_null_error, "");
			}
			return o;
		}

		// 结果，0表示成功，非0失败，非0数字为错误代码。
		Element resultEl = bookingConfirmEl.element("result");
		// 错误描述
		Element msgsEl = bookingConfirmEl.element("messages");
		Element msgEl = msgsEl.element("message");
		// 取票号
		Element cidEl = bookingConfirmEl.element("confirmationId");
		// 订单号
		Element bidEl = bookingConfirmEl.element("bookingId");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (0 != _result) {
			switch (_result) {
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 10:
				if (_result == 5 && msgEl.getText().indexOf("PVE") >= 0) {
					DocumentUtil du = new DocumentUtil();
				}
				break;
			default:
				_result = -5;
				break;
			}
			throw new ServiceException(className, _result + 1020, msgEl.getText());
		}

		if (Integer.parseInt(cidEl.getText()) <= 0) {
			throw new ServiceException(className, 1031, "");
		}
		Order order = new Order();
		order.setConfirmationId(cidEl.getText());
		order.setBookId(bidEl.getText());
		return order;
	}

	public Order queryOrder(String orderNo, int ticketCount, String cinemaId, String cinemaLinkId, String randKey, String offerOrderId) throws ServiceException {

		if (!StringUtils.isEmpty(offerOrderId)) {
			orderNo = offerOrderId;
		}
		StringBuffer _checkBuffer = new StringBuffer();
		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(orderNo)
				.append("&ticketCount=").append(ticketCount).append("&cinemaId=").append(cinemaId).append("&cinemaLinkId=").append(cinemaLinkId).append("&randKey=")
				.append(randKey);

		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();

		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderNo", orderNo);
		paramsMap.put("ticketCount", ticketCount);
		paramsMap.put("sign", _checkValue);
		paramsMap.put("cinemaId", cinemaId);
		paramsMap.put("cinemaLinkId", cinemaLinkId);
		paramsMap.put("randKey", randKey);

		Element bookingQuerymEl = this.analyst("qryOrder", paramsMap);
		// 0表示成功，非0失败，非0数字为错误代码。
		Element resultEl = bookingQuerymEl.element("result");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (_result != 0) {
			throw new ServiceException(className, 1218, bookingQuerymEl.element("messages").getTextTrim());
		}
		// 取票号
		Element confirmationEl = bookingQuerymEl.element("confirmationId");
		if (Integer.parseInt(confirmationEl.getText()) <= 0) {
			throw new ServiceException(className, 1031, "");
		}
		// 订单号
		Element bidEl = bookingQuerymEl.element("bookingId");

		Order order = new Order();
		// 订单状态标记,0表示等待支付确认；1表示交易成功；2表示交易超时，座位已释放。
		String statusId = bookingQuerymEl.elementText("statusInd");
		order.setStatus(Integer.parseInt(statusId));
		order.setBookId(bidEl.getText());
		order.setConfirmationId(confirmationEl.getText());
		// 座位列表
		Element seats = bookingQuerymEl.element("seats");
		List<Element> list = seats.elements("seat");
		int exchange = 0;
		for (Element e : list) {
			String str = e.attributeValue("printedFlg");
			if ("Y".equalsIgnoreCase(str)) {
				exchange++;
			}
		}
		order.setExchangeNum(exchange);
		FirePhenixHelper.setOrderMsgByOrderStatus(order);
		return order;

	}

	private static final String TEST_COFIRM_ID = "12345678";
	private static final String TEST_BOOK_ID = "1235465";
	private static final String TEST_BACK_MSG = "交易成功";

	public Order verifyOrder(String orderId, String ticketCount, String cinemaId, String cinemaLinkId, String hallId, String filmId, String showSeqNo, String showDate,
			String showTime, String prices, String fees, String randKey, String payment, String paymentNo, boolean isTest) throws ServiceException {
		if (!isTest) {
			return this.verifyOrder(orderId, ticketCount, cinemaId, cinemaLinkId, hallId, filmId, showSeqNo, showDate, showTime, prices, fees, randKey, payment, paymentNo);
		}
		Order order = new Order();
		order.setStatus(0);
		order.setConfirmationId(TEST_COFIRM_ID);
		order.setMessage(TEST_BACK_MSG);
		order.setBookId(TEST_BOOK_ID);
		return order;

	}

	public Order queryOrder(String orderNo, int ticketCount, String cinemaId, String cinemaLinkId, String randKey, String offerOrderId, boolean isTest) throws ServiceException {
		if (!isTest) {
			return this.queryOrder(orderNo, ticketCount, cinemaId, cinemaLinkId, randKey, offerOrderId);
		}
		Order order = new Order();
		order.setStatus(0);
		order.setConfirmationId(TEST_COFIRM_ID);
		order.setMessage(TEST_BACK_MSG);
		order.setBookId(TEST_BOOK_ID);
		return order;

	}

	@Override
	public void unlockSeats(RequestUnlockObj obj) throws ServiceException {

		StringBuffer _checkBuffer = new StringBuffer();

		_checkBuffer.append("userId=").append(offerInfo.getAccount()).append("&userPass=").append(offerInfo.getPassword()).append("&orderNo=").append(obj.getOrderId())
				.append("&ticketCount=").append(obj.getTicketCount()).append("&cinemaId=").append(obj.getCinemaId()).append("&cinemaLinkId=").append(obj.getCinemaLinkId())
				.append("&randKey=").append(obj.getRandKey());

		String _checkValue = MD5.getVal(_checkBuffer.toString()).toUpperCase();

		LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("orderId", obj.getOrderId());
		paramsMap.put("ticketCount", obj.getTicketCount());
		paramsMap.put("checkValue", _checkValue);
		paramsMap.put("cinemaId", obj.getCinemaId());
		paramsMap.put("cinemaLinkId", obj.getCinemaLinkId());
		paramsMap.put("randKey", obj.getRandKey());

		Element seatReleaseEl = this.analyst("unlockSeat", paramsMap);
		Element resultEl = seatReleaseEl.element("result");
		Element msgsEl = seatReleaseEl.element("messages");
		Element msgEl = msgsEl.element("message");
		int _result = Integer.parseInt(resultEl.getTextTrim());
		if (0 != _result) {
			switch (_result) {
			case 14:
				break;
			case 8:
				break;
			default:
				_result = 15;
				break;
			}
			throw new ServiceException(className, _result + 1000, msgEl.getText());
		}

	}

	@SuppressWarnings("unused")
	@Override
	public Order queryOrder(RequestQueryOrderObj obj) throws ServiceException {
		if (false) {
			return this.queryOrder(obj.getOrderNo(), obj.getTicketCount(), obj.getCinemaId(), obj.getCinemaLinkId(), obj.getRandKey(), null);
		}
		Order order = new Order();
		order.setStatus(0);
		order.setConfirmationId(TEST_COFIRM_ID);
		order.setMessage(TEST_BACK_MSG);
		order.setBookId(TEST_BOOK_ID);
		return order;
	}

	@SuppressWarnings("unused")
	@Override
	public Order verifyOrder(RequestVerifyOrderObj obj) throws ServiceException {
		if (false) {
			// return this.verifyOrder();
		}
		Order order = new Order();
		order.setStatus(0);
		order.setConfirmationId(TEST_COFIRM_ID);
		order.setMessage(TEST_BACK_MSG);
		order.setBookId(TEST_BOOK_ID);
		return order;
	}

}
