package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents the error response body sent when a requested Courier entity cannot be found in the database.
 * <p>
 * Used typically when performing operations requiring a valid Courier ID, such as assigning an order.
 */
public class CourierNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new CourierNotFoundBody.
     *
     * @param message   The descriptive error message (e.g., "Courier with ID X not found").
     * @param status    The HTTP status code (usually 404 NOT_FOUND).
     * @param timestamp The exact moment the error was caught.
     */
    public CourierNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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
