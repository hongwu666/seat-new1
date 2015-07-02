package com.maizuo.seat.entity;

import java.util.Date;

public class ThirdCinameRelation implements SystemMode {

	private int id;

	private int thirdId;

	private String cinameId;

	private Date createDate;

	public int getThirdId() {
		return thirdId;
	}

	public void setThirdId(int thirdId) {
		this.thirdId = thirdId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCinameId() {
		return cinameId;
	}

	public void setCinameId(String cinameId) {
		this.cinameId = cinameId;
	}

	@Override
	public String getListKey() {
		return String.valueOf(thirdId);
	}

	@Override
	public String getObjKey() {
		return null;
	}

}
