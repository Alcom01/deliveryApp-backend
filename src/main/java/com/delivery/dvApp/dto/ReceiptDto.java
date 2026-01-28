package com.delivery.dvApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * DTO representing an order receipt.
 *
 * <p>Returned after successfully creating an order.</p>
 */
public class ReceiptDto {
    /** Name of the restaurant */
    private String restaurantName;

    /** List of ordered items with quantities */
    private List<ItemDetailsDto> itemDetails;

    /** Total price of the order */
    private BigDecimal total;

    /** Timestamp when the order was created */
    private LocalDateTime createdAt;

    /** Unique identifier of the order */
    private  Long orderId;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<ItemDetailsDto> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetailsDto> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
