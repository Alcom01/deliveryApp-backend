package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a lookup for a Courier by ID fails.
 */
public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException(String message){
        super(message);
    }
}
