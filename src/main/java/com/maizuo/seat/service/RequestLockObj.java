package com.maizuo.seat.service;

import com.maizuo.seat.entity.Foretell;

public class RequestLockObj {
	//
	private String orderNo;
	// 購票數量
	private String ticketCount;
	// 提供商影院id
	private String cinemaId;
	// 提供商影院linkid
	private String cinemaLinkId;
	// 庁id
	private String hallId;
	// 區域id
	private String sectionId;
	// 電影id
	private String filmId;
	// 排期应用编号1
	private String seqNo;
	// 排期应用编号2
	private String showSeqNo;
	// 日期
	private String showDate;
	// 时间
	private String showTime;
	// 座位号rowid:coluid|xx
	private String seatId;
	// 随机8位
	private String randKey;
	// 卖座影院id
	private String mzCinemaId;
	// 付费类型
	private String payType;
	// 接受二維碼的手機號
	private String mobilePhone;

	/**
	 * 订单号
	 * 
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 购票数量
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
	 * 庁id
	 * 
	 * @return
	 */
	public String getHallId() {
		return hallId;
	}

	/**
	 * 区域id
	 * 
	 * @return
	 */
	public String getSectionId() {
		return sectionId;
	}

	/**
	 * 影片id
	 * 
	 * @return
	 */
	public String getFilmId() {
		return filmId;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	/**
	 * 日期
	 * 
	 * @return
	 */
	public String getShowDate() {
		return showDate;
	}

	/**
	 * 时间
	 * 
	 * @return
	 */
	public String getShowTime() {
		return showTime;
	}

	/**
	 * 座位号rowId:columnid|xx
	 * 
	 * @return
	 */
	public String getSeatId() {
		return seatId;
	}

	/**
	 * 8位随机数
	 * 
	 * @return
	 */
	public String getRandKey() {
		return randKey;
	}

	/**
	 * 卖座影院id
	 * 
	 * @return
	 */
	public String getMzCinemaId() {
		return mzCinemaId;
	}

	/**
	 * 付費類型
	 * 
	 * @return
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * 手機號
	 * 
	 * @return
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public void setShowSeqNo(String showSeqNo) {
		this.showSeqNo = showSeqNo;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public void setRandKey(String randKey) {
		this.randKey = randKey;
	}

	public void setMzCinemaId(String mzCinemaId) {
		this.mzCinemaId = mzCinemaId;
	}

	/**
	 * 获得排期应用编号1
	 * 
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public RequestLockObj(Foretell foretell) {
		this.setShowSeqNo(foretell.getShowSeqNo());
		this.setSeqNo(foretell.getSeqNo());
		this.setShowTime(foretell.getShowTime());
		this.setShowDate(foretell.getShowDate());
		this.setSectionId(foretell.getSectionId() == null ? "01" : foretell.getSectionId());
		this.setFilmId(foretell.getOfferMovieId());
		this.setHallId(foretell.getHallId() + "");
	}
}
