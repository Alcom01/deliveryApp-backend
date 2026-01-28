package com.delivery.dvApp.controller;

import com.delivery.dvApp.dto.ActiveOrderDto;
import com.delivery.dvApp.dto.DeliveredOrderDto;
import com.delivery.dvApp.entity.Courier;
import com.delivery.dvApp.service.CourierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for courier-related endpoints.
 *
 * <p>Provides APIs for managing couriers, viewing active deliveries,
 * and retrieving delivery history.</p>
 */
@RestController
@RequestMapping("courier")
public class CourierController {
     private CourierService courierService;

    /**
     * Constructs a CourierController with required service.
     *
     * @param courierService service handling courier business logic
     */
    public CourierController(CourierService courierService){
        this.courierService = courierService;

    }

    /**
     * Creates a new courier.
     *
     * @param courier courier entity to be created
     * @return courierId
     */
    @PostMapping("/add-courier")
    public Long addCourier(@RequestBody Courier courier){
         return courierService.addCourier(courier);

    }

    /**
     * Soft deletes a courier.
     *
     * <p>A courier cannot be deleted if they have active orders.</p>
     *
     * @param courierId ID of the courier
     */
    @DeleteMapping("/delete-courier")
    public void deleteCourier(@RequestParam Long courierId){
        courierService.deleteCourier(courierId);

    }

    /**
     * Retrieves active orders assigned to a courier.
     *
     * <p>Active orders are defined as orders with PICKED_UP status.</p>
     *
     * @param courierId ID of the courier
     * @return list of active order DTOs
     */
    @GetMapping("/view-activeOrders")
    public List<ActiveOrderDto> getActiveOrders(@RequestParam Long courierId){
        return courierService.getActiveOrders(courierId);
    }

    /**
     * Retrieves delivery history for a courier.
     *
     * <p>Only delivered orders are included.</p>
     *
     * @param courierId ID of the courier
     * @return list of delivered order DTOs
     */
    @GetMapping("/view-courierHistory")
    public List<DeliveredOrderDto> getCourierHistory(@RequestParam Long courierId){
        return courierService.getCourierHistory(courierId);

    }

}
