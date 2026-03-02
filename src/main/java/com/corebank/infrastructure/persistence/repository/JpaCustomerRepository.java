package com.corebank.infrastructure.persistence.repository;

import com.corebank.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA interface for customer persistence.
 */
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByDocument(String document);
}
