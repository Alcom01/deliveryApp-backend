package com.delivery.dvApp.repository;

import com.delivery.dvApp.entity.Restaurant;
import com.delivery.dvApp.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Restaurant} entities.
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
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    /**
     * Finds a restaurant by its name.
     *
     * @param name restaurant name
     * @return Optional containing the restaurant if found,
     *         or empty if no restaurant exists with the given name
     */
    Optional<Restaurant> findByName(String name);

    /**
     * Retrieves all restaurants belonging to a specific category.
     *
     * @param category restaurant category
     * @return list of restaurants matching the given category
     */
    List<Restaurant> findByCategory(Category category);

    /**
     * Retrieves all restaurants that are not soft-deleted.
     *
     * <p>
     * This method filters restaurants where the {@code deleted}
     * flag is set to false.
     * </p>
     *
     * @return list of active (non-deleted) restaurants
     */
    List<Restaurant> findByDeletedFalse();
}
