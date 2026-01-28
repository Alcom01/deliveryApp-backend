package com.delivery.dvApp.exception.custom;
/**
 * Thrown when an item in an order does not belong to the restaurant
 * specified in that same order.
 */
public class ItemMisMatchException extends RuntimeException{

    public ItemMisMatchException(String message){
        super(message);
    }
}
