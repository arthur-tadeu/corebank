package com.corebank.domain.repository;

import com.corebank.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

/**
 * <h2>Customer Repository Contract</h2>
 * <p>
 * Domain abstraction for customer persistence.
 * </p>
 */
public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    Optional<Customer> findByDocument(String document);
}
