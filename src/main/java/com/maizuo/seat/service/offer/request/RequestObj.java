package com.maizuo.seat.service.offer.request;

public  class RequestObj {
	/**
	 * 提供商影院id
	 */
	private String offerCinemaId;
	/**
	 * linkid
	 */
	private String linkId;
	/**
	 * 查詢的日期
	 */
	private String showDate;
	/**
	 * 厅id
	 */
	private String hallId;
	
	public String getOfferCinemaId() {
		return offerCinemaId;
	}
	public String getLinkId() {
		return linkId;
	}
	public String getShowDate() {
		return showDate;
	}
	public String getHallId() {
		return hallId;
	}
	public void setHallId(String hallId) {
		this.hallId = hallId;
	}
	public void setOfferCinemaId(String offerCinemaId) {
		this.offerCinemaId = offerCinemaId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	




}

