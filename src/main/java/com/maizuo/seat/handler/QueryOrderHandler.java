package com.maizuo.seat.handler;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.maizuo.seat.entity.Order;
import com.maizuo.seat.factory.OfferServiceFactory;
import com.maizuo.seat.service.offer.OfferService;

public class QueryOrderHandler {
	@Autowired
	private ThreadPoolTaskExecutor asyncTaskExecutor;
	@Autowired
	private OfferServiceFactory offerServiceFactory;

	private final static long TEN_MINUTE = 10 * 60 * 1000;

	private final static long TEN_SENCOND = 10 * 1000;

	/**
	 * 移除查询十分钟的对象
	 * 
	 * @param queryOrderObject
	 */
	public void remove(QueryOrderObject queryOrderObject) {
		QueryOrderPool.getInstance().remove(queryOrderObject);
	}

	/**
	 * 处理对象
	 * 
	 * @param model
	 */
	public void handle(final QueryOrderObject queryOrderObject) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				_handle(queryOrderObject);
			}
		};
		asyncTaskExecutor.execute(runnable);
	}

	public void _handle(QueryOrderObject queryOrderObject) {
		OfferService offerService = offerServiceFactory.getBean(queryOrderObject.getOfferId());
		// 查询订单
		Order order = offerService.queryOrder(queryOrderObject.getObj());
		// 订单落地成功
		if (order.getStatus() == 0) {
			System.out.println("成功" + queryOrderObject.getTime() + offerService.getClass().getSimpleName());
			remove(queryOrderObject);
			return;
		}
		System.out.println("失败" + queryOrderObject.getTime() + offerService.getClass().getSimpleName());
	}

	public void init() {
		new Thread(new WorkThread()).start();
	}

	class WorkThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Iterator<QueryOrderObject> it = QueryOrderPool.getInstance().all();
					while (it.hasNext()) {
						QueryOrderObject queryOrderObject = it.next();
						if (System.currentTimeMillis() - queryOrderObject.getTime() > TEN_MINUTE) {
							remove(queryOrderObject);
							continue;
						}
						handle(queryOrderObject);
					}
					// 每十秒钟调一次
					Thread.sleep(TEN_SENCOND);
				} catch (Exception e) {
					try {
						// 每十秒钟调一次
						Thread.sleep(TEN_SENCOND);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}
	}
}
