package com.maizuo.seat.util;

import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;

public class IDGenerator {

	public static String getID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		NumberUtils.toInt("");
		System.out.println(getID().length());
	}
}
