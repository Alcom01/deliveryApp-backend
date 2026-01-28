package com.delivery.dvApp.exception.custom;
/**
 * Thrown when an order operation is attempted on an invalid status.
 * (e.g., trying to deliver an order that hasn't been picked up).
 */
public class InvalidOrderStatusException extends RuntimeException{

    public InvalidOrderStatusException(String message){
        super(message);
    }
}
