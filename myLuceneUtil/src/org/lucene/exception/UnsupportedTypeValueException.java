package org.lucene.exception;


public class UnsupportedTypeValueException extends DocumentNotIndexedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedTypeValueException() {
		super();
	}

	public UnsupportedTypeValueException(String message) {
		super(message);
	}

	public UnsupportedTypeValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedTypeValueException(Throwable cause) {
		super(cause);
	}
}
