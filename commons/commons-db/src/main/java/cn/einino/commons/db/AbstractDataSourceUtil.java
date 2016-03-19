package cn.einino.commons.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDataSourceUtil<T extends Object> implements DataSourceUtil<T> {

	protected Logger Log = LoggerFactory.getLogger(getClass());

}
