package com.delivery.dvApp.serviceTests;

import com.delivery.dvApp.dto.ItemQuantityDto;
import com.delivery.dvApp.dto.OrderRequestDto;
import com.delivery.dvApp.dto.ReceiptDto;
import com.delivery.dvApp.entity.*;
import com.delivery.dvApp.enums.Category;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.enums.Vehicle;
import com.delivery.dvApp.exception.custom.InvalidOrderStatusException;
import com.delivery.dvApp.repository.*;
import com.delivery.dvApp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link OrderService} class.
 * <p>
 * This class uses JUnit 5 and Mockito to verify business logic, including:
 * <ul>
 * <li>Order creation and total price calculation.</li>
 * <li>Validation of item ownership (Restaurant vs Item).</li>
 * <li>Order lifecycle state transitions (Created -> Accepted -> PickedUp -> Delivered).</li>
 * <li>Exception handling for invalid state transitions.</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;



    /**
     * Verifies that a valid {@link OrderRequestDto} results in a successfully saved Order.
     * <p>
     * <b>Scenario:</b> A customer orders 2 burgers (25.00 each) and 1 fries (15.00).
     * <br><b>Expectation:</b>
     * <ul>
     * <li>Order is saved with status {@code CREATED}.</li>
     * <li>Total price is correctly calculated as 65.00.</li>
     * <li>Correct associations (Restaurant, Customer, Courier) are set.</li>
     * <li>A correct {@link ReceiptDto} is returned.</li>
     * </ul>
     */
    @Test
    void createOrder_shouldCreateOrderWithValidItems(){
        //  Arrange
        Restaurant restaurant = new Restaurant(
                "Vegan Place",
                "Aleja Parisowska 99/15",
                "+48111222333",
                Category.VEGAN
        );
        restaurant.setId(1L);

        Customer customer = new Customer(
                "John",
                "Aleja Disney 13/64 02-888",
                "+48999888777"
        );
        customer.setId(2L);

        Courier courier = new Courier(
                "Alex",
                Vehicle.MOTORBIKE,
                50.0,
                20.0,
                "+48555444333222"
        );
        courier.setId(3L);

        Item item1 = new Item(
                "Vegan Burger",
                "Plant based",
                restaurant,
                BigDecimal.valueOf(25)
        );
        item1.setId(10L);

        Item item2 = new Item(
                "Vegan Fries",
                "Crispy",
                restaurant,
                BigDecimal.valueOf(15)
        );
        item2.setId(11L);

        ItemQuantityDto q1 = new ItemQuantityDto();
        q1.setItemId(10L);
        q1.setQuantity(2);

        ItemQuantityDto q2 = new ItemQuantityDto();
        q2.setItemId(11L);
        q2.setQuantity(1);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setRestaurantId(1L);
        requestDto.setCustomerId(2L);
        requestDto.setCourierId(3L);
        requestDto.setItemToQuantities(List.of(q1, q2));

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
        when(courierRepository.findById(3L)).thenReturn(Optional.of(courier));
        when(itemRepository.findById(10L)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(11L)).thenReturn(Optional.of(item2));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        //  Act
        ReceiptDto receipt = orderService.createOrder(requestDto);

        // Assert
        verify(orderRepository).save(orderCaptor.capture());
        verify(orderItemRepository, times(2)).save(any(OrderItem.class));

        Order savedOrder = orderCaptor.getValue();

        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());
        assertEquals(BigDecimal.valueOf(65), savedOrder.getTotalPrice()); // 25*2 + 15*1
        assertEquals(restaurant, savedOrder.getRestaurant());
        assertEquals(customer, savedOrder.getCustomer());
        assertEquals(courier, savedOrder.getCourier());
        assertNotNull(savedOrder.getCreatedAt());

        assertEquals("Vegan Place", receipt.getRestaurantName());
        assertEquals(BigDecimal.valueOf(65), receipt.getTotal());
        assertEquals(2, receipt.getItemDetails().size());
        assertNotNull(receipt.getCreatedAt());
    }

    /**
     * Verifies business logic integrity regarding Item-Restaurant ownership.
     * <p>
     * <b>Scenario:</b> A user attempts to order an Item that belongs to Restaurant B,
     * but the Order is initiated for Restaurant A.
     * <br><b>Expectation:</b> A {@link RuntimeException} is thrown with the message
     * "This item does not belong to this restaurant".
     */
    @Test
    void shouldThrowExceptionWhenItemDoesNotBelongToRestaurant() {
        // GIVEN
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);

        Customer customer = new Customer();
        customer.setId(1L);

        Courier courier = new Courier();
        courier.setId(1L);

        Item item = new Item();
        item.setId(10L);
        item.setRestaurant(restaurant2); // ❌ different restaurant
        item.setPrice(BigDecimal.valueOf(20));

        ItemQuantityDto itemQuantityDto = new ItemQuantityDto();
        itemQuantityDto.setItemId(10L);
        itemQuantityDto.setQuantity(1);

        OrderRequestDto requestDto = new OrderRequestDto(
                1L,
                1L,
                1L,
                List.of(itemQuantityDto)
        );

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant1));
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));
        when(courierRepository.findById(1L))
                .thenReturn(Optional.of(courier));
        when(itemRepository.findById(10L))
                .thenReturn(Optional.of(item));

        // THEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.createOrder(requestDto)
        );

        assertEquals("This item does not belong to this restaurant", exception.getMessage());
    }

    /**
     * Verifies complex price calculation logic.
     * <p>
     * <b>Scenario:</b>
     * <ul>
     * <li>2x Kebab (45.00) = 90.00</li>
     * <li>1x Fries (10.00) = 10.00</li>
     * <li>1x Ayran (6.00) = 6.00</li>
     * </ul>
     * <b>Expectation:</b> Total price matches exactly 106.00.
     */
    @Test
    void createOrder_shouldCalculateTotalCorrect(){
        //Arrange
        Restaurant restaurant = new Restaurant(
                "Adana Kebab",
                "Plac Wilsona 78/12 05-178",
                "+48333444555"
                ,Category.KEBAB);
        restaurant.setId(1L);

        Customer customer = new Customer(
                "John",
                "Aleja Disney 13/64 02-888",
                "+48999888777"
        );
        customer.setId(2L);

        Courier courier = new Courier(
                "Alex",
                Vehicle.MOTORBIKE,
                50.0,
                20.0,
                "+48555444333222"
        );
        courier.setId(3L);

        Item item1 = new Item(
                "XXL KEBAB",
                "A Delicious Kebab",
                restaurant,
                BigDecimal.valueOf(45)
        );
        item1.setId(10L);

        Item item2 = new Item(
                "French Fries",
                "Crispy,salty and tasy fires",
                restaurant,
                BigDecimal.valueOf(10)
        );
        item2.setId(11L);


        Item item3 = new Item(
                "Ayran",
                "Refreshing drink",
                restaurant,
                BigDecimal.valueOf(6)
        );
        item3.setId(12L);

        ItemQuantityDto q1 = new ItemQuantityDto();
        q1.setItemId(10L);
        q1.setQuantity(2);

        ItemQuantityDto q2 = new ItemQuantityDto();
        q2.setItemId(11L);
        q2.setQuantity(1);

        ItemQuantityDto q3 = new ItemQuantityDto();
        q3.setItemId(12L);
        q3.setQuantity(1);

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setRestaurantId(1L);
        requestDto.setCustomerId(2L);
        requestDto.setCourierId(3L);
        requestDto.setItemToQuantities(List.of(q1, q2,q3));

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
        when(courierRepository.findById(3L)).thenReturn(Optional.of(courier));
        when(itemRepository.findById(10L)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(11L)).thenReturn(Optional.of(item2));
        when(itemRepository.findById(12L)).thenReturn(Optional.of(item3));

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        //Act
        ReceiptDto receipt = orderService.createOrder(requestDto);

        verify(orderRepository).save(orderCaptor.capture());
        verify(orderItemRepository, times(3)).save(any(OrderItem.class));

        Order savedOrder = orderCaptor.getValue();

        assertEquals(BigDecimal.valueOf(106), savedOrder.getTotalPrice());

        assertEquals(BigDecimal.valueOf(106), receipt.getTotal());

    }

    /**
     * Verifies the state transition from {@code CREATED} to {@code ACCEPTED}.
     */
    @Test
    void acceptOrder_shouldChangeStatusFromCreatedToAccepted(){
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order acceptedOrder = orderService.acceptOrder(1L);

        // Assert
        assertEquals(OrderStatus.ACCEPTED, acceptedOrder.getStatus());
        verify(orderRepository).save(order);
    }

    /**
     * Verifies the state transition from {@code ACCEPTED} to {@code PICKED_UP}.
     */
    @Test
    void pickUpOrder_ShouldChangeStatusFromAcceptedToPickedUp(){
        //Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.ACCEPTED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation ->invocation.getArgument(0));

        //Act
        Order pickedUpOrder = orderService.pickUpOrder(1L);

        //Assert
        assertEquals(OrderStatus.PICKED_UP,pickedUpOrder.getStatus());
        verify(orderRepository).save(order);
    }

    /**
     * Verifies the state transition from {@code PICKED_UP} to {@code DELIVERED}.
     */
    @Test
    void deliverOrder_ShouldChangeStatusFromPickedUpToDelivered(){

        //Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PICKED_UP);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation ->invocation.getArgument(0));

        //Act
        Order deliveredOrder = orderService.deliverOrder(1L);

        //Assert
        assertEquals(OrderStatus.DELIVERED,deliveredOrder.getStatus());
        verify(orderRepository).save(order);

    }

    /**
     * Verifies that an order can be cancelled if it has not yet been delivered.
     * <p>
     * <b>Scenario:</b> Order is currently {@code PICKED_UP} (or Created/Accepted).
     * <br><b>Expectation:</b> Status changes to {@code CANCELLED}.
     */
    @Test
    void cancelOrder_ShouldCancelOrderIfItIsNotDelivered(){
        //Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PICKED_UP);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation ->invocation.getArgument(0));

        //Act
        Order cancelledOrder = orderService.cancelOrder(1L);

        //Assert
        assertEquals(OrderStatus.CANCELLED,cancelledOrder.getStatus());
        verify(orderRepository).save(order);

    }

    /**
     * Verifies that a {@code DELIVERED} order cannot be cancelled.
     * <p>
     * <b>Expectation:</b> An {@link InvalidOrderStatusException} is thrown.
     */
    @Test
    void cancelOrder_ShouldThrowExceptionIfItOrderDelivered(){
        //GIVEN
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.DELIVERED); // It has to be CREATED OR ACCEPTED in order to cancel the order

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

      //THEN
        InvalidOrderStatusException exception = assertThrows(
                InvalidOrderStatusException.class,
                () -> orderService.cancelOrder(1L)
        );

        assertEquals("Delivered order cannot be cancelled", exception.getMessage());
    }


    /**
     * Verifies strict order flow enforcement: an order cannot be delivered
     * unless it has previously been marked as {@code PICKED_UP}.
     * <p>
     * <b>Scenario:</b> Attempting to deliver an {@code ACCEPTED} order.
     * <br><b>Expectation:</b> An {@link InvalidOrderStatusException} is thrown.
     */
    @Test
    void deliverOrder_ShouldThrowException_IfOrderNotPickedUp() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.ACCEPTED); // Order has to be PICKED_UP before delivery

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        InvalidOrderStatusException exception = assertThrows(
                InvalidOrderStatusException.class,
                () -> orderService.deliverOrder(1L)
        );

        assertEquals("Order Must be PICKED_UP before delivery.", exception.getMessage());
    }

}
