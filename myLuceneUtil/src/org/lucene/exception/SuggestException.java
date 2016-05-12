package org.lucene.exception;

public class SuggestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8453787248716641320L;

	public SuggestException() {
		super();
	}

	public SuggestException(String message) {
		super(message);
	}

	public SuggestException(Throwable cause) {
		super(cause);
	}

	public SuggestException(String message, Throwable cause) {
		super(message, cause);
	}

	public SuggestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
