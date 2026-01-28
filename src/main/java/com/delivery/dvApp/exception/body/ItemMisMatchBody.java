package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 * Represents the error response body sent when there is a mismatch between an Item and its Restaurant.
 * <p>
 * This error ensures data integrity by preventing orders that contain items
 * belonging to a restaurant other than the one specified in the order request.
 */
public class ItemMisMatchBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * Constructs a new ItemMisMatchBody.
     *
     * @param message   The error message (e.g., "Item X does not belong to Restaurant Y").
     * @param status    The HTTP status code (usually 400 BAD_REQUEST or 409 CONFLICT).
     * @param timestamp The exact moment the error was caught.
     */
    public ItemMisMatchBody(String message, HttpStatus status, ZonedDateTime timestamp) {
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