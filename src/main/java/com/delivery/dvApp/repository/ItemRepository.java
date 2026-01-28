package com.delivery.dvApp.repository;

import com.delivery.dvApp.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing {@link Item} entities.
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
public interface ItemRepository extends JpaRepository<Item,Long> {

    /**
     * Retrieves all items associated with a specific restaurant.
     *
     * @param restaurantID ID of the restaurant
     * @return Optional containing a list of items belonging to the restaurant,
     *         or empty if no items exist
     */
    Optional<List<Item>> findByRestaurantId(Long restaurantID);

}
