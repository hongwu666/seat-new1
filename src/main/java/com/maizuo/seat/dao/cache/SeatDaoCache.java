package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemRedisDao;
import com.maizuo.seat.dao.SeatDao;
import com.maizuo.seat.entity.Seat;

public class SeatDaoCache extends BaseSystemRedisDao<Seat> implements SeatDao {

	@Override
	public Seat get(String offerCinemaId, String hallId, String sectionId, String rowId, String columnId) {
		List<Seat> list = this.getList(offerCinemaId + "_" + hallId);

		for (Seat seat : list) {
			if (seat.getSectionId().equals(sectionId) && seat.getRowId().equals(rowId) && seat.getColumnId().equals(columnId)) {
				return seat;
			}
		}
		return null;
	}
}
