package cn.einino.commons.core.concurrent;

public interface Worker<T> {

	T work() throws Exception;
}
