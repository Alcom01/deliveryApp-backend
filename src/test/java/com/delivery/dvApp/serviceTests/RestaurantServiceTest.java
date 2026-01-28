package com.delivery.dvApp.serviceTests;

import com.delivery.dvApp.dto.MenuItemDto;
import com.delivery.dvApp.entity.Item;
import com.delivery.dvApp.entity.Restaurant;
import com.delivery.dvApp.enums.Category;
import com.delivery.dvApp.repository.ItemRepository;
import com.delivery.dvApp.repository.RestaurantRepository;
import com.delivery.dvApp.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RestaurantService}.
 * <p>
 * Focuses on restaurants and managing their menus.
 */
@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {
    @Mock
     private RestaurantRepository restaurantRepository;

    @Mock
     private ItemRepository itemRepository;

    @InjectMocks
     private RestaurantService restaurantService;


    /**
     * Verifies that a valid Restaurant entity is saved to the repository.
     */
    @Test
    void addRestaurant_shouldAddRestaurantSuccessfully(){

        //Arrange
        Restaurant restaurant = new Restaurant(
                "Pizza Di Napoli",
                "Warsaw Center",
                "+48123456789",
                Category.PIZZA
                );

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
         //Act
        restaurantService.addRestaurant(restaurant);
        //Assert
        verify(restaurantRepository,times(1)).save(restaurant);

    }

    /**
     * Verifies that a new Item can be added to an existing Restaurant's menu.
     * <p>
     * <b>Scenario:</b> A restaurant exists, and a valid {@link MenuItemDto} is provided.
     * <br><b>Expectation:</b>
     * <ul>
     * <li>The item is saved to the repository.</li>
     * <li>The saved item is correctly associated with the restaurant.</li>
     * <li>Fields (Name, Description, Price) match the DTO.</li>
     * </ul>
     */
    @Test
    void addItem_shouldAddItemToRestaurantMenuSuccessfully(){
        //Arrange
        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant("Pizza Di Napoli",
                "Warsaw Center",
                "+48123456789",
                Category.PIZZA);

        MenuItemDto itemDto = new MenuItemDto();
        itemDto.setName("4 Cheese Pizza");
        itemDto.setDescription("A delicious cheesy classic pizza");
        itemDto.setPrice(BigDecimal.valueOf(35.45));

        when(restaurantRepository.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);

        //Act
        restaurantService.addItem(restaurantId,itemDto);

        //Assert
        verify(itemRepository,times(1)).save(itemCaptor.capture());

        Item savedItem = itemCaptor.getValue();
       assertEquals("4 Cheese Pizza",savedItem.getName());
       assertEquals("A delicious cheesy classic pizza",savedItem.getDescription());
       assertEquals(BigDecimal.valueOf(35.45),savedItem.getPrice());
       assertEquals(restaurant,savedItem.getRestaurant());

    }
}
