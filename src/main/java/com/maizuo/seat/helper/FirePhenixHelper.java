package com.maizuo.seat.helper;

import com.maizuo.seat.entity.Order;

public class FirePhenixHelper {
	/**
	 * 把传入的价格列表规范化
	 * 
	 * @param str
	 * @return
	 */
	public static String che(String str) {
		String[] arr = str.split("\\|");
		str = "";

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].indexOf(".") < 0) {
				str += "|" + (Double.parseDouble(arr[i]) / 100);
			} else {
				str += "|" + (Double.parseDouble(arr[i]));
			}
		}

		if (str.length() > 0) {
			str = str.substring(1);
		}
		return str;
	}

	public static void setOrderMsgByOrderStatus(Order order) {
		if (order.getStatus() == 0) {
			order.setStatus(1);
			order.setMessage("交易失败");
		} else if (order.getStatus() == 1) {
			order.setStatus(0);
			order.setMessage("交易成功");
		} else if (order.getStatus() == 2) {
			order.setStatus(5);
			order.setMessage("交易失败，座位释放.");
		}
	}
}
