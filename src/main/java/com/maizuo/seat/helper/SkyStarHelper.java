package com.maizuo.seat.helper;

import com.maizuo.seat.entity.Order;

public class SkyStarHelper {
	//public int getOrderPrice(int balance_price, Show sh, boolean isAct) {
	/*
		int lowest_price = balance_price;
		int op = mm.getFaceOrderPrice(sh.getOfferCinemaId(), sh.getFilmId() + "", sh.getId(), isAct, false);
		if (op == 0) {
			op = balance_price;
		} else if (op < 0) {
			lowest_price = mm.getLowstPrice(sh.getOfferCinemaId(), sh.getFilmId() + "", sh.getOfferId());
			if (lowest_price == 0) {
				lowest_price = balance_price;
			}
			op = lowest_price;
		}
		if (op <= 0) {
			op = balance_price;
		}
		return op;
	*/
//}

	public static void setOrderMsgByOrderStatus(Order order) {
		int status;
		String message;
		switch (order.getStatus()) {
		case 4:
			order.setStatus(-1);
			order.setMessage("支付失败");
			break;
		case 6:
			order.setStatus(0);
			order.setMessage("已支付");
			break;
		case 7:
			order.setStatus(2);
			order.setMessage("已退票");
			break;
		case 8:
			order.setStatus(3);
			order.setMessage("已打票");
			break;
		case 9:
			order.setStatus(4);
			order.setMessage("地面售票成功");
			break;
		default:
			order.setStatus(0);
			order.setMessage("已支付");
			break;
		}
		
	}
}
