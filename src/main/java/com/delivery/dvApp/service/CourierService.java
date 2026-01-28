package com.delivery.dvApp.service;

import com.delivery.dvApp.dto.ActiveOrderDto;
import com.delivery.dvApp.dto.DeliveredOrderDto;
import com.delivery.dvApp.entity.Courier;
import com.delivery.dvApp.entity.Order;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.exception.custom.ActiveOrderNotFoundException;
import com.delivery.dvApp.exception.custom.CourierNotFoundException;
import com.delivery.dvApp.exception.custom.OrderExistsException;
import com.delivery.dvApp.exception.custom.OrderHistoryNotFoundException;
import com.delivery.dvApp.repository.CourierRepository;
import com.delivery.dvApp.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service layer responsible for courier-related operations.
 *
 * <p>This service handles courier creation, soft deletion,
 * viewing active deliveries, and retrieving delivery history.</p>
 */
@Service
public class CourierService {
    private final CourierRepository courierRepository;
    private  final OrderRepository orderRepository;

    /**
     * Constructs a CourierService with required repositories.
     *
     * @param courierRepository repository for couriers
     * @param orderRepository repository for orders
     */
    public CourierService(CourierRepository courierRepository, OrderRepository orderRepository) {
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Adds a new courier.
     *
     * @param courier courier entity to be saved
     */
    public Long addCourier(Courier courier) {
        courierRepository.save(courier);

        return courier.getId();

    }


    /**
     * Soft deletes a courier.
     *
     * <p>A courier cannot be deleted if they have active orders
     * (ACCEPTED or PICKED_UP).</p>
     *
     * @param courierId ID of the courier
     * @throws CourierNotFoundException if courier does not exist
     * @throws OrderExistsException if courier has active orders
     */
    @Transactional
    public void deleteCourier(Long courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("There is no such courier exists"));


        List<OrderStatus> activeStatuses = List.of(OrderStatus.ACCEPTED, OrderStatus.PICKED_UP);
        boolean hasActiveOrders = orderRepository.existsByCourierIdAndStatusIn(courierId, activeStatuses);

        if (hasActiveOrders) {
            throw new OrderExistsException("Courier cannot be deleted because they have active orders in progress.");
        }

        courier.setDeleted(true);
        courierRepository.save(courier);
    }

    /**
     * Retrieves all active orders assigned to a courier.
     *
     * <p>Only orders with {@link OrderStatus#PICKED_UP} status
     * are considered active deliveries.</p>
     *
     * @param courierId ID of the courier
     * @return list of active order DTOs
     * @throws CourierNotFoundException if courier does not exist
     * @throws ActiveOrderNotFoundException if no active orders are found
     */
   @Transactional
    public List<ActiveOrderDto> getActiveOrders(Long courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(()-> new CourierNotFoundException("Courier does not exists."));
        List<Order> orders = orderRepository.findByCourierIdAndStatus(courierId,OrderStatus.PICKED_UP);

        if(orders.isEmpty()){
            throw new ActiveOrderNotFoundException("There is no active orders");
        }

        List<ActiveOrderDto> activeOrders = new ArrayList<>();


        for(Order order : orders){
                ActiveOrderDto activeOrderDto = new ActiveOrderDto();
                activeOrderDto.setRestaurantName(order.getRestaurant().getName());
                activeOrderDto.setPickedAt(order.getPickedAt());
                activeOrderDto.setCustomerAddress(order.getCustomer().getAddress());
                activeOrderDto.setTotalPrice(order.getTotalPrice());
                activeOrderDto.setCustomerNumber(order.getCustomer().getPhoneNumber());
                activeOrderDto.setOrderId(order.getId());
                activeOrders.add(activeOrderDto);



        }

        return activeOrders;


    }

    /**
     * Retrieves delivery history for a courier.
     *
     * <p>Only orders with {@link OrderStatus#DELIVERED} status
     * are included in the delivery history.</p>
     *
     * @param courierId ID of the courier
     * @return list of delivered order DTOs
     * @throws CourierNotFoundException if courier does not exist
     * @throws OrderHistoryNotFoundException if no delivery history exists
     */
    @Transactional
    public List<DeliveredOrderDto> getCourierHistory(Long courierId) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException("There is no such courier exists."));

        List<Order> orders = orderRepository.findByCourierIdAndStatus(courierId,OrderStatus.DELIVERED);

        if(orders.isEmpty()){
            throw new OrderHistoryNotFoundException("Delivery history not found.");
        }


        List<DeliveredOrderDto> deliveredOrders = new ArrayList<>();

        for (Order order : orders){
                DeliveredOrderDto deliveredOrder =  new DeliveredOrderDto();
                deliveredOrder.setRestaurantName(order.getRestaurant().getName());
                deliveredOrder.setCustomerAddress(order.getCustomer().getAddress());
                deliveredOrder.setDeliveredAt(order.getDeliveredAt());
                deliveredOrder.setTotalPrice(order.getTotalPrice());
                deliveredOrder.setOrderId(order.getId());

                deliveredOrders.add(deliveredOrder);

        }
        return deliveredOrders;
    }
}

