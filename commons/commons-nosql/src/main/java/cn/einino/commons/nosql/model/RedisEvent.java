package cn.einino.commons.nosql.model;

public class RedisEvent {

	private final String channel;
	private final String data;

	public RedisEvent(String channel, String data) {
		this.channel = channel;
		this.data = data;
	}

	public final String getChannel() {
		return channel;
	}

	public final String getData() {
		return data;
	}

}
