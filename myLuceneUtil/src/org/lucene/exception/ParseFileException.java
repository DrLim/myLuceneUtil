package org.lucene.exception;

public class ParseFileException extends DocumentNotIndexedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3463767967398463520L;
	
	public ParseFileException() {
		super();
	}

	public ParseFileException(String message) {
		super(message);
	}

	public ParseFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseFileException(Throwable cause) {
		super(cause);
	}

}
