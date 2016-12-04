package cn.einino.commons.core.module;

import cn.einino.commons.core.cache.listener.RemoteCacheListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by einino on 2016/12/3.
 */
public class RemoteCacheKey {

    private final String key;
    private final RemoteCacheListener listener;
    private final Map<String, Object> params = new HashMap<>();

    public RemoteCacheKey(String key, RemoteCacheListener listener) {
        this.key = key;
        this.listener = listener;
    }

    public final Map<String, Object> getParams() {
        return params;
    }

    @SuppressWarnings("unchecked")
    public final <T> T getParam(String key) {
        Object obj = params.get(key);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    public final String getKey() {
        return key;
    }

    public final RemoteCacheListener getListener() {
        return listener;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RemoteCacheKey other = (RemoteCacheKey) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CacheKey [key=" + key + "]";
    }
}
