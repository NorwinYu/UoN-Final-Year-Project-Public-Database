package edu.mum.library.common;

public class LibraryException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -7088845152057314366L;

	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public LibraryException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
