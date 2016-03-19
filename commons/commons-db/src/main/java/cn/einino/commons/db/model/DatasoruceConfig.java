package cn.einino.commons.db.model;

public class DatasoruceConfig {

	private String driverClass;
	private String jdbcUrl;
	private String user;
	private String password;
	private int maxPoolSize;
	private int minPoolSize;
	private int maxIdleTime;
	private int acquireRetryAttempts = 0;
	private String preferredTestQuery = "select 1";
	private int idleConnectionTestPeriod;

	public DatasoruceConfig() {
	}

	public final String getDriverClass() {
		return driverClass;
	}

	public final void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public final String getJdbcUrl() {
		return jdbcUrl;
	}

	public final void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public final String getUser() {
		return user;
	}

	public final void setUser(String user) {
		this.user = user;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final int getMaxPoolSize() {
		return maxPoolSize;
	}

	public final void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public final int getMinPoolSize() {
		return minPoolSize;
	}

	public final void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public final int getMaxIdleTime() {
		return maxIdleTime;
	}

	public final void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public final int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}

	public final void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

	public final String getPreferredTestQuery() {
		return preferredTestQuery;
	}

	public final void setPreferredTestQuery(String preferredTestQuery) {
		this.preferredTestQuery = preferredTestQuery;
	}

	public final int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	public final void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

}
