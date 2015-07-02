package com.maizuo.seat.service.offer.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.maizuo.seat.service.offer.OfferInfo;

public class Common {
	protected static AtomicInteger mzCinemaId = new AtomicInteger(1);
	protected OfferInfo offerInfo;

	public OfferInfo getOfferInfo() {
		return offerInfo;
	}

	public void setOfferInfo(OfferInfo offerInfo) {
		this.offerInfo = offerInfo;
	}

}
