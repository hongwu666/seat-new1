package com.maizuo.seat.exception;

import com.maizuo.seat.state.error.Error;
import com.maizuo.seat.state.error.ErrorFactory;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// service类名
	private String className;

	// 错误消息
	private String errorMsg = "";

	// 错误代号
	private int errorCode = 0;

	// 结果成功失败
	private boolean result = true;

	public ServiceException(String className, int errorCode, String errorMsg) {
		super();
		this.className = className;
		Error error = ErrorFactory.getError(className, errorCode);
		this.errorMsg = error == null ? errorMsg : error.getComment();
		this.errorCode = errorCode;
		this.result = false;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public boolean isResult() {
		return result;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ServiceException() {
		super();
	}

}
