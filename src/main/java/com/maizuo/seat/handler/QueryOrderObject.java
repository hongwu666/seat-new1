package com.maizuo.seat.handler;

import com.maizuo.seat.service.offer.request.RequestQueryOrderObj;

public class QueryOrderObject {
	/**
	 * 开始时间
	 */
	private long time;
	/**
	 * 提供商id
	 */
	private int offerId;

	private RequestQueryOrderObj obj;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public RequestQueryOrderObj getObj() {
		return obj;
	}

	public void setObj(RequestQueryOrderObj obj) {
		this.obj = obj;
	}

}
