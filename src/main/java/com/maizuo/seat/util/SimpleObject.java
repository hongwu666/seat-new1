package com.maizuo.seat.util;

public class SimpleObject {

	private String key;

	private Object value;

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setKV(String key, Object value) {
		this.setKey(key);
		this.setValue(value);
	}

	public SimpleObject(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public SimpleObject() {
		super();
	}

}
