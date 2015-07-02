package com.maizuo.seat.util;

import java.util.ArrayList;
import java.util.List;

public class SimpleChain {

	private List<SimpleObject> list = new ArrayList<SimpleObject>();

	public SimpleChain add(SimpleObject obj) {
		list.add(obj);
		return this;
	}

	public List<SimpleObject> getAll() {
		return list;
	}

	public Object get(String key) {
		for (SimpleObject obj : list) {
			if (obj.getKey().equals(key)) {
				return obj.getValue();
			}
		}
		return null;
	}
}
