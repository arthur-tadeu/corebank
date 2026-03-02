package com.corebank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Transaction Value Object</h2>
 * <p>
 * An <b>immutable</b> record of a financial movement against an account.
 * Transactions are append-only — once created, they are never modified or
 * deleted.
 * </p>
 *
 * <h3>Design Decision: Immutability</h3>
 * <p>
 * Financial ledger entries must be immutable to maintain audit integrity.
 * The {@code balanceAfter} field provides a snapshot for reconciliation,
 * enabling point-in-time balance reconstruction without replaying all events.
 * </p>
 */
public class Transaction {
    private final UUID id;
    private final UUID accountId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal balanceAfter;
    private final String description;
    private final String idempotencyKey;
    private final LocalDateTime createdAt;

    public Transaction(UUID id, UUID accountId, TransactionType type, BigDecimal amount,
            BigDecimal balanceAfter, String description, String idempotencyKey,
            LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = createdAt;
    }

    /**
     * Factory method for creating a new deposit transaction record.
     */
    public static Transaction createDeposit(UUID accountId, BigDecimal amount,
            BigDecimal balanceAfter, String description,
            String idempotencyKey) {
        return new Transaction(
                UUID.randomUUID(), accountId, TransactionType.DEPOSIT,
                amount, balanceAfter, description, idempotencyKey, LocalDateTime.now());
    }

    /**
     * Factory method for creating a new withdrawal transaction record.
     */
    public static Transaction createWithdrawal(UUID accountId, BigDecimal amount,
            BigDecimal balanceAfter, String description,
            String idempotencyKey) {
        return new Transaction(
                UUID.randomUUID(), accountId, TransactionType.WITHDRAWAL,
                amount, balanceAfter, description, idempotencyKey, LocalDateTime.now());
    }

    // Getters — no setters, enforcing immutability
    public UUID getId() {
        return id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
