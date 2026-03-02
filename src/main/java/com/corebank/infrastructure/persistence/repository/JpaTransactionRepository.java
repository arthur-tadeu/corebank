package com.corebank.infrastructure.persistence.repository;

import com.corebank.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA interface for transaction persistence.
 */
public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByAccountIdOrderByCreatedAtDesc(UUID accountId);

    Optional<TransactionEntity> findByIdempotencyKey(String idempotencyKey);
}
