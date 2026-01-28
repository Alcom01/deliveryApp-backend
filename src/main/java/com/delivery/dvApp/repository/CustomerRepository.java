package com.delivery.dvApp.repository;

import com.delivery.dvApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link Customer} entities.
 *
 * <p>
 * Extends {@link JpaRepository}, providing standard CRUD operations
 * such as save, findById, findAll, delete, etc.
 * </p>
 *
 * <p>
 * No additional custom query methods are defined for now.
 * All basic operations are inherited from JpaRepository.
 * </p>
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
