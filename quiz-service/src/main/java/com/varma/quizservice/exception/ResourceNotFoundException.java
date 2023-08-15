package com.varma.quizservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ResourceNotFoundException class represents an exception that is thrown when a requested resource is not found.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified error message.
     *
     * @param message the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}