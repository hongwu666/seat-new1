package com.maizuo.seat.helper;

import com.maizuo.seat.util.UrlRequestUtils;

public class RegetPaiQiThread extends Thread {
	private String url;

	public RegetPaiQiThread(String url) {
		this.url = url;
	}

	public void run() {
		UrlRequestUtils.execute(url, null, "get");
	}
}
