package com.delivery.dvApp.service;

import com.delivery.dvApp.dto.ActiveOrderCustomerDto;
import com.delivery.dvApp.dto.ItemDetailsDto;
import com.delivery.dvApp.dto.OrderHistoryDto;
import com.delivery.dvApp.entity.Customer;
import com.delivery.dvApp.entity.Order;
import com.delivery.dvApp.entity.OrderItem;
import com.delivery.dvApp.enums.OrderStatus;
import com.delivery.dvApp.exception.custom.CustomerNotFoundException;
import com.delivery.dvApp.exception.custom.OrderExistsException;
import com.delivery.dvApp.repository.CustomerRepository;
import com.delivery.dvApp.repository.ItemRepository;
import com.delivery.dvApp.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service layer responsible for customer-related operations.
 *
 * <p>This service manages customer creation, soft deletion,
 * viewing active orders, and retrieving order history.</p>
 */
@Service
public class CustomerService {

    private  final CustomerRepository customerRepository;
    private final  OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * Constructs a CustomerService with required repositories.
     *
     * @param customerRepository repository for customers
     * @param orderRepository repository for orders
     * @param itemRepository repository for items
     */
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository,ItemRepository itemRepository){
          this.customerRepository = customerRepository;
          this.orderRepository = orderRepository;
          this.itemRepository = itemRepository;
    }

    /**
     * Adds a new customer.
     *
     * @param customer customer entity to be saved
     */
    public Long addCustomer(Customer customer){
         customerRepository.save(customer);
         return customer.getId();
    }

    /**
     * Soft deletes a customer.
     *
     * <p>A customer cannot be deleted if they have active orders
     * (CREATED, ACCEPTED, or PICKED_UP).</p>
     *
     * @param customerId ID of the customer
     * @throws CustomerNotFoundException if customer does not exist
     * @throws OrderExistsException if customer has active orders
     */
    public void deleteCustomer(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist."));

        // Check for ACTIVE orders
        List<OrderStatus> activeStatuses = List.of(OrderStatus.CREATED, OrderStatus.ACCEPTED, OrderStatus.PICKED_UP);
        boolean hasActiveOrders = orderRepository.existsByCustomerIdAndStatusIn(customerId, activeStatuses);

        if (hasActiveOrders) {
            throw new OrderExistsException("Cannot delete customer because they have an active order.");
        }

        // Soft delete
        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    /**
     * Retrieves all active orders for a customer.
     *
     * <p>Currently considers orders with {@link OrderStatus#ACCEPTED}
     * status as active.</p>
     *
     * @param customerId ID of the customer
     * @return list of active order DTOs
     * @throws CustomerNotFoundException if customer does not exist
     */
    @Transactional
    public List<ActiveOrderCustomerDto> viewActiveOrders(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found."));

        List<Order> orders= orderRepository.findByCustomerIdAndStatus(customerId, OrderStatus.ACCEPTED);

        List<ActiveOrderCustomerDto> activeOrderCustomerDtos = new ArrayList<>();
        for(Order order : orders){
            ActiveOrderCustomerDto activeOrderCustomerDto = new ActiveOrderCustomerDto();
            activeOrderCustomerDto.setCourierName(order.getCourier().getName());
            activeOrderCustomerDto.setCourierNumber(order.getCourier().getPhoneNumber());
            activeOrderCustomerDto.setTotal(order.getTotalPrice());
            activeOrderCustomerDto.setRestaurantName(order.getRestaurant().getName());
            activeOrderCustomerDto.setCreatedAt(order.getCreatedAt());


            List<OrderItem> orderItems = order.getOrderItems();
            List<ItemDetailsDto> itemDetailsDtos = new ArrayList<>();
            for(OrderItem orderItem : orderItems){
                ItemDetailsDto itemDetailsDto = new ItemDetailsDto();
                itemDetailsDto.setItemName(orderItem.getItem().getName());
                itemDetailsDto.setQuantity(orderItem.getQuantity());
                itemDetailsDtos.add(itemDetailsDto);
            }
            activeOrderCustomerDto.setItemDetailsDtos(itemDetailsDtos);
            activeOrderCustomerDtos.add(activeOrderCustomerDto);
        }
        return activeOrderCustomerDtos;


    }

    /**
     * Retrieves the order history for a customer.
     *
     * <p>Only orders with {@link OrderStatus#DELIVERED} status
     * are included in the history.</p>
     *
     * @param customerId ID of the customer
     * @return list of delivered order history DTOs
     * @throws CustomerNotFoundException if customer does not exist
     */
    @Transactional
    public List<OrderHistoryDto> viewOrderHistory(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found."));

        List<Order> orders = orderRepository.findByCustomerIdAndStatus(customerId,OrderStatus.DELIVERED);
        List<OrderHistoryDto> orderHistory = new ArrayList<>();

        for(Order order : orders){
            OrderHistoryDto orderHistoryDto = new OrderHistoryDto();
            orderHistoryDto.setRestaurantName(order.getRestaurant().getName());
            orderHistoryDto.setCourierName(order.getCourier().getName());
            orderHistoryDto.setCourierNumber(order.getCourier().getPhoneNumber());
            orderHistoryDto.setDeliveredAt(order.getDeliveredAt());
            orderHistoryDto.setTotal(order.getTotalPrice());

            List<OrderItem> orderItems = order.getOrderItems();
            List<ItemDetailsDto> itemDetailsDtos = new ArrayList<>();
            for(OrderItem orderItem : orderItems){
                ItemDetailsDto itemDetailsDto = new ItemDetailsDto();
                itemDetailsDto.setItemName(orderItem.getItem().getName());
                itemDetailsDto.setQuantity(orderItem.getQuantity());
                itemDetailsDtos.add(itemDetailsDto);
            }
            orderHistoryDto.setItemDetailsDtos(itemDetailsDtos);
            orderHistory.add(orderHistoryDto);
        }
        return orderHistory;
    }
}
