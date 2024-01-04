package com.speer.sharenotex.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Generic exception class representing runtime exceptions with additional
 * details. Extends {@link RuntimeException}.
 *
 * @see RuntimeException
 */
@Getter
@Setter
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String fieldName;
	private final HttpStatus httpStatusCode;

	/**
	 * Constructs a GenericException with the specified field name, HTTP status, and
	 * message.
	 *
	 * @param fieldName      The name of the field associated with the exception.
	 * @param httpStatusCode The HTTP status code representing the exception.
	 * @param message        A descriptive message providing more information about
	 *                       the exception.
	 */
	public GenericException(final String fieldName, final HttpStatus httpStatusCode, final String message) {
		super(message);
		this.fieldName = fieldName;
		this.httpStatusCode = httpStatusCode;
	}
}
