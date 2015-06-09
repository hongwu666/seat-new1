package com.maizuo.seat.service.offer;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

public class OfferInfo {

	private String url;
	private String account;
	private String password;
	private String key;
	private String type;
	private static OfferInfo info;

	public static OfferInfo ins() {
		synchronized (OfferInfo.class) {
			if (info == null) {
				info = new OfferInfo();
			}
		}
		return info;
	}

	private OfferInfo() {
	}

	public String getUrl() {
		return url;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public OfferInfo get(int offerId) {
		String offerName = OfferConfig.ins().getOfferName(offerId + "");
		OfferInfo offerInfo = new OfferInfo();
		Field[] fields = offerInfo.getClass().getDeclaredFields();
		for (Field field : fields) {
			String key = offerName + "." + field.getName();
			String value = OfferConfig.ins().getOfferInfo(key);
			try {
				if (!StringUtils.isEmpty(value)) {
					field.set(offerInfo, value);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return offerInfo;
	}
}
