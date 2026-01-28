package com.delivery.dvApp.exception.custom;
/**
 * Thrown when a quantity provided in an order is invalid (e.g., zero or negative).
 */
public class InvalidQuantityException extends RuntimeException{
     public InvalidQuantityException(String message){
         super(message);
     }
}
