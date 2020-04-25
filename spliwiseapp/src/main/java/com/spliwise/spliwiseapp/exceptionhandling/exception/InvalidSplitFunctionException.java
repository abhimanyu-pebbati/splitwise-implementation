package com.spliwise.spliwiseapp.exceptionhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSplitFunctionException extends InvalidEntityException {
	public InvalidSplitFunctionException() {
		super();
	}

	public InvalidSplitFunctionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSplitFunctionException(String message) {
		super(message);
	}

	public InvalidSplitFunctionException(Throwable cause) {
		super(cause);
	}
}