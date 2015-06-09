package com.maizuo.seat.entity;

import java.util.Date;

public class Seat {
	private int id;
	private int hallId;
	private int offerId;
	private int offerSeatId;
	private int cinemaId;
	private int sectionId;
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

	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getOfferSeatId() {
		return offerSeatId;
	}

	public void setOfferSeatId(int offerSeatId) {
		this.offerSeatId = offerSeatId;
	}

	public int getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
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

}
