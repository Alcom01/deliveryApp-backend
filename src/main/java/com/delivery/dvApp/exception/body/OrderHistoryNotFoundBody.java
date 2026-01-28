package com.delivery.dvApp.exception.body;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

/**
 *  Represents payload for scenarios where a user/courier requests a list of past orders,
 * but no historical data exists for that account.
 */
public class OrderHistoryNotFoundBody {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    /**
     * @param message   Human-readable explanation of the missing order history.
     * @param status    The HTTP status (typically 404 Not Found).
     * @param timestamp The exact moment the error was caught.
     */
    public OrderHistoryNotFoundBody(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public ZonedDateTime getTimestamp() { return timestamp; }
}
