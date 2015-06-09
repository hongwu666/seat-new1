package com.maizuo.seat.entity;

import java.util.Date;

public class Offer {

	private int id;//提供商id
	private String name;//提供商名称
	private Date date;//创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
