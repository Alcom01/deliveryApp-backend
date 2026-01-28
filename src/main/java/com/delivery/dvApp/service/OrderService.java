package com.delivery.dvApp.service;

import com.delivery.dvApp.dto.ItemDetailsDto;
import com.delivery.dvApp.dto.ItemQuantityDto;
import com.delivery.dvApp.dto.OrderRequestDto;
import com.delivery.dvApp.dto.ReceiptDto;
import com.delivery.dvApp.entity.*;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.exception.custom.*;
import com.delivery.dvApp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

/**
 * Service layer responsible for handling order-related business logic.
 *
 * <p>This service manages order creation, validation, status transitions
 * (accept, pickup, delivery, cancellation), and receipt generation.</p>
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final ItemRepository itemRepository;
    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;


    /**
     * Constructs an OrderService with required repositories.
     *
     * @param orderRepository repository for orders
     * @param restaurantRepository repository for restaurants
     * @param itemRepository repository for items
     * @param courierRepository repository for couriers
     * @param customerRepository repository for customers
     * @param orderItemRepository repository for order items
     */
    public OrderService(OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        ItemRepository itemRepository,
                        CourierRepository courierRepository,
                        CustomerRepository customerRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.itemRepository = itemRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
    }


    /**
     * Creates a new order and generates a receipt.
     *
     * <p>This method validates the restaurant, customer, courier, items,
     * quantities, and ensures all items belong to the same restaurant.</p>
     *
     * <p>The order is initially created with {@link OrderStatus#CREATED} status.</p>
     *
     * @param orderRequestDto request containing restaurant, customer, courier,
     *                        and item quantity details
     * @return generated receipt for the created order
     * @throws RestaurantNotFoundException if restaurant does not exist
     * @throws CustomerNotFoundException if customer does not exist
     * @throws CourierNotFoundException if courier does not exist
     * @throws ItemNotFoundException if an item does not exist
     * @throws ItemMisMatchException if an item does not belong to the restaurant
     * @throws InvalidQuantityException if quantity is less than 1
     */
    @Transactional
    public ReceiptDto createOrder(OrderRequestDto orderRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId())
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant Not Found"));

        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found."));

        Courier courier = courierRepository.findById(orderRequestDto.getCourierId())
                .orElseThrow(()-> new CourierNotFoundException("Courier Not Found."));


            Order order = new Order();
            order.setRestaurant(restaurant);
            order.setCustomer(customer);


             BigDecimal totalSum = BigDecimal.ZERO;

             List<OrderItem> orderItems = new ArrayList<>();

             List<ItemQuantityDto>   itemQuantities = orderRequestDto.getItemToQuantities();

             ReceiptDto receiptDto = new ReceiptDto();


             List<ItemDetailsDto> itemDetails = new ArrayList<>();


             for(ItemQuantityDto itemQuantityDto : itemQuantities){
                     Item item = itemRepository.findById(itemQuantityDto.getItemId())
                             .orElseThrow(() -> new ItemNotFoundException("Item Not Found"));


                     if (!item.getRestaurant().getId().equals(restaurant.getId())){
                         throw new ItemMisMatchException("This item does not belong to this restaurant");
                     }


                     if(itemQuantityDto.getQuantity() <= 0){
                         throw new InvalidQuantityException("Quantity cannot be less than 1.");

                     }

                     totalSum = totalSum.add(item.getPrice().multiply(BigDecimal.valueOf(itemQuantityDto.getQuantity())));

                     OrderItem orderItem = new OrderItem();
                     orderItem.setOrder(order);
                     orderItem.setItem(item);
                     orderItem.setQuantity(itemQuantityDto.getQuantity());
                     orderItem.setPriceAtPurchase(item.getPrice());

                     orderItems.add(orderItem);
                     orderItemRepository.save(orderItem);


                     ItemDetailsDto itemDetailsDto = new ItemDetailsDto();
                     itemDetailsDto.setItemName(item.getName());
                     itemDetailsDto.setQuantity(itemQuantityDto.getQuantity());

                     itemDetails.add(itemDetailsDto);


             }

             order.setTotalPrice(totalSum);
             order.setCourier(courier);
             order.setOrderItems(orderItems);
             order.setStatus(OrderStatus.CREATED);
             order.setCreatedAt(LocalDateTime.now());
             orderRepository.save(order);


             receiptDto.setRestaurantName(restaurant.getName());
             receiptDto.setTotal(totalSum);
             receiptDto.setItemDetails(itemDetails);
             receiptDto.setCreatedAt(order.getCreatedAt());
             receiptDto.setOrderId(order.getId());



             return receiptDto;
    }


    /**
     * Accepts an order.
     *
     * <p>Only orders with {@link OrderStatus#CREATED} status can be accepted.</p>
     *
     * @param orderId ID of the order
     * @return updated order
     * @throws OrderNotFoundException if order does not exist
     * @throws InvalidOrderStatusException if order status is invalid
     */
    @Transactional
    public Order acceptOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found."));

        if(order.getStatus() != OrderStatus.CREATED){
            throw new InvalidOrderStatusException("Only CREATED orders can be accepted.");
        }

        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    /**
     * Marks an order as picked up by the courier.
     *
     * <p>Order must be in {@link OrderStatus#ACCEPTED} state.</p>
     *
     * @param orderId ID of the order
     * @return updated order
     * @throws OrderNotFoundException if order does not exist
     * @throws InvalidOrderStatusException if order status is invalid
     */
    @Transactional
    public Order pickUpOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found"));


        if(order.getStatus() != OrderStatus.ACCEPTED){
            throw new InvalidOrderStatusException("Order must be ACCEPTED before pickup");
        }
        order.setStatus(OrderStatus.PICKED_UP);
        order.setPickedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    /**
     * Marks an order as delivered.
     *
     * <p>Order must be in {@link OrderStatus#PICKED_UP} state.</p>
     *
     * @param orderId ID of the order
     * @return updated order
     * @throws OrderNotFoundException if order does not exist
     * @throws InvalidOrderStatusException if order status is invalid
     */
    @Transactional
    public Order deliverOrder(Long orderId){
        Order order  = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));

        if(order.getStatus() != OrderStatus.PICKED_UP){
            throw new InvalidOrderStatusException("Order Must be PICKED_UP before delivery.");
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        return orderRepository.save(order);
    }


    /**
     * Cancels an order.
     *
     * <p>Delivered orders cannot be cancelled.</p>
     *
     * @param orderId ID of the order
     * @return updated order
     * @throws OrderNotFoundException if order does not exist
     * @throws InvalidOrderStatusException if order has already been delivered
     */
    @Transactional
    public Order cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order Not Found."));

        if(order.getStatus() == OrderStatus.DELIVERED){
            throw new InvalidOrderStatusException("Delivered order cannot be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        return orderRepository.save(order);
    }


    }

