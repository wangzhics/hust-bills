package com.hust.bill.electric.core.http;

public class RequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1392698227652517211L;

	public RequestException() {
		super();
	}

	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException(String message) {
		super(message);
	}

	public RequestException(Throwable cause) {
		super(cause);
	}

}
