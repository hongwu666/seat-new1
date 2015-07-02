package com.maizuo.seat.state.error;

public enum SeatError implements Error {
	第三方BID错误, 该座位已被锁定,调用过程发生异常,影厅座位不存在;
	@Override
	public String getComment() {
		return this.toString();
	}

	@Override
	public String value() {
		return this.toString();
	}
}