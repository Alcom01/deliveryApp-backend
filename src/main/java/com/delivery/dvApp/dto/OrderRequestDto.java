package com.delivery.dvApp.dto;
import java.util.List;

/**
 * DTO used to create a new order.
 *
 * <p>Contains references to restaurant, customer, courier,
 * and the list of requested items with quantities.</p>
 */
public class OrderRequestDto {

    /** ID of the restaurant where the order is placed */
    private Long restaurantId;

    /** ID of the customer placing the order */
    private Long customerId;

    /** ID of the courier assigned to the order */
    private Long courierId;
    /** List of items and their requested quantities */
    private List<ItemQuantityDto> itemToQuantities;


    public OrderRequestDto() {
    }

    public OrderRequestDto(Long restaurantId, Long customerId, Long courierId, List<ItemQuantityDto> itemToQuantities) {
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.courierId = courierId;
        this.itemToQuantities = itemToQuantities;
    }


    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public List<ItemQuantityDto> getItemToQuantities() {
        return itemToQuantities;
    }

    public void setItemToQuantities(List<ItemQuantityDto>itemToQuantities) {
        this.itemToQuantities = itemToQuantities;
    }
}