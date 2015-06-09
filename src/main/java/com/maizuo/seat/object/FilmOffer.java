package com.maizuo.seat.object;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.dom4j.Element;

public class FilmOffer {
	private String cinemaId;
	private String cinemaLinkId;
	private String offerFilmId;
	private int filmId;
	private String filmName;
	private String filmLanguage;
	private String filmTitle;
	private String filmType;
	private String filmDimesional;
	private String filmDuration;
	// maizuo数据
	private String actors;
	private String movieType;
	private String picName;
	private List<ShowOffer> shows;
	private int relaFlag;
	private int maizuoFilmId;



	public String getCinemaId() {
		return cinemaId;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	public String getOfferFilmId() {
		return offerFilmId;
	}

	public int getFilmId() {
		return filmId;
	}

	public String getFilmName() {
		return filmName;
	}

	public String getFilmLanguage() {
		return filmLanguage;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public String getFilmType() {
		return filmType;
	}

	public String getFilmDimesional() {
		return filmDimesional;
	}

	public String getFilmDuration() {
		return filmDuration;
	}

	public String getActors() {
		return actors;
	}

	public String getMovieType() {
		return movieType;
	}

	public String getPicName() {
		return picName;
	}

	public List<ShowOffer> getShows() {
		return shows;
	}

	public int getRelaFlag() {
		return relaFlag;
	}

	public int getMaizuoFilmId() {
		return maizuoFilmId;
	}
	@XmlAttribute
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	@XmlAttribute
	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}
	@XmlAttribute
	public void setOfferFilmId(String offerFilmId) {
		this.offerFilmId = offerFilmId;
	}
	@XmlAttribute
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	@XmlAttribute
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	@XmlAttribute
	public void setFilmLanguage(String filmLanguage) {
		this.filmLanguage = filmLanguage;
	}
	@XmlAttribute
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	@XmlAttribute
	public void setFilmType(String filmType) {
		this.filmType = filmType;
	}
	@XmlAttribute
	public void setFilmDimesional(String filmDimesional) {
		this.filmDimesional = filmDimesional;
	}
	@XmlAttribute
	public void setFilmDuration(String filmDuration) {
		this.filmDuration = filmDuration;
	}
	@XmlAttribute
	public void setActors(String actors) {
		this.actors = actors;
	}
	@XmlAttribute
	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}
	@XmlAttribute
	public void setPicName(String picName) {
		this.picName = picName;
	}
	@XmlElementWrapper(name="shows")
	@XmlElement(name="showOffer")
	public void setShows(List<ShowOffer> shows) {
		this.shows = shows;
	}
	@XmlAttribute
	public void setRelaFlag(int relaFlag) {
		this.relaFlag = relaFlag;
	}
	@XmlAttribute
	public void setMaizuoFilmId(int maizuoFilmId) {
		this.maizuoFilmId = maizuoFilmId;
	}

	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\t").append(cinemaId).append("\t").append(filmId).append("\t").append(filmName).append("\t").append(filmLanguage).append("\t").append(filmTitle)
				.append("\t").append(filmType).append("\t").append(filmDimesional).append("\t").append(filmDuration);

		return strBuffer.toString();
	}

	public boolean equals(Object o) {
		if (!(o instanceof FilmOffer)) {
			return false;
		}
		FilmOffer f = (FilmOffer) o;
		return (f.getOfferFilmId().equals(offerFilmId));
	}

	public FilmOffer() {
		super();
	}

	public FilmOffer(Element element) {
		this.setFilmDuration(element.attributeValue("duration"));
		this.setFilmDimesional(element.attributeValue("dimensional"));
		this.setFilmType(element.attributeValue("imax"));
		this.setFilmTitle(element.attributeValue("title"));
		this.setFilmLanguage(element.attributeValue("language"));
		this.setFilmName(element.attributeValue("name"));
		this.setOfferFilmId(element.attributeValue("id"));
		this.setFilmId(0);
	}

}
