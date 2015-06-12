package com.maizuo.seat.service;

import javax.servlet.http.HttpServletRequest;

import com.maizuo.seat.entity.Foretell;

public interface ForetellService {

	public String forwarderServer(String bufUrl, HttpServletRequest request);

	public Foretell get(String id);
}
