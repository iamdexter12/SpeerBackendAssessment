package com.speer.sharenotex.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a resource is not found, resulting in a not found HTTP status.
 * Inherits from {@link GenericException}.
 *
 * @see GenericException
 */
public class NotFoundException extends GenericException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a NotFoundException with the specified field name and message.
     *
     * @param fieldName The name of the field associated with the exception.
     * @param message   A descriptive message providing more information about the exception.
     */
    public NotFoundException(String fieldName, String message) {
        super(fieldName, HttpStatus.NOT_FOUND, message);
    }
}

