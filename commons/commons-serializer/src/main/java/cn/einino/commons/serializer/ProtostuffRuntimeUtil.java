package cn.einino.commons.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import cn.einino.commons.serializer.exceptions.SerializeException;

/**
 * Created by einino on 2016/11/18.
 */
public abstract class ProtostuffRuntimeUtil {

	private static final int DEFAULT_LIST_BUFFER_SIZE = 512;
	private static final Logger logger = LoggerFactory.getLogger(ProtostuffRuntimeUtil.class);
	private static final ConcurrentHashMap<Class<?>, RuntimeSchema<?>> schemas = new ConcurrentHashMap<>(16);

	@SuppressWarnings("unchecked")
	private static <T> RuntimeSchema<T> getSchema(Class<?> clz) {
		if (clz == null) {
			logger.error("Get prototstuff schema error: by 'clz' invalid");
			throw new IllegalArgumentException("Get protostuff schema error");
		}
		RuntimeSchema<?> schema = schemas.get(clz);
		if (schema == null) {
			if (logger.isDebugEnabled()) {
				String msg = new StringBuilder("Get class:[").append(clz).append("] protostuff schema").toString();
				logger.debug(msg);
			}
			schema = schemas.putIfAbsent(clz, RuntimeSchema.createFrom(clz));
			if (schema == null) {
				schema = schemas.get(clz);
			}
		}
		return (RuntimeSchema<T>) schema;
	}

	public static <T> byte[] serialize(T obj) throws SerializeException {
		if (obj == null) {
			logger.error("Serialize object error: by 'obj' invalid");
			throw new IllegalArgumentException("Serialize object error");
		}
		if (logger.isDebugEnabled()) {
			String msg = new StringBuilder("Serialize class:[").append(obj.getClass()).append("] object").toString();
			logger.debug(msg);
		}
		RuntimeSchema<T> schema = getSchema(obj.getClass());
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.MIN_BUFFER_SIZE);
		try {
			byte[] buf = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
			return buf;
		} catch (Exception e) {
			String msg = new StringBuilder("Serialize class:[").append(obj.getClass()).append("] object error")
					.toString();
			logger.error(msg, e);
			throw new SerializeException("Serialize object error", e);

		}
	}

	public static <T> T deserialize(byte[] srcs, Class<T> clz) throws SerializeException {
		if (srcs == null || srcs.length == 0 || clz == null) {
			logger.error("Deserialize object error: by 'srcs' or 'clz' invalid");
			throw new IllegalArgumentException("Deserialize object error");
		}
		if (logger.isDebugEnabled()) {
			String msg = new StringBuilder("Deserialize class:[").append(clz).append("] object").toString();
			logger.debug(msg);
		}
		RuntimeSchema<T> schema = getSchema(clz);
		try {
			T instance = clz.newInstance();
			ProtostuffIOUtil.mergeFrom(srcs, instance, schema);
			return instance;
		} catch (Exception e) {
			String msg = new StringBuilder("Deserialize class:[").append(clz).append("] object error").toString();
			logger.error(msg);
			throw new SerializeException("Deserialize object error", e);
		}
	}

	public static <T> byte[] serializeList(List<T> objList, Class<T> clz) throws SerializeException {
		if (CollectionUtils.isEmpty(objList) || clz == null) {
			logger.error("Serialize object list error: by 'objList' or 'clz' invalid");
			throw new IllegalArgumentException("Serialize object list error");
		}
		if (logger.isDebugEnabled()) {
			String msg = new StringBuilder("Serialize class:[").append(clz).append("] object list").toString();
			logger.debug(msg);
		}
		RuntimeSchema<T> schema = getSchema(clz);
		LinkedBuffer buffer = LinkedBuffer.allocate(DEFAULT_LIST_BUFFER_SIZE);
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
			return bos.toByteArray();
		} catch (Exception e) {
			String msg = new StringBuilder("Serialize class:[").append(clz).append("] object list error").toString();
			logger.error(msg, e);
			throw new SerializeException("Serialize object list error", e);
		}
	}

	public static <T> List<T> deserializeList(byte[] srcs, Class<T> clz) throws SerializeException {
		if (srcs == null || srcs.length == 0 || clz == null) {
			logger.error("Deserialize object list error: by 'srcs' or 'clz' invalid");
			throw new IllegalArgumentException("Deserialize object list error");
		}
		if (logger.isDebugEnabled()) {
			String msg = new StringBuilder("Deserialize class:[").append(clz).append("] object list").toString();
			logger.debug(msg);
		}
		RuntimeSchema<T> schema = getSchema(clz);
		try (ByteArrayInputStream bis = new ByteArrayInputStream(srcs)) {
			List<T> list = ProtostuffIOUtil.parseListFrom(bis, schema);
			return list;
		} catch (Exception e) {
			String msg = new StringBuilder("Deserialize class:[").append(clz).append("] object list error").toString();
			logger.error(msg);
			throw new SerializeException("Deserialize object list error", e);
		}
	}
}
