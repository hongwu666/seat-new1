package com.maizuo.seat.factory;

import com.hyx.zookeeper.MaizuoConfig;
import com.maizuo.seat.constant.Constant;

public class ServiceFactory {

	public static String getValue(String key) {
		MaizuoConfig serverConfig = MaizuoConfig.getInstance();
		String msg = "";

		try {
			msg = serverConfig.getConfig().getStringByFullName("/seatSplitFlow1/" + key);
			if (msg == null) {
				msg = Constant.DEFAULT_URL;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
}
