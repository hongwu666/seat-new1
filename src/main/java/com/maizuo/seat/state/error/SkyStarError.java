package com.maizuo.seat.state.error;

public enum SkyStarError implements Error {
	影厅对应的座位不存在(1) {
		@Override
		public String getComment() {
			return SeatError.影厅座位不存在.getComment();
		}
	},
	调用过程发生异常(100500) {
		@Override
		public String getComment() {
			return SeatError.调用过程发生异常.getComment();
		}
	},
	网线掉落(21) {
		@Override
		public String getComment() {
			return null;
		}
	},
	参数校验信息错误(100101) {
		@Override
		public String getComment() {
			return null;
		}
	},

	XML参数解释错误(100102) {
		@Override
		public String getComment() {
			return null;
		}
	},
	地面连接数量已满或不存在此地面链接(100103) {
		@Override
		public String getComment() {
			return null;
		}
	},
	应用无权限(100104) {
		@Override
		public String getComment() {
			return null;
		}
	},
	令牌失效(100105) {
		@Override
		public String getComment() {
			return null;
		}
	},
	该座位不可售(100106) {
		@Override
		public String getComment() {
			return null;
		}
	},
	流水号已经存在(101106) {
		@Override
		public String getComment() {
			return null;
		}
	},
	座位数不够(101104) {
		@Override
		public String getComment() {
			return SeatError.该座位已被锁定.getComment();
		}
	},
	中心解锁失败(101209) {
		@Override
		public String getComment() {
			return null;
		}
	},
	;
	private int code;

	private SkyStarError(int code) {
		this.code = code;
	}

	public static SkyStarError getErrorByCode(int code) {
		for (SkyStarError m : values()) {
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
