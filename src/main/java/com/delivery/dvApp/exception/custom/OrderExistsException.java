package com.delivery.dvApp.exception.custom;
/**
 * Thrown when an attempt is made to create an order that is already
 * registered as active.
 */
public class OrderExistsException extends RuntimeException{

    public OrderExistsException(String message){
        super(message);
    }
}
