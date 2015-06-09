package com.maizuo.seat.object;

import javax.xml.bind.annotation.XmlAttribute;

import org.dom4j.Element;

public class SeatOffer {

	private String rowId;
	private String rowNum;
	private String columnId;
	private String columnNum;
	private String offerSeatId;
	private int status;
	private String payFlag;
	private int isLocked;
	private String offerType;
	private String damagedFlg;
	private String loveInd;
	private String sectionId;
	private String effective;
	private String effectiveDate;
	private String cinemaId;
	private String hallId;
	private int seatId;
	private int seatType; // 0:noSeat 1:hasSeat 2:sellSeat 3:loveSeat

	public String getRowId() {
		return rowId;
	}

	public String getRowNum() {
		return rowNum;
	}

	public String getColumnId() {
		return columnId;
	}

	public String getColumnNum() {
		return columnNum;
	}

	public String getOfferSeatId() {
		return offerSeatId;
	}

	public int getStatus() {
		return status;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public String getOfferType() {
		return offerType;
	}

	public String getDamagedFlg() {
		return damagedFlg;
	}

	public String getLoveInd() {
		return loveInd;
	}

	public String getSectionId() {
		return sectionId;
	}

	public String getEffective() {
		return effective;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public String getHallId() {
		return hallId;
	}

	public int getSeatId() {
		return seatId;
	}

	public int getSeatType() {
		return seatType;
	}

	@XmlAttribute
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	@XmlAttribute
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	@XmlAttribute
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	@XmlAttribute
	public void setColumnNum(String columnNum) {
		this.columnNum = columnNum;
	}

	@XmlAttribute
	public void setOfferSeatId(String offerSeatId) {
		this.offerSeatId = offerSeatId;
	}

	@XmlAttribute
	public void setStatus(int status) {
		this.status = status;
	}

	@XmlAttribute
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	@XmlAttribute
	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	@XmlAttribute
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	@XmlAttribute
	public void setDamagedFlg(String damagedFlg) {
		this.damagedFlg = damagedFlg;
	}

	@XmlAttribute
	public void setLoveInd(String loveInd) {
		this.loveInd = loveInd;
	}

	@XmlAttribute
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	@XmlAttribute
	public void setEffective(String effective) {
		this.effective = effective;
	}

	@XmlAttribute
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@XmlAttribute
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	@XmlAttribute
	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	@XmlAttribute
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	@XmlAttribute
	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}

	public SeatOffer() {
	}

	public SeatOffer(Element element) {
		this.setRowId(element.attributeValue("rowId"));
		this.setRowNum(element.attributeValue("rowNum"));
		this.setColumnId(element.attributeValue("columnId"));
		this.setColumnNum(element.attributeValue("columnNum"));
		this.setOfferType(element.attributeValue("typeInd"));
		this.setDamagedFlg(element.attributeValue("damagedFlg"));
		this.setLoveInd(element.attributeValue("loveInd"));
	}

}
