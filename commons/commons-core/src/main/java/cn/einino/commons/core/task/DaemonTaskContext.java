package cn.einino.commons.core.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DaemonTaskContext {

	protected final ExecutorService executor = Executors.newCachedThreadPool();
	protected volatile Map<String, Future<?>> futures = Collections.synchronizedMap(new HashMap<String, Future<?>>());

	public void submit(String name, Runnable task) {
		Future<?> future = executor.submit(task);
		futures.put(name, future);
	}

	public void finishTask(String name) {
		Future<?> future = futures.remove(name);
		if (future != null) {
			future.cancel(false);
		}
	}

	public void finishContext() {
		executor.shutdown();
	}
}
