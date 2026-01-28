package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represent error response body for data integrity violations, specifically unique constraint
 * conflicts on entity names (e.g., duplicate restaurant names).
 */
public class NameAlreadyExistsBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * @param message   Explanation of the conflict (e.g., "Restaurant 'Pizza Palace' already exists").
     * @param status    The HTTP status (typically 409 Conflict or 400 Bad Request).
     * @param timestamp The exact moment the collision occurred.
     */
    public NameAlreadyExistsBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public ZonedDateTime getTimestamp() { return timestamp; }
}