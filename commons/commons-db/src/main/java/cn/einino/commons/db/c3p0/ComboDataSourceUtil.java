package cn.einino.commons.db.c3p0;

import java.beans.PropertyVetoException;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.einino.commons.db.AbstractDataSourceUtil;
import cn.einino.commons.db.exception.DataBaseTemplateException;
import cn.einino.commons.db.model.DatasoruceConfig;
import cn.einino.commons.db.template.TemplateUtil;

public class ComboDataSourceUtil extends AbstractDataSourceUtil<DatasoruceConfig> {

	protected ComboPooledDataSource dataSource;
	protected DatasoruceConfig datasoruceConfig;
	protected List<TemplateUtil<?>> utils;

	public ComboDataSourceUtil(DatasoruceConfig config) {
		this.datasoruceConfig = config;
	}

	@Override
	public void initDatasource() {
		initDatasource(datasoruceConfig);
	}

	@Override
	public void initDatasource(DatasoruceConfig config) {
		ComboPooledDataSource source = new ComboPooledDataSource();
		try {
			source.setDriverClass(config.getDriverClass());
			source.setJdbcUrl(config.getJdbcUrl());
			source.setUser(config.getUser());
			source.setPassword(config.getPassword());
			source.setMaxPoolSize(config.getMaxPoolSize());
			source.setMinPoolSize(config.getMinPoolSize());
			source.setMaxIdleTime(config.getMaxIdleTime());
			source.setAcquireRetryAttempts(config.getAcquireRetryAttempts());
			source.setPreferredTestQuery(config.getPreferredTestQuery());
			source.setIdleConnectionTestPeriod(config.getIdleConnectionTestPeriod());
			closeDatasource();
			dataSource = source;
			initTemplateUtils();
			datasoruceConfig = config;
		} catch (PropertyVetoException e) {
			String msg = new StringBuilder("Init data source error").toString();
			Log.error(msg, e);
		}
	}

	@Override
	public void addDataBaseTemplateUtils(List<TemplateUtil<?>> utils) {
		this.utils = utils;
	}

	@Override
	public void closeDatasource() {
		if (dataSource != null) {
			dataSource.close();
		}
		dataSource = null;
	}

	protected void initTemplateUtils() {
		if (utils == null || utils.size() < 1) {
			return;
		}
		for (TemplateUtil<?> util : utils) {
			try {
				util.initTemlate(dataSource);
			} catch (DataBaseTemplateException e) {
				Log.error("Init template error", e);
			}
		}
	}

}
