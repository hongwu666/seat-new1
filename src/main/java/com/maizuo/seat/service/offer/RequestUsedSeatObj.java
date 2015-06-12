package com.maizuo.seat.service.offer;

public class RequestUsedSeatObj {
	private String cinemaId;
	private String cinemaLinkID;
	private String hallId;
	private String sectionId;
	private String filmId;
	private String showSeqNo;
	private String showDate;
	private String showTime;
	private int Ciz;

	public String getCinemaId() {
		return cinemaId;
	}

	public String getCinemaLinkID() {
		return cinemaLinkID;
	}

	public int getCiz() {
		return Ciz;
	}

	public void setCiz(int ciz) {
		Ciz = ciz;
	}

	public String getHallId() {
		return hallId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public String getFilmId() {
		return filmId;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	public String getShowDate() {
		return showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public void setCinemaLinkID(String cinemaLinkID) {
		this.cinemaLinkID = cinemaLinkID;
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

}
