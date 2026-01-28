package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a Restaurant lookup fails.
 */
public class RestaurantNotFoundException extends RuntimeException{

    public RestaurantNotFoundException(String message){
        super(message);
    }
}
