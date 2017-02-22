package cn.einino.commons.core.concurrent;

import java.util.concurrent.Executor;

import cn.einino.commons.core.concurrent.future.DefaultFuture;
import cn.einino.commons.core.concurrent.future.Future;
import cn.einino.commons.core.concurrent.future.FutureListener;

public class PooledWorkExecutor extends AbstractWorkExecutor implements WorkExecutor {

	protected final Executor executor;

	public PooledWorkExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public <T> Future<T> execute(Worker<T> worker) {
		Future<T> future = new DefaultFuture<>();
		execute(worker, future);
		return future;
	}

	@Override
	public <T> Future<T> execute(Worker<T> worker, FutureListener<T> listener) {
		Future<T> future = new DefaultFuture<>(listener);
		execute(worker, future);
		return future;
	}

	protected <T> void execute(final Worker<T> worker, final Future<T> future) {
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					T value = worker.work();
					future.set(value);
				} catch (Exception e) {
					logger.error("Pool worker executor worker error: ", e);
					future.setCause(e);
				}
			}
		};
		if (executor != null) {
			executor.execute(task);
		} else {
			new Thread(task).start();
		}
	}

}
