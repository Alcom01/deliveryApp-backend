package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents the error response body sent when a requested Customer entity cannot be found.
 * <p>
 * This is returned to the client to indicate that the customer ID provided in the request
 * does not correspond to any existing user records.
 */
public class CustomerNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new CustomerNotFoundBody.
     *
     * @param message   The descriptive error message.
     * @param status    The HTTP status code (usually 404 NOT_FOUND).
     * @param timestamp The exact moment the error was caught.
     */
    public CustomerNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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
