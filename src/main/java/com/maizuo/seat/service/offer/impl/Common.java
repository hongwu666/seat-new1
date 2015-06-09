package com.maizuo.seat.service.offer.impl;

import java.util.Map;

import com.maizuo.seat.service.offer.OfferInfo;

public class Common {

	/**
	 * 错误消息
	 */
	protected String errorMsg = "";
	/**
	 * 错误代号
	 */
	protected int errorCode = 0;

	/**
	 * 结果成功失败
	 */
	protected boolean result = true;

	protected OfferInfo offerInfo;

	public String getErrorMsg() {
		return errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public OfferInfo getOfferInfo() {
		return offerInfo;
	}

	public void setOfferInfo(OfferInfo offerInfo) {
		this.offerInfo = offerInfo;
	}

	/**
	 * 检查结果信息是否正确
	 * 
	 * @param map
	 * @return
	 */
	public boolean check(Map<String, Object> map) {
		if ((Integer) map.get("ERROR_CODE") > 0) {
			this.errorCode = (Integer) map.get("ERROR_CODE");
			this.errorMsg = (String) map.get("ERROR_MSG");
			this.result = false;
			return false;
		}
		return true;
	}

	public void reset() {
		errorCode = 0;
		errorMsg = "";
		result = true;
	}
}
