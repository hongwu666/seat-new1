package com.maizuo.seat.dao.cache;

import java.util.List;

import com.maizuo.seat.dao.BaseSystemCacheDao;
import com.maizuo.seat.dao.ThirdCinameRelationDao;
import com.maizuo.seat.entity.ThirdCinameRelation;

public class ThirdCinameRelationDaoCache extends BaseSystemCacheDao<ThirdCinameRelation> implements ThirdCinameRelationDao {

	public List<ThirdCinameRelation> getList(int thirdId) {
		return super.getList(String.valueOf(thirdId));
	}
}
