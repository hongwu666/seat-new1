package com.maizuo.seat.asyncdb;

import java.util.List;

public class AsyncDbModel<T> {

	private String table;

	private List<T> list;

	private T t;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

}
