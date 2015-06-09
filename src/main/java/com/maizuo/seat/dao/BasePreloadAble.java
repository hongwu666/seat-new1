package com.maizuo.seat.dao;

import com.maizuo.seat.dao.reload.ReloadAble;
import com.maizuo.seat.dao.reload.ReloadManager;

public abstract class BasePreloadAble implements ReloadAble {

	@Override
	public void init() {
		initDate();
		ReloadManager.inc().register(getClass().getSimpleName(), this);
	}

	@Override
	public void reload() {
		init();
	}

	public abstract void initDate();
}
