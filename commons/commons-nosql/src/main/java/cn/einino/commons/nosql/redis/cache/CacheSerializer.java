package cn.einino.commons.nosql.redis.cache;

import java.util.List;

/**
 * Created by einino on 2016/11/27.
 */
public interface CacheSerializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] datas, Class<T> clz);

    <T> byte[] serializeList(List<T> list, Class<T> clz);

    <T> List<T> deserializeList(byte[] datas, Class<T> clz);
}
