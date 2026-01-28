package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents the error response body sent when an item quantity is invalid.
 * <p>
 * This typically occurs during order creation if the quantity is zero, negative,
 * or exceeds stock limits (if inventory management is implemented).
 */
public class InvalidQuantityBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new InvalidQuantityBody.
     *
     * @param message   Human readable explanation of exception(e.g., "Quantity must be greater than zero").
     * @param status    The HTTP status code (usually 400 BAD_REQUEST).
     * @param timestamp The exact moment the error was caught.
     */
    public InvalidQuantityBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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
