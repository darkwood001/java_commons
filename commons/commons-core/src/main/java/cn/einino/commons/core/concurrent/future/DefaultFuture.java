package cn.einino.commons.core.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DefaultFuture<T> extends AbstractFuture<T> {

	protected final Object lock = new Object();
	protected T value;
	protected Exception cause;
	protected FutureListener<T> listener;
	protected volatile boolean cancel = false;
	protected volatile boolean done = false;

	public DefaultFuture() {
	}

	public DefaultFuture(FutureListener<T> listener) {
		this.listener = listener;
	}

	@Override
	public void set(T value) {
		synchronized (lock) {
			this.value = value;
			done = true;
			if (listener != null) {
				listener.onValue(value);
			}
			lock.notifyAll();
		}
	}

	@Override
	public void setCause(Exception cause) {
		synchronized (lock) {
			this.cause = cause;
			done = true;
			if (listener != null) {
				this.listener.onException(cause);
			}
			lock.notifyAll();
		}
	}

	@Override
	public void addListener(FutureListener<T> listener) {
		synchronized (lock) {
			this.listener = listener;
			if (this.listener != null) {
				if (cause != null) {
					this.listener.onException(cause);
				} else if (value != null) {
					this.listener.onValue(value);
				}
			}
		}
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		if (cause != null) {
			throw new ExecutionException(cause);
		}
		if (value != null) {
			return value;
		}
		return awaitDone(0L);
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		if (cause != null) {
			throw new ExecutionException(cause);
		}
		if (value != null) {
			return value;
		}
		Long time = 0L;
		if (timeout > 0) {
			time = TimeUnit.MILLISECONDS.convert(timeout, unit);
		}
		return awaitDone(time);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (isDone() || isCancelled()) {
			return false;
		}
		synchronized (lock) {
			if (isDone() || isCancelled()) {
				return false;
			}
			cancel = mayInterruptIfRunning;
			while (!isDone()) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					logger.error("Cancel worker wait interrupted");
				}
			}
			return true;
		}
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	protected T awaitDone(Long timeout) throws InterruptedException, ExecutionException {
		synchronized (lock) {
			if (value == null && cause == null) {
				if (timeout == null || timeout < 1) {
					lock.wait();
				} else {
					lock.wait(timeout);
				}
			}
		}
		if (cause != null) {
			throw new ExecutionException(cause);
		}
		return value;
	}
}
