package com.maizuo.seat.service.offer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 合作商配置，根据合作商返回合作商英文代码，用于在程序中获取响应的处理对象
 * 
 * @author chenjian
 * 
 */
public class OfferConfig {

	private final static String DEFAULT_OFFER = "firePhenix";
	private static OfferConfig cfg;

	private Map<String, String> offer;

	private Map<String, String> offerInfo;

	private Properties prop;

	public static OfferConfig ins() {
		synchronized (OfferConfig.class) {
			if (cfg == null) {
				cfg = new OfferConfig();
			}
		}
		return cfg;
	}

	private OfferConfig() {
		try {
			initOfferConfig();
			initOfferInfoConfig();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void reload() {
		try {
			initOfferConfig();
			initOfferInfoConfig();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void initOfferConfig() throws IOException {
		prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("offer.properties"));
		offer = new HashMap<String, String>();
		Set<Object> keys = prop.keySet();
		for (Object key : keys) {
			offer.put((String) key, prop.getProperty((String) key));
		}
	}

	private void initOfferInfoConfig() throws IOException {
		prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("offerInfo.properties"));
		offerInfo = new HashMap<String, String>();
		Set<Entry<Object, Object>> entries = prop.entrySet();
		for (Entry<Object, Object> entry : entries) {
			String value = new String(((String) entry.getValue()).getBytes("ISO8859-1"), "utf-8");
			offerInfo.put((String) entry.getKey(), value);
		}
	}

	public String getOfferInfo(String key) {
		return offerInfo.get(key);
	}

	public String getOfferName(int offerId) {
		return getOfferName(String.valueOf(offerId));
	}

	public String getOfferName(String offerId) {
		if (StringUtils.isBlank(offerId)) {
			return DEFAULT_OFFER;
		}
		return offer.containsKey(offerId) ? offer.get(offerId) : DEFAULT_OFFER;
	}
}
