package com.spliwise.spliwiseapp.exceptionhandling.exception;

public abstract class AbstractException extends RuntimeException {
	public AbstractException() {
		super();
	}

	public AbstractException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractException(String message) {
		super(message);
	}

	public AbstractException(Throwable cause) {
		super(cause);
	}
}