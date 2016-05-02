package org.lucene.exception;

public class SearcherException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearcherException() {
		super();
	}

	public SearcherException(String message) {
		super(message);
	}

	public SearcherException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearcherException(Throwable cause) {
		super(cause);
	}

}
