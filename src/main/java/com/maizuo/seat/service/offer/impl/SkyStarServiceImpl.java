package com.maizuo.seat.service.offer.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.maizuo.common.jdbc.JdbcImpl;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.entity.Order;
import com.maizuo.seat.entity.Seat;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.helper.SkyStarHelper;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.RequestLockObj;
import com.maizuo.seat.service.SeatService;
import com.maizuo.seat.service.offer.OfferInfo;
import com.maizuo.seat.service.offer.OfferService;
import com.maizuo.seat.service.offer.request.RequestObj;
import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;
import com.maizuo.seat.service.offer.request.RequestUnlockObj;
import com.maizuo.seat.service.offer.request.RequestUsedSeatObj;
import com.maizuo.seat.service.offer.request.RequestVerifyOrderObj;
import com.maizuo.seat.util.IDGenerator;
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
	// 类名
	private final String calssName = getClass().getSimpleName();
	@Autowired
	private JdbcImpl jdbcImpl;
	@Autowired
	private SeatService seatService;

	private Logger logger = Logger.getLogger(SkyStarServiceImpl.class);
	private Map<String, Object> token = null;

	private String getMD5Params(LinkedHashMap<String, Object> ps) {
		return getMD5Params(ps, null);
	}

	private String getMD5Params(LinkedHashMap<String, Object> ps, Map<String, Object> token) {
		StringBuffer str = new StringBuffer(offerInfo.getAccount());
		for (Entry<String, Object> e : ps.entrySet()) {
			if (!e.getKey().equals("pAppCode") && !e.getKey().equals("pTokenID")) {
				str.append(e.getValue());
			}
		}
		if (token != null) {
			str.append(token.get(TOKEN_ID));
			str.append(token.get(TOKEN));
		}
		str.append(offerInfo.getPassword());
		return MD5.getHEXVal(str.toString().toLowerCase()).toLowerCase();
	}

	public Element analyst(String method, LinkedHashMap<String, Object> params) throws ServiceException {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		String documentStr = UrlRequestUtils.xfireCall(offerInfo.getUrl(), method, params);
		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			throw new ServiceException(calssName, response_null_error, documentStr);
		}

		if (document == null) {
			throw new ServiceException(calssName, response_null_error, documentStr);
		}

		Element rootE = document.getRootElement();
		int errorCode = Integer.parseInt(rootE.elementText("ResultCode"));
		if (errorCode != 0) {
			throw new ServiceException(calssName, errorCode, documentStr);
		}
		return rootE;

	}

	public synchronized Element analyst1(String method, LinkedHashMap<String, Object> params) throws ServiceException {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		String documentStr = UrlRequestUtils.b(offerInfo.getUrl(), method, params);
		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("UTF8")));
		} catch (Exception e1) {
			throw new ServiceException(calssName, response_null_error, documentStr);
		}

		if (document == null) {
			throw new ServiceException(calssName, response_null_error, documentStr);
		}

		Element rootE = document.getRootElement();
		int errorCode = Integer.parseInt(rootE.elementText("ResultCode"));
		if (errorCode != 0) {
			throw new ServiceException(calssName, errorCode, documentStr);
		}
		return rootE;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CinemaOffer> getCinemas() throws ServiceException {
		final List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();
		long start1 = System.currentTimeMillis();
		final LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);
		Element rootE = analyst("GetCinema", ps);

		Element cinemasE = rootE.element("Cinemas");
		final Iterator iterator = cinemasE.elementIterator();
		long start2 = System.currentTimeMillis();
		logger.info(start2 - start1 + "getCinemas");
		long start5 = System.currentTimeMillis();
		int i = 0, j = 0;
		final StringBuffer hallIds = new StringBuffer();
		final StringBuffer hallNames = new StringBuffer();
		System.out.println(cinemasE.nodeCount());
		//final CountDownLatch doneSignal = new CountDownLatch(cinemasE.nodeCount());
