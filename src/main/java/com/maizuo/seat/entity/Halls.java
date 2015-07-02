package com.maizuo.seat.entity;

public class Halls extends DefaultSystemRedisMode<Halls> {
	private int id;
	private String cinameId;
	private int code;
	private String name;
	private String updateHallNames;
	private int vipFlag;
	private int seatNum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCinameId() {
		return cinameId;
	}

	public void setCinameId(String cinameId) {
		this.cinameId = cinameId;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdateHallNames() {
		return updateHallNames;
	}

	public void setUpdateHallNames(String updateHallNames) {
		this.updateHallNames = updateHallNames;
	}

	public int getVipFlag() {
		return vipFlag;
	}

	public void setVipFlag(int vipFlag) {
		this.vipFlag = vipFlag;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	@Override
	public String getListKey() {
		return cinameId;
	}

	@Override
	public String getObjKey() {
		return String.valueOf(id);
	}

}
