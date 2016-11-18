package cn.einino.commons.nosql.redis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.einino.commons.nosql.model.RedisDatasourceConfig;
import cn.einino.commons.nosql.model.RedisPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Object lock = new Object();
    private volatile Map<String, JedisPool> pools = Collections.synchronizedMap(new HashMap<String, JedisPool>());

    public Jedis getJedis(RedisDatasourceConfig config) {
        Jedis jedis;
        if (config instanceof RedisPoolConfig) {
            jedis = getJedisFromPool((RedisPoolConfig) config);
        } else {
            logger.info("Create single jedis");
            jedis = new Jedis(config.getHost(), config.getPort());
        }
        if (jedis != null) {
            jedis.select(config.getDatabase());
        }
        return jedis;
    }

    protected Jedis getJedisFromPool(RedisPoolConfig config) {
        Jedis jedis = null;
        JedisPool pool = getJedisPool(config);
        if (pool != null) {
            jedis = pool.getResource();
        }
        return jedis;
    }

    protected JedisPool getJedisPool(RedisPoolConfig config) {
        JedisPool pool = pools.get(config.getKey());
        if (pool == null) {
            synchronized (lock) {
                pool = pools.get(config.getKey());
                if (pool == null) {
                    String msg = new StringBuilder("Create key:[").append(config.getKey()).append("] jedis poole").toString();
                    logger.info(msg);
                    if (config.getPassword() != null && !"".equals(config.getPassword())) {
                        pool = new JedisPool(config.getPoolConfig(), config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
                    } else {
                        pool = new JedisPool(config.getPoolConfig(), config.getHost(), config.getPort(), config.getTimeout());
                    }
                    pools.put(config.getKey(), pool);
                }
            }
        }
        return pool;
    }
}
