/*package com.maizuo.seat.service.offer.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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
import com.maizuo.seat.util.MD5;
import com.maizuo.seat.util.SimpleObject;
import com.maizuo.seat.util.UrlRequestUtils;

*//**
 * 蜘蛛网
 * 
 * @author Administrator
 * 
 *//*
public class SplierServiceImpl extends Common implements OfferService {
	private Map<String, String> encryptParams = new LinkedHashMap<>();
	private StringBuffer str = new StringBuffer();
	private StringBuffer strmd5 = new StringBuffer();

	private static Logger log = Logger.getLogger(SplierServiceImpl.class);

	private String getParams(String funcName) {
		str.setLength(0);
		strmd5.setLength(0);
		str.append("filetype=xml");
		for (Iterator<Entry<String, String>> it = encryptParams.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			str.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			strmd5.append(entry.getValue());
		}
		strmd5.append(offerInfo.getPassword());
		String enstr = MD5.getValUTF(strmd5.toString()).toLowerCase();

		str.append("&sign=").append(enstr);
		return str.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CinemaOffer> getCinemas() {
		long start = System.currentTimeMillis();
		encryptParams.clear();
		encryptParams.put("key", offerInfo.getAccount());
		Document document = null;
		SAXReader saxReader = new SAXReader();
		List<CinemaOffer> cinemaList = new ArrayList<CinemaOffer>();

		// 取蜘蛛网包含的所有城市
		String params = getParams("cityList");
		String urlstr = offerInfo.getUrl() + "/" + offerInfo.getAccount() + "/" + "cityList" + ".html";
		String documentStr = UrlRequestUtils.a(urlstr, params);
		try {
			document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("utf8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (document == null) {
			errorCode = 1092;
		} else {
			Element rootElm = document.getRootElement();
			errorCode = Integer.parseInt(rootElm.element("result").getText());
			errorMsg = rootElm.elementText("message");
			if (errorCode == 0) {
				List<Element> cityElms = rootElm.elements("cityInfo");
				for (Element elm : cityElms) {
					String cityCode = elm.attributeValue("cityCode");
					String cityName = elm.attributeValue("cityName");

					// 根据城市编码取得包含的影院信息
					encryptParams.clear();
					encryptParams.put("cityCode", cityCode);
					encryptParams.put("key", offerInfo.getAccount());
					params = getParams("cinemaList");
					String urlstr1 = offerInfo.getUrl() + "/" + offerInfo.getAccount() + "/" + "cinemaList" + ".html";
					documentStr = UrlRequestUtils.a(urlstr1, params);
					try {
						document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("utf8")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					rootElm = document.getRootElement();
					errorCode = Integer.parseInt(rootElm.element("result").getText());
					if (errorCode == 0) {
						List<Element> cinemaElms = rootElm.elements("cinemaInfo");
						for (Element ce : cinemaElms) {
							String cinemaId = ce.elementText("cinemaId");
							String cinemaName = ce.elementText("cinemaName");

							// 根据影院编码取的该影院厅信息
							this.encryptParams.clear();
							encryptParams.put("cinemaId", cinemaId);
							encryptParams.put("key", offerInfo.getAccount());
							params = getParams("hallList");
							String urlstr2 = offerInfo.getUrl() + "/" + offerInfo.getAccount() + "/" + "hallList" + ".html";
							documentStr = UrlRequestUtils.a(urlstr2, params);
							try {
								document = saxReader.read(new ByteArrayInputStream(documentStr.getBytes("utf8")));
							} catch (Exception e) {
								e.printStackTrace();
							}

							rootElm = document.getRootElement();
							errorCode = Integer.parseInt(rootElm.element("result").getText());
							if (errorCode == 0) {
								CinemaOffer cinema = new CinemaOffer();
								cinema.setOfferCityId(cityCode);
								cinema.setCityName(cityName);
								cinema.setOfferCinemaId(cinemaId);
								cinema.setCinemaName(cinemaName);
								String hallIds = "";
								String hallNames = "";
								String hallCounts = "";
								String hallVips = "";
								List<Element> hallElms = rootElm.elements("hallInfo");
								for (Element he : hallElms) {
									hallIds += "-" + he.elementText("hallId");
									hallNames += "-" + he.elementText("hallName");
									hallCounts += "-" + he.elementText("seatCount");
									hallVips += "-" + ("N".equals(he.elementText("vipFlag")) ? "0" : "1");
								}
								if (hallIds.length() > 0) {
									cinema.setHalls(hallIds.substring(1));
									cinema.setHallNames(hallNames.substring(1));
									cinema.setSeatCounts(hallCounts.substring(1));
									cinema.setVipFlags(hallVips.substring(1));
								}
								result = true;
								cinemaList.add(cinema);
							} else {
								this.setErrorMsg(rootElm.element("message").getText());
							}
						}
					} else {
						this.setErrorMsg(rootElm.element("message").getText());
					}
				}
			} else {
				this.setErrorMsg(rootElm.element("message").getText());
			}
		}
		long end = System.currentTimeMillis();
		log.error("getCinemas-------" + (end - start));
		return cinemaList;
	}

	@Override
	public boolean getResult() {
		return false;
	}

	@Override
	public int getErrorCode() {
		return 0;
	}

	@Override
	public String getErrorMsg() {
		return null;
	}

	@Override
	public void setIsShowLog(boolean isShowLog) {

	}

	@Override
	public List<FilmOffer> getFilms(RequestObj obj) {
		return null;
	}

	@Override
	public List<ShowOffer> getForetell(RequestObj obj) {
		return null;
	}

	@Override
	public List<SeatOffer> getSeats(RequestObj obj) {
		return null;
	}

	public void init() {
		this.offerInfo = OfferInfo.ins().get(3);
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