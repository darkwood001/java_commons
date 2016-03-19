package cn.einino.commons.db.exception;

public class DataBaseTemplateException extends Exception {

	private static final long serialVersionUID = -4917497235157557346L;

	public DataBaseTemplateException() {
	}

	public DataBaseTemplateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataBaseTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataBaseTemplateException(String message) {
		super(message);
	}

	public DataBaseTemplateException(Throwable cause) {
		super(cause);
	}

}
