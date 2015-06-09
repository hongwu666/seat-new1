package com.maizuo.seat.util;

public class TableUtil {
	public static String getUserTowerFloorTable(int id) {
		long index = id % 10;
		return "user_" + index;
	}

}
