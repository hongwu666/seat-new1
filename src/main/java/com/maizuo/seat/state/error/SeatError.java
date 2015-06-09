package com.maizuo.seat.state.error;

public enum SeatError implements Error {
	/**
	 * 网络异常
	 */
	第三方BID错误(2);
	private int code;

	private SeatError(int code) {
		this.code = code;
	}
	public int getCode(){
		return code;
	}
	public String getComment() {
		return this.toString();
	}

}
