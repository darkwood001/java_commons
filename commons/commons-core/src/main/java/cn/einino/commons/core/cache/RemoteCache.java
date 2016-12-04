package cn.einino.commons.core.cache;

import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.RemoteCacheKey;

import java.util.List;

/**
 * Created by einino on 2016/12/3.
 */
public interface RemoteCache {

    <T> T get(RemoteCacheKey key, Class<T> clz) throws CacheException;

    <T> List<T> getList(RemoteCacheKey key, Class<T> clz) throws CacheException;

    <T> void put(RemoteCacheKey key, T data, Class<T> clz) throws CacheException;

    <T> void putList(RemoteCacheKey key, List<T> datas, Class<T> clz) throws CacheException;

    void delete(RemoteCacheKey key) throws CacheException;

    void cleanUp() throws CacheException;
}
