package com.maizuo.seat.state.error;

import com.maizuo.seat.service.offer.OfferConfig;

public class ErrorFactory {

	public static Error getError(int offerId, int code) {
		Error error = null;
		String offerName = OfferConfig.ins().getOfferName(offerId);
		switch (offerName) {
		case "zygj":
			error = ZYGJError.getErrorByCode(code);
			break;

		default:
			break;
		}
		return error;
	}

}
