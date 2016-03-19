package cn.einino.commons.db.template.mybatis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;

import cn.einino.commons.db.exception.DataBaseTemplateException;
import cn.einino.commons.db.template.TemplateUtil;

public class MybatisTemplateUtil implements TemplateUtil<SqlSessionTemplate> {

	protected SqlSessionTemplate template;
	protected final Resource configLocation;
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();

	public MybatisTemplateUtil(Resource configLocation) {
		this.configLocation = configLocation;
	}

	@Override
	public void initTemlate(DataSource dataSource) throws DataBaseTemplateException {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(configLocation);
		try {
			SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(factoryBean.getObject());
			try {
				lock.writeLock().lock();
				template = sessionTemplate;
			} finally {
				lock.writeLock().unlock();
			}
		} catch (Exception e) {
			throw new DataBaseTemplateException("Init template error", e);
		}
	}

	@Override
	public SqlSessionTemplate getTemplate() {
		SqlSessionTemplate sessionTemplate;
		try {
			lock.readLock().lock();
			sessionTemplate = template;
		} finally {
			lock.readLock().unlock();
		}
		return sessionTemplate;
	}

}
