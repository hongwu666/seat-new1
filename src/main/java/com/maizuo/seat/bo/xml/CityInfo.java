package com.maizuo.seat.bo.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class CityInfo {

	/**
	 * 城市id
	 */
	private int cityId;
	/**
	 * 城市名称
	 */
	private String name;

	public int getCityId() {
		return cityId;
	}

	@XmlAttribute
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

}
