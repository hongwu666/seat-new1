package com.maizuo.seat.asyncdb;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.maizuo.common.jdbc.JdbcImpl;

public class AsyncDbHandler {

	@Autowired
	private JdbcImpl jdbcImpl;

	@Autowired
	private ThreadPoolTaskExecutor asyncTaskExecutor;

	public void handle(final AsyncDbModel<?> model) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				_handle(model);
			}
		};
		asyncTaskExecutor.execute(runnable);
	}

	public void _handle(AsyncDbModel<?> model) {
		this.jdbcImpl.insert(model.getTable(), model.getT());
	}

	public void init() {
		new Thread(new WorkThread()).start();
	}

	class WorkThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					AsyncDbModel<?> model = AsyncDbPool.getInstance().take();
					if (model != null) {
						handle(model);
					}

				} catch (InterruptedException e) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
		}

	}
}
