package org.lucene.exception;

public class DocumentNotIndexedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentNotIndexedException() {
		super();
	}

	public DocumentNotIndexedException(String message) {
		super(message);
	}

	public DocumentNotIndexedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocumentNotIndexedException(Throwable cause) {
		super(cause);
	}
}
