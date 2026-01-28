package com.delivery.dvApp.dto;

/**
 * DTO representing a menu item and its requested quantity.
 *
 * <p>Used when creating a new order.</p>
 */
public class ItemQuantityDto {
    /** Unique identifier of the menu item */
    private Long itemId;

    /** Quantity requested for the item */
    private int quantity;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {

        this.itemId = itemId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
