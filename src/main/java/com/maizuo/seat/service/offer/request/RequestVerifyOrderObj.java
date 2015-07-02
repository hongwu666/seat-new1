package com.maizuo.seat.service.offer.request;

public class RequestVerifyOrderObj {
	// 排期应用号
	private String offerForetellId;
	// 流水号
	private String thirdPartOrderId;
	// 要支付的余额
	private String balance;
	// 付费类型
	private String payType;
	// 影院会员卡支付交易流水号
	private String paySeqNo;

	// 临时订单号
	private String orderId;
	// 购票数量
	private String ticketCount;
	// 影院编号
	private String cinemaId;
	// 影院连接编码
	private String cinemaLinkId;
	// 影厅编号
	private String hallId;
	// 影片编码
	private String filmId;
	// 场次内部编号
	private String showSeqNo;
	// 场次日期
	private String showDate;
	// 场次时间
	private String showTime;
	// 影票价格列表
	private String prices;
	// 影票手续费列表
	private String fees;
	// 随机串号
	private String randKey;
	// 支付方式
	private String payment;
	// 支付号码
	private String paymentNo;

	public String getOfferForetellId() {
		return offerForetellId;
	}

	public String getThirdPartOrderId() {
		return thirdPartOrderId;
	}

	public String getBalance() {
		return balance;
	}

	public String getPayType() {
		return payType;
	}

	public String getPaySeqNo() {
		return paySeqNo;
	}

	public void setOfferForetellId(String offerForetellId) {
		this.offerForetellId = offerForetellId;
	}

	public void setThirdPartOrderId(String thirdPartOrderId) {
		this.thirdPartOrderId = thirdPartOrderId;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public void setPaySeqNo(String paySeqNo) {
		this.paySeqNo = paySeqNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public String getCinemaLinkId() {
		return cinemaLinkId;
	}

	public String getHallId() {
		return hallId;
	}

	public String getFilmId() {
		return filmId;
	}

	public String getShowSeqNo() {
		return showSeqNo;
	}

	public String getShowDate() {
		return showDate;
	}

	public String getShowTime() {
		return showTime;
	}

	public String getPrices() {
		return prices;
	}

	public String getFees() {
		return fees;
	}

	public String getRandKey() {
		return randKey;
	}

	public String getPayment() {
		return payment;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public void setCinemaLinkId(String cinemaLinkId) {
		this.cinemaLinkId = cinemaLinkId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public void setShowSeqNo(String showSeqNo) {
		this.showSeqNo = showSeqNo;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public void setRandKey(String randKey) {
		this.randKey = randKey;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

}
