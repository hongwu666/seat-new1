package com.maizuo.seat.util;

public class JedisKey {

	public static String getUserKey(int id) {
		return "user" + "_" + id;
	}

	/**
	 * 获取提供商城市列表key
	 * 
	 * @param offerId
	 * @return
	 */
	public static String getCitysKey(int offerId) {
		return "Citys" + "_" + offerId;
	}

	/**
	 * 获得提供商影院列表key
	 * 
	 * @param offerId
	 * @return
	 */
	public static String getCinemasKey(int offerId) {
		return "Cinemas" + "_" + offerId;
	}

	/**
	 * 获得提供商影院列表key
	 * 
	 * @param offerId
	 * @return
	 */
	public static String getCinemas() {
		return "Cinemas";
	}

	/**
	 * 影院id-提供商key
	 */
	public static String mzCinma_offer = "mzCinma_offer";
}
