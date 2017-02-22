package cn.einino.commons.core.concurrent;

import cn.einino.commons.core.concurrent.future.Future;
import cn.einino.commons.core.concurrent.future.FutureListener;

public interface WorkExecutor {

	<T> Future<T> execute(Worker<T> worker);

	<T> Future<T> execute(Worker<T> worker, FutureListener<T> listener);
}
