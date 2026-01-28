package com.delivery.dvApp.service;

import com.delivery.dvApp.dto.MenuItemDto;
import com.delivery.dvApp.entity.Item;
import com.delivery.dvApp.entity.Restaurant;
import com.delivery.dvApp.enums.Category;
import com.delivery.dvApp.exception.custom.ItemMisMatchException;
import com.delivery.dvApp.exception.custom.ItemNotFoundException;
import com.delivery.dvApp.exception.custom.NameAlreadyExistsException;
import com.delivery.dvApp.exception.custom.RestaurantNotFoundException;
import com.delivery.dvApp.repository.ItemRepository;
import com.delivery.dvApp.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Service layer responsible for handling business logic related to
 * restaurants and their menu items.
 *
 * <p>This class manages CRUD operations for restaurants and items,
 * soft deletion, menu retrieval, and update operations.</p>
 */
@Service
public class RestaurantService {

    private  final RestaurantRepository restaurantRepository;
    private  final ItemRepository  itemRepository;

    /**
     * Constructs a RestaurantService with required repositories.
     *
     * @param restaurantRepository repository for restaurant persistence
     * @param itemRepository repository for item persistence
     */
    public RestaurantService(RestaurantRepository restaurantRepository,
                             ItemRepository itemRepository){
              this.restaurantRepository = restaurantRepository;
              this.itemRepository = itemRepository;
    }


    /**
     * Retrieves all restaurants that are not marked as deleted.
     *
     * @return list of active restaurants
     */
    public List<Restaurant> findAllRestaurants(){
        return restaurantRepository.findByDeletedFalse();
    }

    /**
     * Finds a restaurant by its name.
     *
     * @param name name of the restaurant
     * @return the matching restaurant
     * @throws RestaurantNotFoundException if restaurant does not exist or is deleted
     */
    public Restaurant findRestaurantByName(String name){
        Restaurant restaurant = restaurantRepository.findByName(name)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found"));

        if(restaurant.isDeleted()){
            throw new RestaurantNotFoundException("Restaurant does not exist anymore");
        }

        return  restaurant;
    }


    /**
     * Retrieves restaurants belonging to a specific category.
     *
     * @param category category name (case-insensitive)
     * @return list of restaurants in the given category
     * @throws IllegalArgumentException if category is invalid
     */
    public List<Restaurant> getRestaurantsByCategory(String category) {
        Category enumCategory = Category.valueOf(category.toUpperCase());
        return restaurantRepository.findByCategory(enumCategory);
    }

    /**
     * Retrieves the menu of a restaurant.
     *
     * <p>Only items that are not marked as deleted are included.</p>
     *
     * @param restaurantId ID of the restaurant
     * @return list of menu item DTOs
     * @throws RestaurantNotFoundException if restaurant does not exist
     * @throws ItemNotFoundException if no items are found
     */
    @Transactional
    public List<MenuItemDto> getMenuOfRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant Not Found"));

        List<Item> items = itemRepository.findByRestaurantId(restaurantId)
                .orElseThrow(()-> new ItemNotFoundException("There is no items found in this restaurant"));


        List<MenuItemDto> menu = new ArrayList<>();

            for (Item item : items){
                if(!item.isDeleted()) {
                    MenuItemDto dto = new MenuItemDto();
                    dto.setName(item.getName());
                    dto.setDescription(item.getDescription());
                    dto.setPrice(item.getPrice());
                    menu.add(dto);
                }
            }
            return menu;
    }


    /**
     * Changes the name of a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param newName new name to be assigned
     * @throws RestaurantNotFoundException if restaurant does not exist
     * @throws NameAlreadyExistsException if new name matches the old name
     */
    public void changeRestaurantName(Long restaurantId,String newName){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found."));

          if(restaurant.getName().toUpperCase(Locale.ROOT).equals(newName.toUpperCase(Locale.ROOT))){
              throw new NameAlreadyExistsException(" New name cannot be same as old one.");
          }

          restaurant.setName(newName);

          restaurantRepository.save(restaurant);
    }

    /**
     * Changes the address of a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param newAddress new address to be assigned
     * @throws RestaurantNotFoundException if restaurant does not exist
     * @throws NameAlreadyExistsException if new address matches the old address
     */
    public void changeRestaurantAddress(Long restaurantId, String newAddress){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found."));

        if(restaurant.getAddress().toUpperCase(Locale.ROOT).equals(newAddress.toUpperCase(Locale.ROOT))){
            throw new NameAlreadyExistsException(" New address cannot be same as old one.");
        }

        restaurant.setAddress(newAddress);
        restaurantRepository.save(restaurant);
    }

    /**
     * Updates the price of an item.
     *
     * @param itemId ID of the item
     * @param newPrice new price to be set
     * @throws ItemNotFoundException if item does not exist
     */
    public void changeItemPrice(Long itemId,BigDecimal newPrice){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        item.setPrice(newPrice);

         itemRepository.save(item);


    }


    /**
     * Adds a new restaurant.
     *
     * @param restaurant restaurant entity to be saved
     */
    public Long addRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
        return restaurant.getId();

    }

    /**
     * Adds a new menu item to a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param itemDto menu item data transfer object
     * @throws RestaurantNotFoundException if restaurant does not exist
     */
    @Transactional
    public Long addItem(Long restaurantId,MenuItemDto itemDto){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found"));

        Item item = new Item();

        item.setRestaurant(restaurant);
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());

        itemRepository.save(item);
        return item.getId();

    }

    /**
     * Soft deletes a restaurant and all its items.
     *
     * @param restaurantId ID of the restaurant
     * @throws RestaurantNotFoundException if restaurant does not exist
     */
    public void deleteRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found"));


        List<Item> items = itemRepository.findByRestaurantId(restaurantId)
                .orElse(new ArrayList<>());

        for (Item item : items) {
            item.setDeleted(true);
        }
        itemRepository.saveAll(items);


        restaurant.setDeleted(true);
        restaurantRepository.save(restaurant);
    }


    /**
     * Soft deletes an item from a restaurant.
     *
     * @param restaurantId ID of the restaurant
     * @param itemId ID of the item
     * @throws RestaurantNotFoundException if restaurant does not exist
     * @throws ItemNotFoundException if item does not exist
     * @throws ItemMisMatchException if item does not belong to the restaurant
     */
    @Transactional
    public void removeItem( Long restaurantId,Long itemId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item Not Found"));

        if (!item.getRestaurant().getId().equals(restaurantId)) {
            throw new ItemMisMatchException("Item does not belong to this restaurant");
        }
        item.setDeleted(true);
        itemRepository.save(item);

    }

}
