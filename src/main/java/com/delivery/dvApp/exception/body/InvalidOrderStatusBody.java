package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents the error response body sent when an illegal state transition is attempted on an Order.
 * <p>
 * For example, attempting to cancel an order that has already been delivered,
 * or delivering an order that hasn't been picked up yet.
 */
public class InvalidOrderStatusBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new InvalidOrderStatusBody.
     *
     * @param message   The error message explaining why the transition is invalid.
     * @param status    The HTTP status code (usually 400 BAD_REQUEST).
     * @param timestamp The exact moment the error was caught.
     */
    public InvalidOrderStatusBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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