package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 *  Represent the payload returned when an attempt is made to create an order that
 * duplicates an existing active transaction.
 */
public class OrderExistsBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
    /**
     * @param message   Human-readable explanation of the already existing order.
     * @param status    The HTTP status (typically 409 Conflict or 400 Bad Request).
     * @param timestamp The exact moment the error was caught.
     */
    public OrderExistsBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public ZonedDateTime getTimestamp() { return timestamp; }
}