package cn.einino.commons.db.template;

import javax.sql.DataSource;

import cn.einino.commons.db.exception.DataBaseTemplateException;

public interface TemplateUtil<T> {

	void initTemlate(DataSource dataSource) throws DataBaseTemplateException;

	T getTemplate();
}
