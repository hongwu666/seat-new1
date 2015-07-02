package com.maizuo.seat.entity;

import java.util.List;

public class Order {
	private int id;
	private int skyStatus;
	private String cinemaId;
	private String hallId;
	private String filmId;
	private String showDate;
	private String showSeqNo;
	private String showTime;
	private List<Seat> seatList;

	private String tpOrderId;
	private String seatOrderId;
	private int bid;
	private String result;
	private String statusInd;
	private String bookId = "";
	private String confirmationId = "";

	private String message;
	private int status = 1;
	private int isConfirm;
	private int isNew;
	private int adviceNum;
	private int exchangeNum = 0;
	private int seconds;
	private int count;
	private int isReturn = 0;
	private String returnAttrs;
	private int mzCinemaId;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getMzCinemaId() {
		return mzCinemaId;
	}

	public void setMzCinemaId(int mzCinemaId) {
		this.mzCinemaId = mzCinemaId;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getSkyStatus() {
		return skyStatus;
	}

	public void setSkyStatus(int skyStatus) {
		this.skyStatus = skyStatus;
	}

	public int getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(int isReturn) {
		this.isReturn = isReturn;
	}

	public String getReturnAttrs() {
		return returnAttrs;
	}

	public void setReturnAttrs(String returnAttrs) {
		this.returnAttrs = returnAttrs;
	}

	public int getAdviceNum() {
		return adviceNum;
	}

	public void setAdviceNum(int adviceNum) {
		this.adviceNum = adviceNum;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public String getSeatOrderId() {
		return seatOrderId;
	}

	public void setSeatOrderId(String seatOrderId) {
		this.seatOrderId = seatOrderId;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getTpOrderId() {
		return tpOrderId;
	}

	public void setTpOrderId(String tpOrderId) {
		this.tpOrderId = tpOrderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getHallId() {
		return hallId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	public void setShowSeqNo(String showSeqNo) {
		this.showSeqNo = showSeqNo;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public List<Seat> getSeatList() {
		return seatList;
	}

	public void setSeatList(List<Seat> seatList) {
		this.seatList = seatList;
	}

	public String getStatusInd() {
		return statusInd;
	}

	public void setStatusInd(String statusInd) {
		this.statusInd = statusInd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public int getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(int isConfirm) {
		this.isConfirm = isConfirm;
	}

	public int getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(int exchangeNum) {
		this.exchangeNum = exchangeNum;
	}

	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\t").append(result).append("\t").append(cinemaId).append("\t").append(hallId).append("\t").append(filmId).append("\t").append(hallId).append("\t")
				.append(showDate).append("\t").append(showSeqNo).append("\t").append(showTime).append("\t").append(statusInd).append("\t").append(message).append("\t")
				.append(bookId).append("\t").append(confirmationId).append("\n");
		if (null != seatList) {
			for (Seat seat : seatList) {
				strBuffer.append("\n").append(seat.toString());
			}
		}

		return strBuffer.toString();
	}

}
