package cn.einino.commons.db.exception;

public class DataSoruceException extends Exception {

	private static final long serialVersionUID = -5858893618480073231L;

	public DataSoruceException() {
	}

	public DataSoruceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataSoruceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataSoruceException(String message) {
		super(message);
	}

	public DataSoruceException(Throwable cause) {
		super(cause);
	}

}
