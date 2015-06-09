package com.maizuo.seat.bo.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class CinemaInfo {

	private String cinemaId;// 提供商影院编号
	private String cinemaLinkId;// 提供商影院连接编码
	private String cinemaName;// 提供商电影院名称
	private String cinemaCity;// 提供商影院所在地区
	private String halls;
	private String hallNames;

	public String getCinemaId() {
		return cinemaId;
	}

	@XmlAttribute
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	@XmlAttribute
	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	@XmlAttribute
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getCinemaCity() {
		return cinemaCity;
	}

	@XmlAttribute
	public void setCinemaCity(String cinemaCity) {
		this.cinemaCity = cinemaCity;
	}

	public String getHalls() {
		return halls;
	}
	@XmlAttribute
	public void setHalls(String halls) {
		this.halls = halls;
	}

	public String getHallNames() {
		return hallNames;
	}
	@XmlAttribute
	public void setHallNames(String hallNames) {
		this.hallNames = hallNames;
	}

}
