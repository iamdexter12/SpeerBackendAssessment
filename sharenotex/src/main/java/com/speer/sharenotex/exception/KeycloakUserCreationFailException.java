package com.speer.sharenotex.exception;

import org.springframework.http.HttpStatus;

public class KeycloakUserCreationFailException extends GenericException {

	private static final long serialVersionUID = 1L;
	private static final HttpStatus httpStatusCode = HttpStatus.NOT_FOUND;

	/**
	 * Constructs a new KeycloakUserCreationFailException with the specified field
	 * name and message.
	 *
	 * @param fieldName The name of the field that caused the exception.
	 * @param message   The detailed message describing the exception.
	 */
	public KeycloakUserCreationFailException(final String fieldName, final String message) {
		super(fieldName, httpStatusCode, String.format("%s", message));
	}
}