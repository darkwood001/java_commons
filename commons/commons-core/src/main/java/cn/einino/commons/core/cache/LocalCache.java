package cn.einino.commons.core.cache;

import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.CacheKey;

public interface LocalCache {

    void initCache();

    <T> T get(CacheKey key) throws CacheException;

    void put(CacheKey key, Object value) throws CacheException;

    void delete(CacheKey key) throws CacheException;

    void cleanUp();
}
