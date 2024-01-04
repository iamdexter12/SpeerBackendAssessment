package com.speer.sharenotex.exception;

import org.springframework.http.HttpStatus;

public class RateLimitExceedException extends GenericException {

	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.TOO_MANY_REQUESTS;

	/**
	 * Constructs a new RateLimitExceedException with the specified field name and
	 * message.
	 *
	 * @param fieldName The name of the field that caused the exception.
	 * @param message   The detailed message describing the exception.
	 */
	public RateLimitExceedException(final String fieldName, final String message) {
		super(fieldName, httpStatusCode, String.format("%s", message));
	}
}
