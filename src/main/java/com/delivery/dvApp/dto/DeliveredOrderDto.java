package com.delivery.dvApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * DTO representing a delivered order for courier history.
 *
 * <p>Includes summary information about completed deliveries.</p>
 */
public class DeliveredOrderDto {
    /** Name of the restaurant */
    private String restaurantName;

    /** Unique identifier of the order */
    private Long orderId;

    /** Total price of the order */
    private BigDecimal totalPrice;

    /** Timestamp when the order was delivered */
    private LocalDateTime deliveredAt;

    /** Customer delivery address */
    private String customerAddress;

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

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
