package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
/**
 * Represents Payload for errors occurring when a specific restaurant entity
 * cannot be retrieved from the database.
 */
public class RestaurantNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * @param message   Human-readable explanation of the missing restaurant.
     * @param status    The HTTP status (typically 404 Not Found).
     * @param timestamp The exact moment the error was caught.
     */
    public RestaurantNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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
