package cn.einino.commons.nosql.redis.cache;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.einino.commons.core.cache.RemoteCache;
import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.RemoteCacheKey;
import cn.einino.commons.nosql.model.RedisDatasourceConfig;
import cn.einino.commons.nosql.redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * Created by einino on 2016/11/27.
 */
public class RedisCache implements RemoteCache {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final int DEFAULT_EXPIRE = 5 * 60; //second
    protected final int expire;
    protected final RedisDatasourceConfig dataSourceConf;
    protected final JedisUtil jedisUtil;
    protected final CacheSerializer serializer;

    public RedisCache(int expire, JedisUtil jedisUtil, RedisDatasourceConfig dataSourceConf, CacheSerializer serializer) {
        this.expire = expire;
        this.jedisUtil = jedisUtil;
        this.dataSourceConf = dataSourceConf;
        this.serializer = serializer;
    }

    public RedisCache(JedisUtil jedisUtil, RedisDatasourceConfig dataSourceConf, CacheSerializer serializer) {
        this(DEFAULT_EXPIRE, jedisUtil, dataSourceConf, serializer);
    }

    @Override
    public <T> T get(RemoteCacheKey key, Class<T> clz) throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            T data;
            byte[] srcs = jedis.get(key.getKey().getBytes());
            if (srcs == null || srcs.length < 1) {
                data = key.getListener().onLoad(key);
                if (data == null) {
                    return null;
                }
                put(key, data, clz);
                jedis.set(key.getKey().getBytes(), srcs);
            } else {
                data = serializer.deserialize(srcs, clz);
                jedis.expire(key.getKey(), expire);
            }
            return data;
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache get key:[").append(key.getKey()).append("] error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public <T> List<T> getList(RemoteCacheKey key, Class<T> clz) throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            List<T> list;
            byte[] srcs = jedis.get(key.getKey().getBytes());
            if (srcs == null || srcs.length < 1) {
                list = key.getListener().onLoad(key);
                if (!CollectionUtils.isEmpty(list)) {
                    putList(key, list, clz);
                }
            } else {
                list = serializer.deserializeList(srcs, clz);
                jedis.expire(key.getKey(), expire);
            }
            return list;
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache get key:[").append(key.getKey()).append("] error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public <T> void put(RemoteCacheKey key, T data, Class<T> clz) throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            byte[] srcs = serializer.serialize(data);
            if (srcs == null || srcs.length < 1) {
                throw new CacheException("Redis cache put data error: by srcs is empty");
            }
            Transaction tx = jedis.multi();
            tx.set(key.getKey().getBytes(), srcs);
            tx.expire(key.getKey(), expire);
            tx.exec();
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache put key:[").append(key.getKey()).append("] error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public <T> void putList(RemoteCacheKey key, List<T> datas, Class<T> clz) throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            byte[] srcs = serializer.serializeList(datas, clz);
            if (srcs == null || srcs.length < 1) {
                throw new CacheException("Redis cache put datas error: by srcs is empty");
            }
            Transaction tx = jedis.multi();
            tx.set(key.getKey().getBytes(), srcs);
            tx.expire(key.getKey(), expire);
            tx.exec();
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache put key:[").append(key.getKey()).append("] error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void delete(RemoteCacheKey key) throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            jedis.del(key.getKey());
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache delete key:[").append(key.getKey()).append("] error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void cleanUp() throws CacheException {
        Jedis jedis = jedisUtil.getJedis(dataSourceConf);
        if (jedis == null) {
            throw new CacheException("Redis connection get error");
        }
        try {
            jedis.flushDB();
        } catch (Exception e) {
            String msg = new StringBuilder("Redis cache clean error").toString();
            logger.error(msg, e);
            throw new CacheException(msg, e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
