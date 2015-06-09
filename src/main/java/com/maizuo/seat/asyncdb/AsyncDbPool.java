package com.maizuo.seat.asyncdb;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class AsyncDbPool {

	private BlockingQueue<AsyncDbModel<?>> queue = new LinkedBlockingDeque<AsyncDbModel<?>>();

	private static AsyncDbPool pool;

	private AsyncDbPool() {

	}

	public static AsyncDbPool getInstance() {
		if (pool == null) {
			pool = new AsyncDbPool();
		}
		return pool;
	}

	public AsyncDbModel<?> take() throws InterruptedException {
		return queue.take();
	}

	public void add(AsyncDbModel<?> model) {
		queue.add(model);
	}
}
