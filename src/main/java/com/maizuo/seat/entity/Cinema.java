package com.maizuo.seat.entity;

/**
 * 影院
 * 
 * @author Administrator
 * 
 */
public class Cinema implements SystemMode {
	private String id;// 唯一id
	private int offerId;// 提供商id
	private String cinemaId;// 提供商影院编号
	private String cinemaLinkId;// 提供商影院连接编码
	private String cinemaName;// 提供商电影院名称
	private String cinemaCity;// 提供商影院所在地区
	private int state;// 影院连接状态1：可用；0:不可用
	private int mzCityId;// 卖座城市id
	private int mzCinemaId;// 卖座影院id
	private int unLockSenconds;// 锁定多少秒失效
	private int ftCloseMinites;// 场次提前多少分钟关闭
	private int balanceFee;// 服务成本价格
	private int displayFlag;// 1-订座退票

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getCinemaCity() {
		return cinemaCity;
	}

	public void setCinemaCity(String cinemaCity) {
		this.cinemaCity = cinemaCity;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getMzCityId() {
		return mzCityId;
	}

	public void setMzCityId(int mzCityId) {
		this.mzCityId = mzCityId;
	}

	public int getMzCinemaId() {
		return mzCinemaId;
	}

	public void setMzCinemaId(int mzCinemaId) {
		this.mzCinemaId = mzCinemaId;
	}

	public int getUnLockSenconds() {
		return unLockSenconds;
	}

	public void setUnLockSenconds(int unLockSenconds) {
		this.unLockSenconds = unLockSenconds;
	}

	public int getFtCloseMinites() {
		return ftCloseMinites;
	}

	public void setFtCloseMinites(int ftCloseMinites) {
		this.ftCloseMinites = ftCloseMinites;
	}

	public int getBalanceFee() {
		return balanceFee;
	}

	public void setBalanceFee(int balanceFee) {
		this.balanceFee = balanceFee;
	}

	public int getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}

	@Override
	public String getListKey() {
		return String.valueOf(offerId);
	}

	@Override
	public String getObjKey() {
		return String.valueOf(id);
	}

}
