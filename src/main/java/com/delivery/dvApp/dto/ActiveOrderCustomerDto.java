package com.delivery.dvApp.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO representing an active order from the customer's perspective.
 *
 * <p>Contains details about the restaurant, ordered items, assigned courier,
 * order creation time, and total price.</p>
 */
public class ActiveOrderCustomerDto {
    /** Name of the restaurant where the order was placed */
    private String restaurantName;

    /** Timestamp when the order was created */
    private LocalDateTime createdAt;

    /** List of ordered items with quantities */
    private List<ItemDetailsDto> itemDetailsDtos;

    /** Name of the courier assigned to the order */
    private String courierName;

    /** Phone number of the courier */
    private String courierNumber;

    /** Total price of the order */
    private BigDecimal total;


    public List<ItemDetailsDto> getItemDetailsDtos() {
        return itemDetailsDtos;
    }

    public void setItemDetailsDtos(List<ItemDetailsDto> itemDetailsDtos) {
        this.itemDetailsDtos = itemDetailsDtos;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
