package com.maizuo.seat.state.error;

public class ErrorFactory {

	public static Error getError(String className, int code) {
		switch (className) {
		case "FirePhenixServiceImpl":
			return FirePhenixError.getErrorByCode(code);
		case "SkyStarServiceImpl":
			return SkyStarError.getErrorByCode(code);
		default:
			return SkyStarError.getErrorByCode(code);
		}
	}

}
