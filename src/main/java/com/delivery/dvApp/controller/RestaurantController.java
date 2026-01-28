package com.delivery.dvApp.controller;

import com.delivery.dvApp.dto.MenuItemDto;
import com.delivery.dvApp.entity.Restaurant;
import com.delivery.dvApp.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * REST controller responsible for restaurant-related endpoints.
 *
 * <p>Provides APIs for managing restaurants, retrieving menus,
 * updating restaurant details, and managing menu items.</p>
 */
@RestController
@RequestMapping("restaurant")
public class RestaurantController {
    private RestaurantService restaurantService;


    /**
     * Constructs a RestaurantController with required service.
     *
     * @param restaurantService service handling restaurant business logic
     */
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;

    }

    /**
     * Retrieves all active restaurants.
     *
     * @return list of restaurants that are not marked as deleted
     */
    @GetMapping("/findAll")
    public List<Restaurant> findAllRestaurants(){

        return restaurantService.findAllRestaurants();
    }
    /**
     * Retrieves a restaurant by its name.
     *
     * @param name name of the restaurant
     * @return matching restaurant
     */
    @GetMapping("/findByName/{name}")
    public Restaurant findByName(@PathVariable String name){
        return restaurantService.findRestaurantByName(name);
    }


    /**
     * Retrieves the menu of a restaurant.
     *
     * <p>Only items that are not marked as deleted are included.</p>
     *
     * @param restaurantId ID of the restaurant
     * @return list of menu item DTOs
     */
    @GetMapping("/getMenu")
    public List<MenuItemDto> getMenu(@RequestParam Long restaurantId){
        return restaurantService.getMenuOfRestaurant(restaurantId);
    }

    /**
     * Retrieves restaurants by category.
     *
     * @param category category name (case-insensitive)
     * @return list of restaurants belonging to the category
     */
    @GetMapping("/getByCategory")
    public List<Restaurant> getByCategory(@RequestParam String category) {
        return restaurantService.getRestaurantsByCategory(category);
    }

    /**
     * Updates the name of a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param name new name to be assigned
     */
    @PutMapping("/changeName")
    public void changeName(@RequestParam Long restaurantId, @RequestParam String name){
        restaurantService.changeRestaurantName(restaurantId,name);
    }

    /**
     * Updates the address of a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param address new address to be assigned
     */
    @PutMapping("/changeAddress")
    public void changeAddress(@RequestParam Long restaurantId,@RequestParam String address){
        restaurantService.changeRestaurantAddress(restaurantId,address);
    }

    /**
     * Updates the price of a menu item.
     *
     * @param itemId ID of the item
     * @param price new price to be set
     */
    @PutMapping("/changePrice")
    public void changePrice(@RequestParam Long itemId, @RequestParam BigDecimal price){
        restaurantService.changeItemPrice(itemId,price);
    }

    /**
     * Adds a new restaurant.
     *
     * @param restaurant restaurant entity to be created
     */
    @PostMapping("/add-restaurant")
    public Long addRestaurant(@RequestBody Restaurant restaurant){
         return restaurantService.addRestaurant(restaurant);

    }

    /**
     * Adds a new item to a restaurant's menu.
     *
     * @param restaurantId ID of the restaurant
     * @param itemDto menu item details
     */
    @PostMapping("/add-item")
    public Long addItem(@RequestParam Long restaurantId,@RequestBody MenuItemDto itemDto){
         return restaurantService.addItem(restaurantId,itemDto);
    }

    /**
     * Soft deletes a restaurant and all its menu items.
     *
     * @param restaurantId ID of the restaurant
     */
    @DeleteMapping("/delete-restaurant")
    public void deleteRestaurant(@RequestParam Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
    }

    /**
     * Soft deletes an item from a restaurant's menu.
     *
     * @param restaurantId ID of the restaurant
     * @param itemId ID of the item
     */
    @DeleteMapping("/delete-item")
    public void deleteItem(@RequestParam Long restaurantId,@RequestParam Long itemId){
        restaurantService.removeItem(restaurantId,itemId);
    }


}
