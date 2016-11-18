package cn.einino.commons.nosql.model;

import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolConfig extends RedisDatasourceConfig {

    private String key = "default";
    private JedisPoolConfig poolConfig = new JedisPoolConfig();
    private int timeout = 60 * 1000;

    public RedisPoolConfig() {
        poolConfig.setMaxTotal(32);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(8);
        poolConfig.setMaxWaitMillis(60 * 1000);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(true);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public final void setMaxTotal(int maxTotal) {
        poolConfig.setMaxTotal(maxTotal);
    }

    public final void setMaxIdle(int maxIdle) {
        poolConfig.setMaxIdle(maxIdle);
    }

    public final void setMinIdle(int minIdle) {
        poolConfig.setMinIdle(minIdle);
    }

    public final void setMaxWait(long maxWait) {
        poolConfig.setMaxWaitMillis(maxWait);
    }

    public final void setTestOnBorrow(boolean testOnBorrow) {
        poolConfig.setTestOnBorrow(testOnBorrow);
    }

    public final void setTestOnReturn(boolean testOnReturn) {
        poolConfig.setTestOnReturn(testOnReturn);
    }

    public final void setLifo(boolean lifo) {
        poolConfig.setLifo(lifo);
    }

    public final void setMinEvictableIdleTimeMillis(long millis) {
        poolConfig.setMinEvictableIdleTimeMillis(millis);
    }

    public final void setNumTestsPerEvictionRun(int num) {
        poolConfig.setNumTestsPerEvictionRun(num);
    }

    public final void setTestWhileIdle(boolean testWhileIdle) {
        poolConfig.setTestWhileIdle(testWhileIdle);
    }

    public final void setTimeBetweenEvcitionRunsMillis(long millis) {
        poolConfig.setTimeBetweenEvictionRunsMillis(millis);
    }

    public final int getTimeout() {
        return timeout;
    }

    public final void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
