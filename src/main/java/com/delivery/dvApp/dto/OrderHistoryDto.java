package com.delivery.dvApp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * DTO representing a customer's past (delivered) order.
 *
 * <p>Includes restaurant details, courier information,
 * ordered items, delivery time, and total price.</p>
 */
public class OrderHistoryDto {

    /** Name of the restaurant */
    private String restaurantName;

    /** Timestamp when the order was delivered */
    private LocalDateTime deliveredAt;

    /** List of items included in the order */
    private List<ItemDetailsDto> itemDetailsDtos;

    /** Name of the courier who delivered the order */
    private String courierName;

    /** Phone number of the courier */
    private String courierNumber;

    /** Total price of the order */
    private BigDecimal total;



    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

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
}
