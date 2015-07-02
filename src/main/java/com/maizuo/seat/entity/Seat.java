package com.maizuo.seat.entity;

import java.util.Date;

public class Seat extends DefaultSystemRedisMode<Seat> {
	private int id;
	// 提供商厅id
	private String hallId;
	private int offerId;
	// 提供商座位id
	private String offerSeatId;
	// 提供商影院id
	private String cinemaId;
	// 区域id
	private String sectionId;
	private String rowId;
	private int rowNum;
	private String columnId;
	private int columnNum;
	private int loveInd;
	private Date date;
	private int isMoreLayer;
	private String seatName;
	private int mzCinemaId;
	private int effectFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHallId() {
		return hallId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getOfferSeatId() {
		return offerSeatId;
	}

	public void setOfferSeatId(String offerSeatId) {
		this.offerSeatId = offerSeatId;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public int getLoveInd() {
		return loveInd;
	}

	public void setLoveInd(int loveInd) {
		this.loveInd = loveInd;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIsMoreLayer() {
		return isMoreLayer;
	}

	public void setIsMoreLayer(int isMoreLayer) {
		this.isMoreLayer = isMoreLayer;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public int getMzCinemaId() {
		return mzCinemaId;
	}

	public void setMzCinemaId(int mzCinemaId) {
		this.mzCinemaId = mzCinemaId;
	}

	public int getEffectFlag() {
		return effectFlag;
	}

	public void setEffectFlag(int effectFlag) {
		this.effectFlag = effectFlag;
	}

	@Override
	public String getListKey() {
		return cinemaId + "_" + hallId;
	}

	@Override
	public String getObjKey() {
		return String.valueOf(id);
	}

}
