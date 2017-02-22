package cn.einino.commons.core.concurrent.future;

public interface Future<T> extends java.util.concurrent.Future<T> {

	void set(T value);

	void setCause(Exception cause);

	void addListener(FutureListener<T> listener);

}
