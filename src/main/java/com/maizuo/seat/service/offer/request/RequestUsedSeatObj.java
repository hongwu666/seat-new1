package com.maizuo.seat.service.offer.request;

import com.maizuo.seat.entity.Foretell;

public class RequestUsedSeatObj {
	// 提供商影院id
	private String cinemaId;
	// link id
	private String cinemaLinkID;
	// 厅id
	private String hallId;
	// 厅区域id
	private String sectionId;
	// 电影id
	private String filmId;
	// 排期应用号2
	private String showSeqNo;
	// 排期应用号1
	private String seqNo;
	// 日期
	private String showDate;
	// 时间
	private String showTime;
	// 排期id
	private String foretellId;

	public String getCinemaId() {
		return cinemaId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getCinemaLinkID() {
		return cinemaLinkID;
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

	public String getForetellId() {
		return foretellId;
	}

	public void setForetellId(String foretellId) {
		this.foretellId = foretellId;
	}

	public RequestUsedSeatObj(Foretell foretell) {
		this.setCinemaId(foretell.getOfferCinemaId());
		this.setSeqNo(foretell.getSeqNo());
		this.setHallId(foretell.getHallId());
		this.setFilmId(foretell.getOfferMovieId());
		//this.setSectionId(foretell.getSectionId());
		this.setShowSeqNo(foretell.getShowSeqNo());
		this.setShowDate(foretell.getShowDate());
		this.setShowTime(foretell.getShowTime());
		this.setForetellId(foretell.getId());
	}

	public RequestUsedSeatObj() {
		super();
	}

}
