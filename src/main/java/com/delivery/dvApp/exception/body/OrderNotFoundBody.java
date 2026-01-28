package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Payload returned when a specific Order ID is queried but does not exist
 * in the persistence layer.
 */
public class OrderNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
    /**
     * @param message   Human-readable explanation of the missing order.
     * @param status    The HTTP status (typically 404 Not Found).
     * @param timestamp The exact moment the error was caught.
     */
    public OrderNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public ZonedDateTime getTimestamp() { return timestamp; }
}
