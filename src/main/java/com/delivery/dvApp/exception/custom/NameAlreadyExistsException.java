package com.delivery.dvApp.exception.custom;
/**
 * Thrown when attempting to create a restaurant or item with a name
 * that already exists in the system.
 */
public class NameAlreadyExistsException extends RuntimeException{
    public NameAlreadyExistsException(String message){
        super(message);
    }
}
