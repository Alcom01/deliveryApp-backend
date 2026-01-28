package com.delivery.dvApp.enums;

/**
 * Represents the lifecycle stages of a delivery order.
 * <p>
 * These statuses dictate the flow of the application, determining
 * which actions are available to Customers, Restaurants, and Couriers
 * at any given time.
 */
public enum OrderStatus {
    /** * Initial state. The order has been submitted by the customer
     * but has not yet been acknowledged by the restaurant.
     */
    CREATED,

    /** * The restaurant has confirmed the order and is currently
     * preparing the items.
     */
    ACCEPTED,

    /** * The courier has arrived at the restaurant, collected the order,
     * and is now in transit to the customer's location.
     */
    PICKED_UP,

    /** * The final successful state. The order has been handed over
     * to the customer.
     */
    DELIVERED,

    /** * The order has been terminated before completion. This can
     * occur if the restaurant is out of stock or the customer
     * cancels the request.
     */
    CANCELLED
}