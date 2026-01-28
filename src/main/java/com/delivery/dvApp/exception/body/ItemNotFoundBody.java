package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents error response body for errors occurring when a specific menu item cannot be located.
 * Used to provide a structured JSON response to the client.
 */
public class ItemNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * @param message   Human-readable explanation of the missing item.
     * @param status    The HTTP status (typically 404 Not Found).
     * @param timestamp The exact moment the error was caught.
     */
    public ItemNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public ZonedDateTime getTimestamp() { return timestamp; }
}