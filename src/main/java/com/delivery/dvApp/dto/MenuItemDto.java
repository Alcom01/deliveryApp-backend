package com.delivery.dvApp.dto;


import java.math.BigDecimal;
/**
 * DTO representing a restaurant menu item.
 *
 * <p>Used for creating, updating, and displaying menu items.</p>
 */
public class MenuItemDto {
    /** Name of the menu item */
    private  String name;

    /** Description of the menu item */
    private String description;

    /** Price of the menu item */
    private BigDecimal price;



    public MenuItemDto(){

    }

    public MenuItemDto(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