//		ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//		final ReentrantLock lock = new ReentrantLock();
//		 int ii = 1;
		while (iterator.hasNext()) {
			Element cinemaEl = (Element) iterator.next();

			final CinemaOffer cinema = new CinemaOffer();
			cinema.setOfferCinemaId(cinemaEl.element("PlaceNo").getText());
			cinema.setCinemaName(cinemaEl.element("PlaceName").getText());
			cinema.setOfferCityId(cinemaEl.element("CityNo").getText());

			ps.clear();
			ps.put("pAppCode", offerInfo.getAccount());
			ps.put("pCinemaID", cinema.getOfferCinemaId());
			ps.put("pTokenID", token.get(TOKEN_ID));
			verifyInfo = getMD5Params(ps, token);
			ps.put("pVerifyInfo", verifyInfo);
//			Runnable runnable = new Runnable() {
//
//				@Override
//				public void run() {
					long start3 = System.currentTimeMillis();
					Element srE = null;
					try {
						srE = analyst1("GetHall", ps);

					} catch (Exception e2) {
						logger.error(Thread.currentThread().getName());
						//doneSignal.countDown();
						throw e2;
					}
					long start4 = System.currentTimeMillis();
					logger.info(start4 - start3);
					// i += start4 - start3;
					// j++;
					// logger.info(start4 - start3 + "gethalls" +
					// cinema.getOfferCinemaId());
					// lock.lock();
					try {
						hallIds.setLength(0);
						hallNames.setLength(0);
						Element hallsE = srE.element("Halls");
						Iterator hi = hallsE.elementIterator();
						while (hi.hasNext()) {
							Element hall = (Element) hi.next();
							hallIds.append(hall.elementText("HallNo"));
							hallNames.append(hall.elementText("HallName"));
							if (hi.hasNext()) {
								hallIds.append("-");
								hallNames.append("-");
							}
						}
						cinema.setHalls(hallIds.toString());
						cinema.setHallNames(hallNames.toString());

					} finally {
						// lock.unlock();
					}

					cinemaList.add(cinema);
//					doneSignal.countDown();
//				}
//			};
//			e.execute(runnable);

		}
		// e.shutdown();
		// try {
		// doneSignal.await();
		// } catch (InterruptedException e1) {
		// logger.info("sssssssssssssssssssssss");
		// }

		long start6 = System.currentTimeMillis();
		logger.info(start6 - start5 + "gethalls" + "all" + i + "size" + j);
		/*
		 * List<Cinema> list = new ArrayList<>(); List<Halls> list1 = new
		 * ArrayList<>(); for (CinemaOffer cinemaOffer : cinemaList) { Cinema
		 * cinema1 = new Cinema(); cinema1.setId(IDGenerator.getID());
		 * cinema1.setOfferId(2); long sta = System.currentTimeMillis();
		 * String[] ids = cinemaOffer.getHalls().split("-"); String[] names =
		 * cinemaOffer.getHallNames().split("-"); for (int j = 0; j <
		 * ids.length; j++) { Halls halls = new Halls();
		 * halls.setCinameId(cinema1.getId()); halls.setName(names[j]);
		 * halls.setCode(Integer.valueOf(ids[j])); list1.add(halls); } long sta1
		 * = System.currentTimeMillis(); System.out.println(sta1 - sta);
		 * cinema1.setCinemaId(cinemaOffer.getOfferCinemaId());
		 * cinema1.setCinemaCity(cinemaOffer.getCityName());
		 * cinema1.setCinemaLinkId(cinemaOffer.getLinkId());
		 * cinema1.setCinemaName(cinemaOffer.getCinemaName());
		 * cinema1.setMzCinemaId(mzCinemaId.getAndIncrement());
		 * 
		 * list.add(cinema1); } jdbcImpl.insert(list1); jdbcImpl.insert(list);
		 */

		return cinemaList;
	}

	public Map<String, Object> getToken() throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		String verifyInfo = getMD5Params(ps);
		ps.put("pVerifyInfo", verifyInfo);
		Element rootE = analyst("GetToken", ps);
		map.put(TOKEN_ID, rootE.element("TokenID").getText());
		map.put(TOKEN, rootE.element("Token").getText());
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<FilmOffer> getFilms(RequestObj obj) throws ServiceException {

		List<FilmOffer> filmList = new ArrayList<FilmOffer>();
		Map<String, FilmOffer> map = new HashMap<String, FilmOffer>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pPlanDate", obj.getShowDate());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetCinemaPlan", ps);
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

		filmList.addAll(map.values());
		return filmList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ShowOffer> getForetell(RequestObj obj) throws ServiceException {

		List<ShowOffer> showList = new ArrayList<ShowOffer>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pPlanDate", obj.getShowDate());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetCinemaPlan", ps);
		Element filmsEl = rootE.element("CinemaPlans");
		Iterator iterator = filmsEl.elementIterator();
		while (iterator.hasNext()) {
			Element filmEl = (Element) iterator.next();

			int useSign = Integer.parseInt(filmEl.elementText("UseSign"));
			int set = Integer.parseInt(filmEl.elementText("SetClose"));
			if (useSign == 0 && set == 1) {
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
		List<Foretell> list = new ArrayList<Foretell>();
		for (ShowOffer show : showList) {
			Foretell foretell = new Foretell();
			foretell.setId(IDGenerator.getID());
			foretell.setHallId(show.getHallId());
			foretell.setOfferMovieId(show.getOfferFilmId());
			foretell.setOfferCinemaId(obj.getOfferCinemaId());
			// foretell.setSectionId(show.getSectionId());
			foretell.setShowDate(obj.getShowDate());
			foretell.setShowTime(show.getTime());
			foretell.setSeqNo(show.getSeqNo());
			foretell.setShowSeqNo(show.getShowSeqNo());
			list.add(foretell);
		}
		jdbcImpl.insert(list);
		return showList;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SeatOffer> getSeats(RequestObj obj) throws ServiceException {

		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		SeatOffer seat = null;

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pCinemaID", obj.getOfferCinemaId());
		ps.put("pHallID", obj.getHallId());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetHallSite", ps);
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
		List<Seat> list = new ArrayList<Seat>();
		for (SeatOffer seat1 : seatplanList) {
			Seat seat2 = new Seat();
			try {
				BeanUtils.copyProperties(seat2, seat1);
				seat2.setCinemaId(obj.getOfferCinemaId());
				seat2.setHallId(obj.getHallId());
				seat2.setOfferId(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(seat2);
		}
		jdbcImpl.insert(list);

		return seatplanList;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(2);
		try {
			token = this.getToken();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SeatOffer> getUsedSeats(RequestUsedSeatObj obj) {
		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		SeatOffer seat = null;

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		// 排期應用號
		ps.put("pFeatureAppNo", obj.getSeqNo());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetPlanSiteState", ps);
		Element seatsEl = rootE.element("PlanSiteStates");
		Iterator iterator = seatsEl.elementIterator();
		while (iterator.hasNext()) {
			Element seatEl = (Element) iterator.next();
			seat = new SeatOffer();
			seat.setOfferSeatId(seatEl.elementText("SeatNo"));
			seat.setSectionId(seatEl.elementText("SeatPieceNo"));
			seat.setRowId(seatEl.elementText("SeatRow"));
			seat.setColumnId(seatEl.elementText("SeatCol"));
			seat.setRowNum(seatEl.elementText("GraphRow"));
			seat.setColumnNum(seatEl.elementText("GraphCol"));
			// o未售非0不可售
			seat.setStatus(Integer.parseInt(seatEl.elementText("SeatState")));
			if (seat.getStatus() != 0) {
				seatplanList.add(seat);
			}
		}
		return seatplanList;
	}

	/**
	 * 获取对应排期的座位图的状态
	 * 
	 * @param foretellId排期id
	 * @return
	 * @throws ServiceException
	 */
	public List<SeatOffer> getUsedSeats(String foretellId) throws ServiceException {
		List<SeatOffer> seatplanList = new ArrayList<SeatOffer>();
		SeatOffer seat = null;

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pFeatureAppNo", foretellId);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetPlanSiteState", ps);
		Element seatsEl = rootE.element("PlanSiteStates");
		Iterator iterator = seatsEl.elementIterator();
		while (iterator.hasNext()) {
			Element seatEl = (Element) iterator.next();
			seat = new SeatOffer();
			seat.setOfferSeatId(seatEl.elementText("SeatNo"));
			seat.setSectionId(seatEl.elementText("SeatPieceNo"));
			seat.setRowId(seatEl.elementText("SeatRow"));
			seat.setColumnId(seatEl.elementText("SeatCol"));
			seat.setRowNum(seatEl.elementText("GraphRow"));
			seat.setColumnNum(seatEl.elementText("GraphCol"));
			seat.setStatus(Integer.parseInt(seatEl.elementText("SeatState")));
			// 0-未售 非0不可售
			if (seat.getStatus() != 0) {
				seatplanList.add(seat);
			}
		}
		return seatplanList;
	}

	/**
	 * 
	 * @param offerForetellId
	 * @param orderId
	 * @param offerCinemaId
	 * @param hallId
	 * @param sectionId
	 * @param seatids
	 * @param orderPrice显示票价
	 * @param payType
	 * @param mobile
	 * @return
	 * @throws ServiceException
	 */
	public String lockSeat(String offerForetellId, String orderId, String offerCinemaId, String hallId, String sectionId, String seatids, double orderPrice, String payType,
			String mobile) throws ServiceException {
		String[] seats = seatids.split("\\|");

		List<String> seatIdList = null;
		// mm.getSeatIds(offerCinemaId, hallId, sectionId, seats);
		if (seatIdList == null || seatIdList.size() < 1) {
			throw new ServiceException(calssName, 101105, "数据库中影院对应提供商座位不合法...");
		}

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("FeatureAppNo", offerForetellId);
		ps.put("SerialNum", orderId);
		ps.put("Length", seats.length + "");
		ps.put("PayType", payType);
		ps.put("RecvMobilePhone", mobile);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String sign = getMD5Params(ps, token);

		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\"?><RealCheckSeatStateParameter><AppCode>");
		xmlStr.append(offerInfo.getAccount()).append("</AppCode>").append("<FeatureAppNo>").append(offerForetellId).append("</FeatureAppNo>").append("<SerialNum>").append(orderId)
				.append("</SerialNum><SeatInfos>");

		for (int i = 0; i < seatIdList.size(); i++) {
			xmlStr.append("<SeatInfo><SeatNo>").append(seatIdList.get(i)).append("</SeatNo><TicketPrice>").append(orderPrice)
					.append("</TicketPrice><Handlingfee>0</Handlingfee></SeatInfo>");
		}

		xmlStr.append("</SeatInfos><PayType>").append(payType).append("</PayType><RecvMobilePhone>").append(mobile).append("</RecvMobilePhone>").append("<TokenID>")
				.append(token.get(TOKEN_ID)).append("</TokenID><VerifyInfo>").append(sign).append("</VerifyInfo>").append("</RealCheckSeatStateParameter>");
		ps.clear();
		ps.put("pXmlString", xmlStr.toString());

		Element rootE = analyst("LiveRealCheckSeatState", ps);
		String skyStarOrderId = rootE.elementText("OrderNo");
		return skyStarOrderId;
	}

	public void unlockSeats(String offerOrderId) throws ServiceException {

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pOrderNO", offerOrderId);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("UnLockOrder", ps);
	}

	/**
	 * 
	 * @param offerForetellId排期应用号
	 * @param thirdPartOrderId流水号
	 * @param balance要支付的余额
	 * @param payType付费类型
	 * @param paySeqNo影院会员卡支付交易流水号
	 * @return
	 * @throws ServiceException
	 */
	private Order verifyOrder(String offerForetellId, String thirdPartOrderId, String balance, String payType, String paySeqNo) throws ServiceException {

		String recvMobilePhone = "13800138000";// 接收二唯码手机号码
		String sendType = "100";// 接收二唯码的方式移动号码100：短信300：彩信二唯码301：NOKIA长短信二唯码
								// 302：非NOKIA长短信二唯码非移动号码400：信息机发送短信
		String payResult = "0";// 支付结果(0成功1失败)
		String isCmtsPay = "false";// 是否由满天星负责扣费。true表示由满天星负责扣费，false表示由合作方扣费。
		String isCmtsSendCode = "false";// 是否由满天星负责发送二维码。true表示由满天星负责发送二维码，false表示由合作方负责发送二维码。
		String payMobile = "";// 支付手机号码，若需要满天星支付，填支付的手机号码
		String bookSign = "0";// 0全额支付1预定金方式
		String payed = "0";// 商城已经支付的金额 Balance + Payed = 票价
		String sendModeID = "0";// 满天星发送二唯码的模板编号

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("FeatureAppNo", offerForetellId);
		ps.put("SerialNum", thirdPartOrderId);
		ps.put("Printpassword", "");
		ps.put("Balance", balance);
		ps.put("PayType", payType);
		ps.put("RecvMobilePhone", recvMobilePhone);
		ps.put("SendType", sendType);
		ps.put("PayResult", payResult);
		ps.put("IsCmtsPay", isCmtsPay);
		ps.put("IsCmtsSendCode", isCmtsSendCode);
		ps.put("PayMobile", payMobile);
		ps.put("BookSign", bookSign);
		ps.put("Payed", payed);
		ps.put("SendModeID", sendModeID);
		ps.put("PaySeqNo", paySeqNo);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String sign = getMD5Params(ps, token);

		Order order = new Order();

		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\"?>").append("<SellTicketParameter>").append("<AppCode>").append(offerInfo.getAccount()).append("</AppCode>")
				.append("<FeatureAppNo>").append(offerForetellId).append("</FeatureAppNo>").append("<SerialNum>").append(thirdPartOrderId).append("</SerialNum>")
				.append("<Printpassword></Printpassword>").append("<Balance>").append(balance).append("</Balance>").append("<PayType>").append(payType).append("</PayType>")
				.append("<RecvMobilePhone>").append(recvMobilePhone).append("</RecvMobilePhone>").append("<SendType>").append(sendType).append("</SendType>").append("<PayResult>")
				.append(payResult).append("</PayResult>").append("<IsCmtsPay>").append(isCmtsPay).append("</IsCmtsPay>").append("<IsCmtsSendCode>").append(isCmtsSendCode)
				.append("</IsCmtsSendCode>").append("<PayMobile>").append(payMobile).append("</PayMobile>").append("<BookSign>").append(bookSign).append("</BookSign>")
				.append("<Payed>").append(payed).append("</Payed>").append("<SendModeID>").append(sendModeID).append("</SendModeID>").append("<PaySeqNo>").append(paySeqNo)
				.append("</PaySeqNo>").append("<TokenID>").append(token.get(TOKEN_ID)).append("</TokenID>").append("<VerifyInfo>").append(sign).append("</VerifyInfo>")
				.append("</SellTicketParameter>");

		ps.clear();
		ps.put("pXmlString", xmlStr.toString());

		Element rootE = analyst("SellTicket", ps);
		order.setConfirmationId(rootE.elementText("ValidCode"));
		order.setBookId(rootE.elementText("OrderNo"));
		return order;
	}

	public boolean SetFeaturePrice(String offerForetellId, double price) throws ServiceException {

		Map<String, Object> token = this.getToken();
		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		int p = (int) price;
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pFeatureAppNo", offerForetellId);
		ps.put("pAppPric", p + "");
		ps.put("pBalancePric", p + "");
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("SetFeaturePrice", ps);
		return true;
	}

	public Order queryOrder(String thirdPartOrderId, int count) throws ServiceException {

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pSerialNum", thirdPartOrderId);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Order order = new Order();
		Element rootE = analyst("GetOrderStatus", ps);
		if (rootE != null) {
			int errorCode = Integer.parseInt(rootE.elementText("ResultCode"));
			if (errorCode == 0) {
				order.setConfirmationId(rootE.elementText("ValidCode"));
				order.setBookId(rootE.elementText("OrderNo"));
				order.setStatus(Integer.parseInt(rootE.elementText("OrderStatus")));
				order.setSkyStatus(order.getStatus());
				SkyStarHelper.setOrderMsgByOrderStatus(order);
				if (order.getStatus() == 8) {
					order.setExchangeNum(count);
				}
			} else {
				order.setStatus(-2);
				order.setMessage("落地失败");
			}
		}
		return order;
	}

	public Order verifyOrder(String offerForetellId, String thirdPartOrderId, String balance, String payType, String paySeqNo, boolean isTest) throws ServiceException {

		Order order = new Order();

		if (!isTest) {
			return this.verifyOrder(offerForetellId, thirdPartOrderId, balance, payType, "");
		} else {
			order.setStatus(4);
			order.setConfirmationId("12345678");
			order.setMessage("地面售票成功");
			order.setBookId("1235465");
			return order;
		}
	}

	public Order queryOrder(String thirdPartOrderId, boolean isTest, int count) throws ServiceException {
		if (!isTest) {
			return this.queryOrder(thirdPartOrderId, count);
		} else {
			Order order = new Order();
			order.setStatus(4);
			order.setConfirmationId("12345678");
			order.setMessage("地面售票成功");
			order.setBookId("1235465");
			return order;
		}
	}

	public int queryYSOrder(String orderId) throws ServiceException {
		Order o = this.queryOrder(orderId, 0);
		return o.getSkyStatus();
	}

	public int cancelOrder(String offerOrderId) throws ServiceException {
		return cancelOrder(offerOrderId, "ERROR");
	}

	public int cancelOrder(String offerorderId, String msg) throws ServiceException {

		Map<String, Object> token = this.getToken();
		int cancelResult = 0;

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pOrderNO", offerorderId);
		ps.put("pDesc", msg);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = this.getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);
		Element rootE = analyst("BackTicket", ps);
		if (rootE != null) {
			String resultCode = rootE.elementText("ResultCode");
			int errorCode = resultCode == null ? response_null_error : Integer.parseInt(resultCode);

			if (errorCode == 0) {
				cancelResult = 1;
			} else {
				cancelResult = 2;
			}
		}
		return cancelResult;
	}

	// -1:失败 0-提供商打票 1-卖座打票 2-未打 3-未知
	public int printTicket(String orderNo, String validCode, String type) throws ServiceException {

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pOrderNO", orderNo);
		ps.put("pValidCode", validCode);
		ps.put("pRequestType", type);
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("AppPrintTicket", ps);
		if (rootE != null) {
			int printType = 2;

			int errorCode = Integer.parseInt(rootE.elementText("ResultCode"));
			if (errorCode == 0) {
				printType = Integer.parseInt(rootE.elementText("PrintType"));
				return printType;
			}
		}
		return -1;
	}

	// 获取电影院状态信息
	public Map<String, String> getCinemaState() throws ServiceException {
		Map<String, String> map = new HashMap<String, String>();

		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		Element rootE = analyst("GetCinema", ps);
		int errorCode = Integer.parseInt(rootE.elementText("ResultCode"));
		if (errorCode == 0) {
			Element cinemasE = rootE.element("Cinemas");
			Iterator iterator = cinemasE.elementIterator();
			while (iterator.hasNext()) {
				Element cinemaEl = (Element) iterator.next();
				map.put(cinemaEl.elementText("PlaceNo"), cinemaEl.elementText("State"));
			}
		}
		return map;
	}

	@Override
	public void unlockSeats(RequestUnlockObj obj) throws ServiceException {
		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		ps.put("pOrderNO", obj.getOrderId());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String verifyInfo = getMD5Params(ps, token);
		ps.put("pVerifyInfo", verifyInfo);

		analyst("UnLockOrder", ps);
	}

	@Override
	public void lockSeat(RequestLockObj obj) throws ServiceException {
		String[] seats = obj.getSeatId().split("\\|");

		List<String> offerSeatIds = new ArrayList<String>();

		for (int i = 0; i < seats.length; i++) {
			String[] seatId = seats[i].split("\\:");
			String rowId = seatId[0];
			String columnId = seatId[1];
			Seat seat = seatService.get(obj.getCinemaId(), obj.getHallId(), obj.getSectionId(), rowId, columnId);
			offerSeatIds.add(seat.getOfferSeatId());
		}

		// mm.getSeatIds ( offerCinemaId, hallId, sectionId , seats);
		if (offerSeatIds.size() < 1) {
			throw new ServiceException(calssName, 101105, "数据库中影院对应提供商座位不合法...");
		}
		LinkedHashMap<String, Object> ps = new LinkedHashMap<String, Object>();
		ps.put("pAppCode", offerInfo.getAccount());
		// 排期编号
		ps.put("FeatureAppNo", obj.getSeqNo());
		// 订单号
		ps.put("SerialNum", obj.getOrderNo());
		ps.put("Length", seats.length + "");
		// 付费类型
		ps.put("PayType", obj.getPayType());
		// 接收二唯码手机号码
		ps.put("RecvMobilePhone", obj.getMobilePhone());
		ps.put("pTokenID", token.get(TOKEN_ID));
		String sign = getMD5Params(ps, token);

		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\"?><RealCheckSeatStateParameter><AppCode>");
		xmlStr.append(offerInfo.getAccount()).append("</AppCode>").append("<FeatureAppNo>").append(obj.getSeqNo()).append("</FeatureAppNo>").append("<SerialNum>")
				.append(obj.getOrderNo()).append("</SerialNum><SeatInfos>");

		for (int i = 0; i < offerSeatIds.size(); i++) {
			xmlStr.append("<SeatInfo><SeatNo>").append(offerSeatIds.get(i)).append("</SeatNo><TicketPrice>").append(1)
					.append("</TicketPrice><Handlingfee>0</Handlingfee></SeatInfo>");
		}

		xmlStr.append("</SeatInfos><PayType>").append(obj.getPayType()).append("</PayType><RecvMobilePhone>").append(obj.getMobilePhone()).append("</RecvMobilePhone>")
				.append("<TokenID>").append(token.get(TOKEN_ID)).append("</TokenID><VerifyInfo>").append(sign).append("</VerifyInfo>").append("</RealCheckSeatStateParameter>");

		ps.clear();
		ps.put("pXmlString", xmlStr.toString());

		Element rootE = analyst("LiveRealCheckSeatState", ps);
		String skyStarOrderId = rootE.elementText("OrderNo");

	}

	@SuppressWarnings("unused")
	@Override
	public Order queryOrder(RequestQueryOrderObj obj) throws ServiceException {
		if (false) {
			return this.queryOrder(obj.getRandKey(), 0);
		} else {

			Order order = new Order();
			order.setStatus(4);
			order.setConfirmationId("12345678");
			order.setMessage("地面售票成功");
			order.setBookId("1235465");

			return order;

		}
	}

	@SuppressWarnings("unused")
	@Override
	public Order verifyOrder(RequestVerifyOrderObj obj) throws ServiceException {
		Order order = new Order();

		if (false) {
			return this.verifyOrder(obj.getOfferForetellId(), obj.getThirdPartOrderId(), obj.getBalance(), obj.getPayType(), obj.getPaySeqNo());
		} else {
			order.setStatus(4);
			order.setConfirmationId("12345678");
			order.setMessage("地面售票成功");
			order.setBookId("1235465");
			return order;
		}
	}
}
