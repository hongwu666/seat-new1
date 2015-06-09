package com.maizuo.seat.object;

import javax.xml.bind.annotation.XmlAttribute;

import org.dom4j.Element;

/**
 * 排期相关信息
 * 
 * @author Administrator
 * 
 */
public class ShowOffer {
	private int Id;
	private int cinemaId;
	private int isLock;// 0 已锁1未锁
	private String offerCinemaId;
	private String cinemaLinkId;
	private String seqNo;
	private String showSeqNo;
	private String date;
	private String time;
	private String updateLevel;
	private String updateType;
	private String hallId;
	private String hallName;
	private int isSetFeaturePrice;
	private String hallType;
	private String offerLastUpdatetime;

	private int filmId;
	private String offerFilmId;
	private String filmTitle;
	private String filmName;
	private String filmLanguage;
	private int filmSeq;
	private int filmDuration;
	private int filmImax;
	private String filmDimesional;
	private String offerShowId;

	private int offerId;
	private String throughFlag;

	private String sectionId;
	private String priceStandard;
	private String priceLowest;

	private int timeoutFlag;

	private String weekzh;
	private int maizuoPrice;
	private int maizuoFee;

	private int dingZuoMovieId;
	private String mzFilmName;
	private String mzCinemaName;
	private String carrier = "";// 影片载体，如：胶片、数字、IMAX等(无值返回空字符串)

	public String getOfferLastUpdatetime() {
		return offerLastUpdatetime;
	}

	@XmlAttribute
	public void setOfferLastUpdatetime(String offerLastUpdatetime) {
		this.offerLastUpdatetime = offerLastUpdatetime;
	}

	public String getHallType() {
		return hallType;
	}

	@XmlAttribute
	public void setHallType(String hallType) {
		this.hallType = hallType;
	}

	public int getIsSetFeaturePrice() {
		return isSetFeaturePrice;
	}

	@XmlAttribute
	public void setIsSetFeaturePrice(int isSetFeaturePrice) {
		this.isSetFeaturePrice = isSetFeaturePrice;
	}

	public String getHallName() {
		return hallName;
	}

	@XmlAttribute
	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getCarrier() {
		return carrier;
	}

	@XmlAttribute
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getMzCinemaName() {
		return mzCinemaName;
	}

	@XmlAttribute
	public void setMzCinemaName(String mzCinemaName) {
		this.mzCinemaName = mzCinemaName;
	}

	public int getOfferId() {
		return offerId;
	}

	@XmlAttribute
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getMzFilmName() {
		return mzFilmName;
	}

	@XmlAttribute
	public void setMzFilmName(String mzFilmName) {
		this.mzFilmName = mzFilmName;
	}

	public int getMaizuoFee() {
		return maizuoFee;
	}

	@XmlAttribute
	public void setMaizuoFee(int maizuoFee) {
		this.maizuoFee = maizuoFee;
	}

	public int getMaizuoPrice() {
		return maizuoPrice;
	}

	@XmlAttribute
	public void setMaizuoPrice(int maizuoPrice) {
		this.maizuoPrice = maizuoPrice;
	}

	public String getWeekzh() {
		return weekzh;
	}

	@XmlAttribute
	public void setWeekzh(String weekzh) {
		this.weekzh = weekzh;
	}

	public int getTimeoutFlag() {
		return timeoutFlag;
	}

	@XmlAttribute
	public void setTimeoutFlag(int timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	@XmlAttribute
	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}

	public String getOfferShowId() {
		return offerShowId;
	}

