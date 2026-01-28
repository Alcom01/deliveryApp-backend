package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a lookup for a Customer by ID fails.
 */
public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message){
             super(message);
    }
}
