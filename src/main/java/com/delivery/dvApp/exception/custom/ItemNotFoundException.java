package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a specific Item (menu item) cannot be found in the repository.
 */
public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String message){
        super(message);
    }
}
