package com.maizuo.seat.dao.reload;

public interface ReloadAble {
	
	/**
	 * 初始化
	 */
	public void init();
	/**
	 * 重新加载
	 */
	public void reload();
}
