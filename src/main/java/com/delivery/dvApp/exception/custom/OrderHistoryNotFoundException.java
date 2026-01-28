package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a user or courier requests their historical order list
 * and no past orders are found.
 */
public class OrderHistoryNotFoundException extends RuntimeException {
    public OrderHistoryNotFoundException(String message){
        super(message);
    }

}
