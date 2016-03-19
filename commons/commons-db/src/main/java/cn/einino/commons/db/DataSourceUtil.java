package cn.einino.commons.db;

import java.util.List;

import cn.einino.commons.db.template.TemplateUtil;

public interface DataSourceUtil<T extends Object> {

	void initDatasource();

	void initDatasource(T config);

	void addDataBaseTemplateUtils(List<TemplateUtil<?>> utils);

	void closeDatasource();
}
