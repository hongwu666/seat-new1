package com.maizuo.seat.bo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Result")
public class CinemaBO {

	private int status;

	private String resultMsg;

	private List<CinemaInfo> cinemas;

	private List<CityInfo> citys;

	public int getStatus() {
		return status;
	}

	@XmlElement
	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	@XmlElement
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public List<CinemaInfo> getCinemas() {
		return cinemas;
	}

	@XmlElementWrapper(name = "cinemas")
	@XmlElement(name = "cinema")
	public void setCinemas(List<CinemaInfo> cinames) {
		this.cinemas = cinames;
	}

	public List<CityInfo> getCitys() {
		return citys;
	}

	@XmlElementWrapper(name = "citys")
	@XmlElement(name = "city")
	public void setCitys(List<CityInfo> citys) {
		this.citys = citys;
	}

	public CinemaBO() {
	}

}
