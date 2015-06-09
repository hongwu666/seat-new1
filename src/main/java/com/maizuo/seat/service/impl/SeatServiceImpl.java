package com.maizuo.seat.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.maizuo.seat.factory.ServiceFactory;
import com.maizuo.seat.helper.ForIdToOfferIdHelper;
import com.maizuo.seat.service.SeatService;
import com.maizuo.seat.util.UrlRequestUtils;

@Service
public class SeatServiceImpl implements SeatService {

	/**
	 * 转发服务器
	 * 
	 * @param foretellId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String forwarderServer(String bufUrl, HttpServletRequest request) {
		String offerId = request.getParameter("offerid");
		String foretellId = request.getParameter("foretellid");
		String cinemaId = request.getParameter("cinemaid");
		if (offerId == null) {
			if (foretellId != null) {
				offerId = ForIdToOfferIdHelper.getItems(Integer.parseInt(foretellId))[0] + "";
			} else if (cinemaId != null) {
				offerId = "1";
			} else {
				offerId = "DEFAULT";
			}
		}
		String OFFER_URL = "OFFER_" + offerId + "_URL";
		String url = ServiceFactory.getValue(OFFER_URL);
		url += bufUrl;

		Map map = request.getParameterMap();
		Map<String, String> paraMap = new HashMap<String, String>();
		Iterator<Entry<String, String[]>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			for (String str : entry.getValue()) {
				paraMap.put(entry.getKey(), str);
			}
		}
		return UrlRequestUtils.execute(url, paraMap, UrlRequestUtils.Mode.GET);
	}

}
