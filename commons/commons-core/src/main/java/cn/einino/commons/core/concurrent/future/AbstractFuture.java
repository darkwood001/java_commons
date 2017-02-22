package cn.einino.commons.core.concurrent.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFuture<T> implements Future<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
