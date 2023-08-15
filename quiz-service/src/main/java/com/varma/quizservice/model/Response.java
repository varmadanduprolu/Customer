package com.varma.quizservice.model;

/**
 * The Response record represents a response object with an ID and a response status.
 */
public record Response(Integer id, String responseStatus) {
}