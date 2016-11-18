package cn.einino.commons.serializer.exceptions;

/**
 * Created by einino on 2016/11/18.
 */
public class SerializeException extends RuntimeException {

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
