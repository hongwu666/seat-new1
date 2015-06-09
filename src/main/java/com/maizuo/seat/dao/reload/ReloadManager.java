package com.maizuo.seat.dao.reload;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class ReloadManager {

	private static ReloadManager reloadManager = null;

	private Map<String, ReloadAble> map = new HashMap<String, ReloadAble>();

	private ReloadManager() {

	}

	public static ReloadManager inc() {
		if (reloadManager == null) {
			reloadManager = new ReloadManager();
		}
		return reloadManager;
	}

	public void register(String className, ReloadAble reloadAble) {
		map.put(className, reloadAble);
	}

	public void reload(String className) {

		Iterator<Entry<String, ReloadAble>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			Entry<String, ReloadAble> entry = it.next();

			if (!StringUtils.equalsIgnoreCase("ALL", className) && !StringUtils.equalsIgnoreCase(className, entry.getKey())) {
				continue;
			}
			ReloadAble reloadAble = entry.getValue();
			reloadAble.reload();
		}
	}
}
