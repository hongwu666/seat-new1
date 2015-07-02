package com.maizuo.seat.service.offer.request;

public class RequestQueryOrderObj {
	// 订单号
	private String orderNo;
	// 购票数量
	private int ticketCount;
	// 影院编号
	private String cinemaId;
	// 影院连接编号
	private String cinemaLinkId;
	// 随机串号
	private String randKey;

	public String getOrderNo() {
		return orderNo;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	public String getRandKey() {
		return randKey;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setTicketCount(int ticketCount) {
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
