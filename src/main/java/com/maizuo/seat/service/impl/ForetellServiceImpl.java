package com.maizuo.seat.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maizuo.seat.dao.ForetellDao;
import com.maizuo.seat.entity.Foretell;
import com.maizuo.seat.service.ForetellService;

@Service
public class ForetellServiceImpl implements ForetellService {

	@Autowired
	private ForetellDao foretellDao;

	public String forwarderServer(String bufUrl, HttpServletRequest request) {
		return null;
	}

	@Override
	public Foretell get(String id) {
		return foretellDao.get(id);
	}

}
