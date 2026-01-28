package com.delivery.dvApp.enums;

/**
 * Defines the culinary genres available in the delivery system.
 * <p>
 * This enum is used by the {@link com.delivery.dvApp.entity.Restaurant} entity
 * to classify its primary cuisine type and by the search service to filter
 * results for the end-user.
 */
public enum Category {
    /** Classic Italian-style pizzas and flatbreads. */
    PIZZA,

    /** Middle Eastern grilled meats, wraps, and sides. */
    KEBAB,

    /** 100% plant-based dining options, excluding all animal products. */
    VEGAN,

    /** Meat-free dining that may include dairy or eggs. */
    VEGETARIAN,

    /** Gourmet and fast-food style burgers and sandwiches. */
    BURGER,

    /** Japanese-style seafood, rolls, and sashimi. */
    SUSHI,

    /** Sweet treats, pastries, and confectionery. */
    DESSERT
}
