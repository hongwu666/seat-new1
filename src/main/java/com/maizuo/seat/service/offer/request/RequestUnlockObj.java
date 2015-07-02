package com.maizuo.seat.service.offer.request;

public class RequestUnlockObj {
	// 订单id
	private String orderId;
	// 座位数量
	private String ticketCount;
	// 提供商影院id
	private String cinemaId;
	// linkid
	private String cinemaLinkId;
	// 八为随机数
	private String randKey;

	/**
	 * 订单id
	 * 
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 座位数量
	 * 
	 * @return
	 */
	public String getTicketCount() {
		return ticketCount;
	}

	/**
	 * 影院id
	 * 
	 * @return
	 */
	public String getCinemaId() {
		return cinemaId;
	}

	/**
	 * linkid
	 * 
	 * @return
	 */
	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	/**
	 * 随机数
	 * 
	 * @return
	 */
	public String getRandKey() {
		return randKey;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}

	public void setRandKey(String randKey) {
		this.randKey = randKey;
	}

}
