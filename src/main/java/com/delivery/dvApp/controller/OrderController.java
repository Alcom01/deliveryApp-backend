package com.delivery.dvApp.controller;

import com.delivery.dvApp.dto.OrderRequestDto;
import com.delivery.dvApp.dto.ReceiptDto;
import com.delivery.dvApp.entity.Order;
import com.delivery.dvApp.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")

/**
 * REST controller responsible for order-related endpoints.
 *
 * <p>Provides APIs for creating orders and managing
 * order lifecycle transitions such as acceptance,
 * pickup, delivery, and cancellation.</p>
 */
public class OrderController {
    private OrderService orderService;

    /**
     * Constructs an OrderController with required service.
     *
     * @param orderService service handling order business logic
     */
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    /**
     * Creates a new order.
     *
     * <p>This endpoint validates restaurant, customer, courier,
     * and items, then returns a receipt for the created order.</p>
     *
     * @param orderRequestDto request payload containing order details
     * @return receipt containing order summary and total price
     */
    @PostMapping("/createOrder")
    public ReceiptDto createOrder(@RequestBody OrderRequestDto orderRequestDto){
         return orderService.createOrder(orderRequestDto);

    }

    /**
     * Accepts an order.
     *
     * <p>Only orders with CREATED status can be accepted.</p>
     *
     * @param id ID of the order
     * @return updated order
     */
    @PutMapping("/{id}/accept")
    public Order acceptOrder(@PathVariable Long id){
        return orderService.acceptOrder(id);

    }

    /**
     * Marks an order as picked up by the courier.
     *
     * <p>Order must be in ACCEPTED state.</p>
     *
     * @param id ID of the order
     * @return updated order
     */
    @PutMapping("/{id}/pickup")
    public Order pickUp(@PathVariable Long id){
        return  orderService.pickUpOrder(id);
    }

    /**
     * Marks an order as delivered.
     *
     * <p>Order must be in PICKED_UP state.</p>
     *
     * @param id ID of the order
     * @return updated order
     */
    @PutMapping("/{id}/deliver")
    public Order deliver(@PathVariable Long id){
        return orderService.deliverOrder(id);
    }

    /**
     * Cancels an order.
     *
     * <p>Delivered orders cannot be cancelled.</p>
     *
     * @param id ID of the order
     * @return updated order
     */
    @PutMapping("/{id}/cancel")
    public Order cancel(@PathVariable Long id){
        return orderService.cancelOrder(id);
    }

}
