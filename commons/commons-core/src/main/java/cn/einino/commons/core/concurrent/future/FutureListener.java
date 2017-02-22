package cn.einino.commons.core.concurrent.future;

public interface FutureListener<T> {

	void onValue(T value);

	void onException(Exception cause);
}
