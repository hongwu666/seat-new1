package com.maizuo.seat.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {
	public static String join(List<String> list) {
		List<String> l = new ArrayList<String>();
		for (String s : list) {
			l.add("'" + s + "'");
		}
		return StringUtils.join(l, ",");
	}

	public static String joinInteger(List<Integer> list) {
		List<String> l = new ArrayList<String>();
		for (Integer s : list) {
			l.add(String.valueOf(s));
		}
		return StringUtils.join(l, ",");
	}
}
