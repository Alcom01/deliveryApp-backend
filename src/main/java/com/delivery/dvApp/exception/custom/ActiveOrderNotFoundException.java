package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a request for a customer's or courier's active orders returns nothing.
 */
public class ActiveOrderNotFoundException extends RuntimeException{
    public ActiveOrderNotFoundException(String message){
        super(message);
    }
}
