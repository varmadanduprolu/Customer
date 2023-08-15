package com.varma.quizservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The DuplicateException class represents an exception that is thrown when a duplicate resource is encountered.
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateException extends RuntimeException {

    /**
     * Constructs a new DuplicateException with the specified error message.
     *
     * @param message the error message
     */
    public DuplicateException(String message) {
        super(message);
    }
}