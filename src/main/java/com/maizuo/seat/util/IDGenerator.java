package com.maizuo.seat.util;

import java.util.UUID;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;

public class IDGenerator {

	public static String getID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		org.apache.commons.lang.math.NumberUtils.toInt("");
		System.out.println(getID().length());
	}
}
