package com.maizuo.seat.entity;

import java.sql.Date;

public class Foretell implements SystemMode {
	// 唯一id
	private String id;
	// 影院id
	private String offerCinemaId;
	// 影片id
	private String offerMovieId;
	// 日期
	private String showDate;
	// 时间
	private String showTime;
	// 厅id
	private String hallId;
	// 厅名称
	private String hallName;
	private String seqNo;
	private String showSeqNo;

	private String sectionId;
	// 最低价格
	private String lowstPrice;
	// 价格
	private String price;
	private String dimensional;
	private String carrier;
	private String LANGUAGE;
	private int mzCinemaId;
	private int mzMovieId;
	private int mzPrice;
	private int mzFee;
	private Date offerLastUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOfferCinemaId() {
		return offerCinemaId;
	}

	public void setOfferCinemaId(String offerCinemaId) {
		this.offerCinemaId = offerCinemaId;
	}

	public String getOfferMovieId() {
		return offerMovieId;
	}

	public void setOfferMovieId(String offerMovieId) {
		this.offerMovieId = offerMovieId;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getHallId() {
		return hallId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	public void setShowSeqNo(String showSeqNo) {
		this.showSeqNo = showSeqNo;
	}

	public String getLowstPrice() {
		return lowstPrice;
	}

	public void setLowstPrice(String lowstPrice) {
		this.lowstPrice = lowstPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDimensional() {
		return dimensional;
	}

	public void setDimensional(String dimensional) {
		this.dimensional = dimensional;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getLANGUAGE() {
		return LANGUAGE;
	}

	public void setLANGUAGE(String lANGUAGE) {
		LANGUAGE = lANGUAGE;
	}

	public int getMzCinemaId() {
		return mzCinemaId;
	}

	public void setMzCinemaId(int mzCinemaId) {
		this.mzCinemaId = mzCinemaId;
	}

	public int getMzMovieId() {
		return mzMovieId;
	}

	public void setMzMovieId(int mzMovieId) {
		this.mzMovieId = mzMovieId;
	}

	public int getMzPrice() {
		return mzPrice;
	}

	public void setMzPrice(int mzPrice) {
		this.mzPrice = mzPrice;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public int getMzFee() {
		return mzFee;
	}

	public void setMzFee(int mzFee) {
		this.mzFee = mzFee;
	}

	public Date getOfferLastUpdate() {
		return offerLastUpdate;
	}

	public void setOfferLastUpdate(Date offerLastUpdate) {
		this.offerLastUpdate = offerLastUpdate;
	}

	@Override
	public String getListKey() {
		return null;
	}

	@Override
	public String getObjKey() {
		return id;
	}

	public Foretell() {
		super();
	}

}
