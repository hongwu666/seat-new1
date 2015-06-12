package com.maizuo.seat.object;

import javax.xml.bind.annotation.XmlAttribute;

import org.dom4j.Element;

public class CinemaOffer {

	private String cityId;
	private String offerCityId;
	private String offerName;
	private String cityName;
	private String offerCinemaId;
	private String cinemaName;
	private String region;
	private String address;
	private String phone;
	private int offerId;
	private String status;// 1：可用；0：暂停；2：测试

	private String linkId;
	private String halls;
	private String seatCounts;
	private String vipFlags;
	private String hallNames;
	private int cinemaId;
	private String longitude;
	private String latitude;
	private String bids;

	public String getBids() {
		return bids;
	}

	@XmlAttribute
	public void setBids(String bids) {
		this.bids = bids;
	}

	public String getLongitude() {
		return longitude;
	}

	@XmlAttribute
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	@XmlAttribute
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getOfferName() {
		return offerName;
	}

	@XmlAttribute
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferCityId() {
		return offerCityId;
	}

	@XmlAttribute
	public void setOfferCityId(String offerCityId) {
		this.offerCityId = offerCityId;
	}

	public String getOfferCinemaId() {
		return offerCinemaId;
	}

	@XmlAttribute
	public void setOfferCinemaId(String offerCinemaId) {
		this.offerCinemaId = offerCinemaId;
	}

	public int getCinemaId() {
		return cinemaId;
	}

	@XmlAttribute
	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getCityId() {
		return cityId;
	}

	@XmlAttribute
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	@XmlAttribute
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getRegion() {
		return region;
	}

	@XmlAttribute
	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	@XmlAttribute
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	@XmlAttribute
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOfferId() {
		return offerId;
	}

	@XmlAttribute
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getHalls() {
		return halls;
	}

	@XmlAttribute
	public void setHalls(String halls) {
		this.halls = halls;
	}

	public String getSeatCounts() {
		return seatCounts;
	}

	@XmlAttribute
	public void setSeatCounts(String seatCounts) {
		this.seatCounts = seatCounts;
	}

	public String getVipFlags() {
		return vipFlags;
	}

	@XmlAttribute
	public void setVipFlags(String vipFlags) {
		this.vipFlags = vipFlags;
	}

	public String getHallNames() {
		return hallNames;
	}

	@XmlAttribute
	public void setHallNames(String hallNames) {
		this.hallNames = hallNames;
	}

	public String getCityName() {
		return cityName;
	}

	@XmlAttribute
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLinkId() {
		return linkId;
	}

	@XmlAttribute
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getStatus() {
		return status;
	}

	@XmlAttribute
	public void setStatus(String status) {
		this.status = status;
	}

	public CinemaOffer() {
	}

	public CinemaOffer(Element element) {
		this.setCinemaId(0);
		this.setOfferCinemaId(element.attributeValue("id"));
		this.setCinemaName(element.attributeValue("name"));
		this.setLinkId(element.attributeValue("linkId"));
		this.setCityName(element.attributeValue("local"));
		this.setStatus(element.attributeValue("status"));
	}
}
