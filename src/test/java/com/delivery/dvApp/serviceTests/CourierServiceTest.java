package com.delivery.dvApp.serviceTests;

import com.delivery.dvApp.dto.DeliveredOrderDto;
import com.delivery.dvApp.entity.Courier;
import com.delivery.dvApp.entity.Customer;
import com.delivery.dvApp.entity.Order;
import com.delivery.dvApp.entity.Restaurant;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.enums.Vehicle;
import com.delivery.dvApp.exception.custom.CourierNotFoundException;
import com.delivery.dvApp.exception.custom.OrderHistoryNotFoundException;
import com.delivery.dvApp.repository.CourierRepository;
import com.delivery.dvApp.repository.OrderRepository;
import com.delivery.dvApp.service.CourierService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CourierService}.
 * <p>
 * Focuses on courier creation and retrieving delivery history.
 */
@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {
    @Mock
    CourierRepository courierRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    CourierService courierService;

    /**
     * Verifies that a valid Courier entity is saved to the repository.
     */
    @Test
    void addCourier_shouldAddCourierSuccessfully(){
        //Arrange
        Courier courier = new Courier(
                "John Walter",
                Vehicle.CAR,
                151.15,158.15,
                "+48123456789");

        when(courierRepository.save(courier)).thenReturn(courier);

        //Act
        courierService.addCourier(courier);
        //Assert
        verify(courierRepository,times(1)).save(courier);
    }
    /**
     * Verifies retrieving delivery history for a courier.
     * <p>
     * <b>Scenario:</b> Courier exists and has completed one order (Status: DELIVERED).
     * <br><b>Expectation:</b>
     * <ul>
     * <li>Returns a list of {@link DeliveredOrderDto}.</li>
     * <li>Data maps correctly from Order/Restaurant/Customer entities to the DTO.</li>
     * </ul>
     */
    @Test
    void getCourierHistory_ShouldReturnDtoListIfCourierExistsAndHasHistory() {
       //Arrange
        Courier courier = new Courier();
        courier.setId(1L);


        Restaurant restaurant = new Restaurant();
        restaurant.setName("Burger King");

        Customer customer = new Customer();
        customer.setAddress("Zlota 59");

        Order order = new Order();
        order.setId(2L);
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setDeliveredAt(LocalDateTime.of(2025, 10, 5, 12, 0));
        order.setTotalPrice(BigDecimal.valueOf(25.50));


        when(courierRepository.findById(1L)).thenReturn(Optional.of(courier));
        when(orderRepository.findByCourierIdAndStatus(1L, OrderStatus.DELIVERED))
                .thenReturn(List.of(order));

        // Act
        List<DeliveredOrderDto> result = courierService.getCourierHistory(1L);

        // Assert
        assertEquals(result.size(),1);

        DeliveredOrderDto dto = result.get(0);
        assertEquals(dto.getOrderId(),2L);
        assertEquals(dto.getRestaurantName(),"Burger King");
        assertEquals(dto.getCustomerAddress(),"Zlota 59");
        assertEquals(dto.getTotalPrice(),BigDecimal.valueOf(25.50));

        verify(courierRepository).findById(1L);
        verify(orderRepository).findByCourierIdAndStatus(1L, OrderStatus.DELIVERED);
    }

    /**
     * Verifies exception handling when the courier ID does not exist.
     * <p>
     * <b>Expectation:</b> Throws {@link CourierNotFoundException}.
     */
    @Test
    void getCourierHistory_ShouldThrowException_IfCourierNotFound() {
        // Arrange
        Long courierId = 99L;
        when(courierRepository.findById(courierId)).thenReturn(Optional.empty());

        // Act and Assert
        CourierNotFoundException exception = assertThrows(CourierNotFoundException.class, () -> {
            courierService.getCourierHistory(courierId);
        });

        assertEquals(exception.getMessage(),"There is no such courier exists.");
    }

    /**
     * Verifies exception handling when the courier exists but has zero delivered orders.
     * <p>
     * <b>Expectation:</b> Throws {@link OrderHistoryNotFoundException}.
     */
    @Test
    void getCourierHistory_ShouldThrowExceptionIfThereIsNoDeliveryHistory() {
        // Arrange
        Courier courier = new Courier();
        courier.setId(10L);

        // Courier is found
        when(courierRepository.findById(10L)).thenReturn(Optional.of(courier));

        // But order list is empty
        when(orderRepository.findByCourierIdAndStatus(10L, OrderStatus.DELIVERED))
                .thenReturn(Collections.emptyList());

        // Act and Assert
        OrderHistoryNotFoundException exception = assertThrows(OrderHistoryNotFoundException.class, () -> {
            courierService.getCourierHistory(10L);
        });

        assertEquals(exception.getMessage(),"Delivery history not found.");
    }
}

