package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
/**
 * Represents the error response body sent when a search for active orders yields no results.
 * <p>
 * This structure ensures the client receives a consistent JSON format containing
 * the error message, the HTTP status code, and the precise time the error occurred.
 */
public class ActiveOrderNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new ActiveOrderNotFoundBody.
     *
     * @param message   The descriptive error message (e.g., "No active orders found for customer ID X").
     * @param status    The HTTP status code (usually 404 NOT_FOUND).
     * @param timestamp The exact moment the error was caught.
     */
    public ActiveOrderNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
