package cn.einino.commons.core.cache.listener;

import cn.einino.commons.core.exception.CacheException;
import cn.einino.commons.core.module.RemoteCacheKey;

/**
 * Created by einino on 2016/12/3.
 */
public interface RemoteCacheListener {

    <T> T onLoad(RemoteCacheKey key) throws CacheException;
}
