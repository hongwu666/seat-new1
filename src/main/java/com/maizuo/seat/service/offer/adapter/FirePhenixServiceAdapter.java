package com.maizuo.seat.service.offer.adapter;

import java.util.List;

import com.maizuo.seat.entity.Order;
import com.maizuo.seat.exception.ServiceException;
import com.maizuo.seat.object.CinemaOffer;
import com.maizuo.seat.object.FilmOffer;
import com.maizuo.seat.object.SeatOffer;
import com.maizuo.seat.object.ShowOffer;
import com.maizuo.seat.service.offer.OfferService1;
import com.maizuo.seat.service.offer.impl.FirePhenixServiceImpl;

public class FirePhenixServiceAdapter extends FirePhenixServiceImpl implements OfferService1 {

	@Override
	public List<CinemaOffer> getCinemas() throws ServiceException {
		return super.getCinemas();
	}

	@Override
	public void lockSeat() throws ServiceException {
		super.lockSeat(null);
	}

	@Override
	public void unlockSeats() throws ServiceException {

	}

	@Override
	public List<FilmOffer> getFilms() throws ServiceException {
		return null;
	}

	@Override
	public List<ShowOffer> getForetell() throws ServiceException {
		return null;
	}

	@Override
	public List<SeatOffer> getSeats() throws ServiceException {
		return null;
	}

	@Override
	public List<SeatOffer> getUsedSeats() throws ServiceException {
		return null;
	}

	@Override
	public Order queryOrder() throws ServiceException {
		return null;
	}

	@Override
	public Order verifyOrder() throws ServiceException {
		return null;
	}

	@Override
	public void init() {

	}

}