	@XmlAttribute
	public void setOfferShowId(String offerShowId) {
		this.offerShowId = offerShowId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	@XmlAttribute
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	@XmlAttribute
	public void setShowSeqNo(String showSeqNo) {
		this.showSeqNo = showSeqNo;
	}

	public String getDate() {
		return date;
	}

	@XmlAttribute
	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	@XmlAttribute
	public void setTime(String time) {
		this.time = time;
	}

	public String getUpdateLevel() {
		return updateLevel;
	}

	@XmlAttribute
	public void setUpdateLevel(String updateLevel) {
		this.updateLevel = updateLevel;
	}

	public String getUpdateType() {
		return updateType;
	}

	@XmlAttribute
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getHallId() {
		return hallId;
	}

	@XmlAttribute
	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	@XmlAttribute
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public String getFilmName() {
		return filmName;
	}

	@XmlAttribute
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getFilmLanguage() {
		return filmLanguage;
	}

	@XmlAttribute
	public void setFilmLanguage(String filmLanguage) {
		this.filmLanguage = filmLanguage;
	}

	public int getFilmSeq() {
		return filmSeq;
	}

	@XmlAttribute
	public void setFilmSeq(int filmSeq) {
		this.filmSeq = filmSeq;
	}

	public int getFilmDuration() {
		return filmDuration;
	}

	@XmlAttribute
	public void setFilmDuration(int filmDuration) {
		this.filmDuration = filmDuration;
	}

	public int getFilmImax() {
		return filmImax;
	}

	@XmlAttribute
	public void setFilmImax(int filmImax) {
		this.filmImax = filmImax;
	}

	public String getFilmDimesional() {
		return filmDimesional;
	}

	@XmlAttribute
	public void setFilmDimesional(String filmDimesional) {
		this.filmDimesional = filmDimesional;
	}

	public String getThroughFlag() {
		return throughFlag;
	}

	@XmlAttribute
	public void setThroughFlag(String throughFlag) {
		this.throughFlag = throughFlag;
	}

	public String getSectionId() {
		return sectionId;
	}

	public String getOfferFilmId() {
		return offerFilmId;
	}

	@XmlAttribute
	public void setOfferFilmId(String offerFilmId) {
		this.offerFilmId = offerFilmId;
	}

	@XmlAttribute
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getPriceStandard() {
		return priceStandard;
	}

	@XmlAttribute
	public void setPriceStandard(String priceStandard) {
		this.priceStandard = priceStandard;
	}

	public String getPriceLowest() {
		return priceLowest;
	}

	@XmlAttribute
	public void setPriceLowest(String priceLowest) {
		this.priceLowest = priceLowest;
	}

	public int getCinemaId() {
		return cinemaId;
	}

	@XmlAttribute
	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getOfferCinemaId() {
		return offerCinemaId;
	}

	@XmlAttribute
	public void setOfferCinemaId(String offerCinemaId) {
		this.offerCinemaId = offerCinemaId;
	}

	public int getIsLock() {
		return isLock;
	}

	@XmlAttribute
	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getDingZuoMovieId() {
		return dingZuoMovieId;
	}

	@XmlAttribute
	public void setDingZuoMovieId(int dingZuoMovieId) {
		this.dingZuoMovieId = dingZuoMovieId;
	}

	public int getId() {
		return Id;
	}

	@XmlAttribute
	public void setId(int id) {
		Id = id;
	}

	public int getFilmId() {
		return filmId;
	}

	@XmlAttribute
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("\t").append(cinemaId).append("\t").append(seqNo).append("\t").append(showSeqNo).append("\t").append(date).append("\t").append(time).append("\t")
				.append(updateLevel).append("\t").append(updateType).append("\t").append(hallId).append("\t").append(offerFilmId).append("\t\t").append(filmTitle).append("\t\t")
				.append(filmName).append("\t").append(filmLanguage).append("\t").append(filmSeq).append("\t").append(filmDuration).append("\t").append(filmImax).append("\t")
				.append(filmDimesional).append("\t").append(throughFlag).append("\t").append(sectionId).append("\t").append(priceStandard).append("\t").append(priceLowest);

		return strBuffer.toString();
	}

	public ShowOffer() {
		super();
	}

	public ShowOffer(Element element) {
		this.setFilmDimesional(element.attributeValue("dimensional"));
		this.setFilmImax(Integer.parseInt(element.attributeValue("imax")));
		this.setFilmDuration(Integer.parseInt(element.attributeValue("duration")));
		this.setFilmTitle(element.attributeValue("title"));
		this.setFilmLanguage(element.attributeValue("language"));
		this.setFilmName(element.attributeValue("name"));
		this.setOfferFilmId(element.attributeValue("id"));
	}

}
