package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a specific Order cannot be found.
 */
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message){
        super(message);
    }
}
