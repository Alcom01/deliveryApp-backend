package com.delivery.dvApp.repository;

import com.delivery.dvApp.entity.Order;
import com.delivery.dvApp.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Order} entities.
 *
 * <p>
 * Extends {@link JpaRepository}, providing standard CRUD operations
 * such as save, findById, findAll, delete, etc.
 * </p>
 *
 * <p>
 * Additional query methods are defined using Spring Data JPA
 * method name conventions.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

     /**
      * Retrieves all orders assigned to a specific courier with a given status.
      *
      * @param courierId ID of the courier
      * @param status    status of the orders to retrieve
      * @return list of orders matching the courier and status
      */
     List<Order> findByCourierIdAndStatus(Long courierId, OrderStatus status);

     /**
      * Retrieves all orders placed by a specific customer with a given status.
      *
      * @param customerId ID of the customer
      * @param status     status of the orders to retrieve
      * @return list of orders matching the customer and status
      */
     List<Order> findByCustomerIdAndStatus(Long customerId,OrderStatus status);

     /**
      * Checks if a courier has any orders in the given list of active statuses.
      *
      * @param courierId      ID of the courier
      * @param activeStatuses list of active order statuses
      * @return true if the courier has any orders in the specified statuses, false otherwise
      */
     boolean existsByCourierIdAndStatusIn(Long courierId, List<OrderStatus> activeStatuses);

     /**
      * Checks if a customer has any orders in the given list of active statuses.
      *
      * @param customerId     ID of the customer
      * @param activeStatuses list of active order statuses
      * @return true if the customer has any orders in the specified statuses, false otherwise
      */
     boolean existsByCustomerIdAndStatusIn(Long customerId, List<OrderStatus> activeStatuses);
}
