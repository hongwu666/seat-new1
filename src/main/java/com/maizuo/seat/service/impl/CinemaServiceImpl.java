package com.maizuo.seat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maizuo.seat.bo.xml.CinemaBO;
import com.maizuo.seat.bo.xml.CinemaInfo;
import com.maizuo.seat.bo.xml.CityInfo;
import com.maizuo.seat.dao.CinemaDao;
import com.maizuo.seat.dao.HallsDao;
import com.maizuo.seat.dao.ThirdCinameRelationDao;
import com.maizuo.seat.entity.Cinema;
import com.maizuo.seat.entity.Halls;
import com.maizuo.seat.entity.ThirdCinameRelation;
import com.maizuo.seat.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	private CinemaDao cinemaDao;
	@Autowired
	private ThirdCinameRelationDao thirdCinameRelationDao;
	@Autowired
	private HallsDao hallsDao;

	@Override
	public CinemaBO getCinemas(int thirdId, String datetime, String sign) {
		// offerService = OfferFactory.getOfferService(offerId);
		// offerService.getCinemas();
		long start = System.currentTimeMillis();
		CinemaBO bo = new CinemaBO();
		List<ThirdCinameRelation> relations = this.thirdCinameRelationDao.getList(thirdId);
		List<CinemaInfo> cinemas = new ArrayList<>();
		List<CityInfo> citys = new ArrayList<>();
		StringBuffer bufcode = new StringBuffer();
		StringBuffer bufName = new StringBuffer();
		for (ThirdCinameRelation relation : relations) {
			int cinemaId = relation.getCinameId();
			Cinema cinema = null;
			// this.cinemaDao.get(cinemaId);
			List<Halls> halls = this.hallsDao.getList(Integer.valueOf(cinema.getId()));
			CinemaInfo cinemaInfo = new CinemaInfo();
			CityInfo cityInfo = new CityInfo();
			bufcode.setLength(0);
			bufName.setLength(0);
			for (Halls hall : halls) {
				bufcode.append(hall.getCode() + "-");
				bufName.append(hall.getName() + "-");
			}
			cinemaInfo.setHalls(bufcode.toString());
			cinemaInfo.setHallNames(bufName.toString());
			cityInfo.setCityId(cinema.getMzCityId());
			cityInfo.setName(cinema.getCinemaCity());
			BeanUtils.copyProperties(cinema, cinemaInfo);
			citys.add(cityInfo);
			cinemas.add(cinemaInfo);
		}
		bo.setCinemas(cinemas);
		bo.setCitys(citys);
		long end = System.currentTimeMillis();
		System.err.print(end - start);
		return bo;
	}

	@Override
	public Cinema getCinema(int offerId, int mzCinemaId) {

		return this.cinemaDao.get(offerId, mzCinemaId);
	}
}
