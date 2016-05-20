package cn.einino.commons.nosql.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.einino.commons.nosql.model.RedisDatasourceConfig;
import cn.einino.commons.nosql.model.RedisEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@SuppressWarnings("rawtypes")
public class JedisSubContext extends JedisPubSub {

	protected final Logger Log = LoggerFactory.getLogger(getClass());
	protected final Map<String, List<BlockingQueue>> queueMap;
	protected final RedisDatasourceConfig dataSourceConfig;
	protected Jedis jedis;

	public JedisSubContext(Map<String, List<BlockingQueue>> queueMap, RedisDatasourceConfig dataSourceConfig) {
		this.queueMap = queueMap;
		this.dataSourceConfig = dataSourceConfig;
	}

	public void initJedisSubContext() {
		jedis = new Jedis(dataSourceConfig.getHost(), dataSourceConfig.getPort());
		jedis.select(dataSourceConfig.getDatabase());
		String[] channels = queueMap.keySet().toArray(new String[0]);
		jedis.subscribe(this, channels);
	}

	@Override
	public void onMessage(String channel, String message) {
		List<BlockingQueue> queues = queueMap.get(channel);
		if (queues != null && !queues.isEmpty()) {
			RedisEvent event = new RedisEvent(channel, message);
			for (BlockingQueue q : queues) {
				produce(q, event);
			}
		} else {
			String msg = new StringBuilder("Channel:[").append(channel).append("] queue list is empty").toString();
			Log.info(msg);
		}
	}

	@SuppressWarnings("unchecked")
	protected void produce(BlockingQueue queue, RedisEvent event) {
		while (!queue.offer(event)) {
			queue.poll();
		}
	}

}
