package com.maizuo.seat.state.error;

/**
 * 错误描述和代号
 * 
 * @author Administrator
 * 
 */
public enum ZYGJError implements Error {
	第三方BID错误(0) {
		@Override
		public String getComment() {
			return SeatError.第三方BID错误.getComment();
		}
	},
	第三方BID错误1(100) {
		@Override
		public String getComment() {
			return null;
		}
	},
	网线掉落(21) {
		@Override
		public String getComment() {
			return null;
		}
	},
	success1(11) {
		@Override
		public String getComment() {
			return null;
		}
	};

	private int code;

	private ZYGJError(int code) {
		this.code = code;
	}

	public static ZYGJError getErrorByCode(int code) {
		for (ZYGJError m : values()) {
			if (m.code == code) {
				return m;
			}
		}
		return null;
	}

	@Override
	public abstract String getComment();

	public String value() {
		return this.toString();
	}
}
