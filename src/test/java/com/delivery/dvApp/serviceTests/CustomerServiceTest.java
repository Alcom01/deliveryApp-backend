package com.delivery.dvApp.serviceTests;

import com.delivery.dvApp.dto.ActiveOrderCustomerDto;
import com.delivery.dvApp.entity.*;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.exception.custom.CustomerNotFoundException;
import com.delivery.dvApp.repository.CustomerRepository;
import com.delivery.dvApp.repository.OrderRepository;
import com.delivery.dvApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CustomerService}.
 * <p>
 * Focuses on customer management and retrieval of active order data.
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    CustomerService customerService;


    /**
     * Verifies that a valid Customer entity is saved to the repository.
     */
    @Test
    void addCustomer_shouldAddCustomerSuccessfully() {
        //Arrange
        Customer customer = new Customer(
                "Micheal Henderson",
                "Aleja Wilanowska 61/14 02-888",
                "+48123456789");
        when(customerRepository.save(customer)).thenReturn(customer);
        //Act
        customerService.addCustomer(customer);
        //Assert
        verify(customerRepository, times(1)).save(customer);

    }

    /**
     * Verifies retrieving active orders for a customer.
     * <p>
     * <b>Scenario:</b> A customer exists with one active order (Status: ACCEPTED).
     * <br><b>Expectation:</b>
     * <ul>
     * <li>Returns a list containing exactly one {@link ActiveOrderCustomerDto}.</li>
     * <li>The DTO fields (Courier Name, Restaurant Name, Total, Items) match the entity data.</li>
     * </ul>
     */
    @Test
    void viewActiveOrders_ShouldReturnActiveOrders() {
        //Arrange
        Customer customer = new Customer();
        customer.setId(1L);

        Courier courier = new Courier();
        courier.setName("Bruce Wayne");
        courier.setPhoneNumber("+48123456789");

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Gotham Pizza");

        Item item = new Item();
        item.setName("Pepperoni Pizza");


        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(2);


        Order order = new Order();
        order.setCourier(courier);
        order.setRestaurant(restaurant);
        order.setOrderItems(List.of(orderItem));
        order.setTotalPrice(BigDecimal.valueOf(50.00));
        order.setCreatedAt(LocalDateTime.now());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomerIdAndStatus(1L, OrderStatus.ACCEPTED))
                .thenReturn(List.of(order));

        // Act
        List<ActiveOrderCustomerDto> result = customerService.viewActiveOrders(1l);

        //Assert
        assertEquals(result.size(),1);

        ActiveOrderCustomerDto dto = result.get(0);


        assertEquals(dto.getCourierName(),"Bruce Wayne");
        assertEquals(dto.getRestaurantName(),"Gotham Pizza");
        assertEquals(dto.getTotal(),BigDecimal.valueOf(50.00));


        assertEquals(dto.getItemDetailsDtos().size(),1);
        assertEquals(dto.getItemDetailsDtos().get(0).getItemName(),"Pepperoni Pizza");
        assertEquals(dto.getItemDetailsDtos().get(0).getQuantity(),2);


        verify(customerRepository).findById(1L);
        verify(orderRepository).findByCustomerIdAndStatus(1L, OrderStatus.ACCEPTED);
    }

    /**
     * Verifies exception handling when trying to view orders for a non-existent customer.
     * <p>
     * <b>Expectation:</b> Throws {@link CustomerNotFoundException} with message "Customer not found."
     */
    @Test
    void viewActiveOrders_ShouldThrowExceptionIfCustomerIsNotFound() {
        //Arrange
        Long customerId = 99L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.viewActiveOrders(customerId);
        });
        //Assert
        assertEquals(exception.getMessage(),"Customer not found.");

    }

    /**
     * Verifies behavior when the customer exists but has no active orders.
     * <p>
     * <b>Expectation:</b> Returns an empty list (does not return null or throw exception).
     */
    @Test
    void viewActiveOrders_ShouldReturnEmptyListIfThereIsNoActiveOrders() {
        //Arrange
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(orderRepository.findByCustomerIdAndStatus(1L, OrderStatus.ACCEPTED))
                .thenReturn(Collections.emptyList());

        //Act
        List<ActiveOrderCustomerDto> result = customerService.viewActiveOrders(1L);

        //Assert
        assertThat(result).isEmpty();
    }
}

