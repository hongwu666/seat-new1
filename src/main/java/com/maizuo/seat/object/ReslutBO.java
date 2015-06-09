package com.maizuo.seat.object;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maizuo.seat.service.offer.OfferService;

@XmlRootElement(name = "Result")
public class ReslutBO {

	private int errorCode;

	private String errMsg;

	private String result;

	private List<CinemaOffer> list;

	private List<ShowOffer> showOffers;

	private List<FilmOffer> filmOffers;

	private List<SeatOffer> seatOffers;

	public List<CinemaOffer> getList() {
		return list;
	}

	@XmlElementWrapper(name = "cinemas")
	@XmlElement(name = "cinema")
	public void setList(List<CinemaOffer> list) {
		this.list = list;
	}

	public List<ShowOffer> getShowOffers() {
		return showOffers;
	}

	@XmlElementWrapper(name = "showOffers")
	@XmlElement(name = "showOffer")
	public void setShowOffers(List<ShowOffer> showOffers) {
		this.showOffers = showOffers;
	}

	public List<SeatOffer> getSeatOffers() {
		return seatOffers;
	}

	@XmlElementWrapper(name = "seats")
	@XmlElement(name = "seat")
	public void setSeatOffers(List<SeatOffer> seatOffers) {
		this.seatOffers = seatOffers;
	}

	public List<FilmOffer> getFilmOffers() {
		return filmOffers;
	}

	@XmlElementWrapper(name = "films")
	@XmlElement(name = "film")
	public void setFilmOffers(List<FilmOffer> filmOffers) {
		this.filmOffers = filmOffers;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public String getResult() {
		return result;
	}

	@XmlElement
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@XmlElement
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@XmlElement
	public void setResult(String result) {
		this.result = result;
	}

	public ReslutBO(OfferService offerService) {
		this.errorCode = offerService.getErrorCode();
		this.errMsg = offerService.getErrorMsg();
		this.result = offerService.getResult() ? "success" : "fail";
	}

	public ReslutBO() {
		super();
	}

}
