package com.delivery.dvApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * DTO representing an active order assigned to a courier.
 *
 * <p>Used to show pickup and delivery-related details
 * for couriers handling ongoing orders.</p>
 */
public class ActiveOrderDto {
    /** Name of the restaurant */
    private String restaurantName;

    /** Unique identifier of the order */
    private Long orderId;

    /** Total price of the order */
    private BigDecimal totalPrice;

    /** Timestamp when the order was picked up */
    private LocalDateTime pickedAt;

    /** Delivery address of the customer */
    private String customerAddress;

    /** Customer phone number */
    private String customerNumber;


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getPickedAt() {
        return pickedAt;
    }

    public void setPickedAt(LocalDateTime pickedAt) {
        this.pickedAt = pickedAt;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPhoneNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
