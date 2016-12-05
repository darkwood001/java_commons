package cn.einino.commons.serializer.exceptions;

/**
 * Created by einino on 2016/11/18.
 */
public class SerializeException extends RuntimeException {

	private static final long serialVersionUID = 1261053067005195272L;

	public SerializeException(String message) {
		super(message);
	}

	public SerializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
