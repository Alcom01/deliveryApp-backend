package com.delivery.dvApp.dto;
/**
 * DTO representing item details within an order.
 *
 * <p>Used in receipts, order history, and active order views.</p>
 */
public class ItemDetailsDto {
    /** Name of the menu item */
    private String itemName;
    /** Quantity of the item ordered */
    private int quantity;


    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
