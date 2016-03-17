package cn.einino.commons.nosql.model;

import redis.clients.jedis.Protocol;

public class RedisDatasourceConfig {

	private String host;
	private int port = 6379;
	private int database = Protocol.DEFAULT_DATABASE;

	public RedisDatasourceConfig() {
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final int getDatabase() {
		return database;
	}

	public final void setDatabase(int database) {
		this.database = database;
	}

}
