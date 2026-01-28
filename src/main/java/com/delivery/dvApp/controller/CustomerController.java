package com.delivery.dvApp.controller;

import com.delivery.dvApp.dto.ActiveOrderCustomerDto;
import com.delivery.dvApp.dto.OrderHistoryDto;
import com.delivery.dvApp.entity.Customer;
import com.delivery.dvApp.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller responsible for customer-related endpoints.
 *
 * <p>Provides APIs for managing customers, viewing active orders,
 * and retrieving order history.</p>
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    private CustomerService customerService;

    /**
     * Constructs a CustomerController with required service.
     *
     * @param customerService service handling customer business logic
     */
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    /**
     * Creates a new customer.
     *
     * @param customer customer entity to be created
     */
    @PostMapping("/add-customer")
    public Long addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);


    }


    /**
     * Soft deletes a customer.
     *
     * <p>A customer cannot be deleted if they have active orders.</p>
     *
     * @param customerId ID of the customer
     */
    @DeleteMapping("/delete-customer")
    public void deleteCustomer(@RequestParam Long customerId){
         customerService.deleteCustomer(customerId);
    }

    /**
     * Retrieves active orders for a customer.
     *
     * <p>Active orders are currently defined as orders
     * with ACCEPTED status.</p>
     *
     * @param customerId ID of the customer
     * @return list of active order DTOs
     */
    @GetMapping("/view-activeOrders")
    public List<ActiveOrderCustomerDto> viewActiveOrders(@RequestParam Long customerId){
        return customerService.viewActiveOrders(customerId);

    }
    /**
     * Retrieves order history for a customer.
     *
     * <p>Only delivered orders are included in the history.</p>
     *
     * @param customerId ID of the customer
     * @return list of order history DTOs
     */
    @GetMapping("/view-orderHistory")
    public List<OrderHistoryDto> viewOrderHistory(@RequestParam Long customerId){
        return customerService.viewOrderHistory(customerId);
    }
}
