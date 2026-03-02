package com.corebank.domain.repository;

import com.corebank.domain.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <h2>Transaction Repository Contract</h2>
 * <p>
 * Domain abstraction for transaction persistence.
 * Follows the <b>Dependency Inversion Principle</b>: the domain declares
 * its required capabilities without knowing the implementation.
 * </p>
 */
public interface TransactionRepository {

    /**
     * Persists a new transaction record.
     */
    Transaction save(Transaction transaction);

    /**
     * Retrieves all transactions for a given account, ordered by creation time
     * descending.
     */
    List<Transaction> findByAccountId(UUID accountId);

    /**
     * Looks up a transaction by its idempotency key for duplicate detection.
     */
    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);
}
