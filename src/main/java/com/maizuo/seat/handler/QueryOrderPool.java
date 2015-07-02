package com.maizuo.seat.handler;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueryOrderPool {

	private BlockingQueue<QueryOrderObject> queue = new LinkedBlockingQueue<QueryOrderObject>();

	private static QueryOrderPool pool;

	private QueryOrderPool() {

	}

	public static QueryOrderPool getInstance() {
		if (pool == null) {
			pool = new QueryOrderPool();
		}
		return pool;
	}

	public Iterator<QueryOrderObject> all() {
		Iterator<QueryOrderObject> it = queue.iterator();
		return it;
	}

	/**
	 * 带走对象
	 * 
	 * @return
	 */
	public QueryOrderObject take() {
		return queue.poll();
	}

	/**
	 * 添加对象
	 * 
	 * @param queryOrderObject
	 */
	public void add(QueryOrderObject queryOrderObject) {
		this.queue.add(queryOrderObject);
	}

	/**
	 * 移除对象
	 * 
	 * @param queryOrderObject
	 */
	public void remove(QueryOrderObject queryOrderObject) {
		this.queue.remove(queryOrderObject);
	}
}
