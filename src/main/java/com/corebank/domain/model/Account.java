package com.corebank.domain.model;

import com.corebank.domain.event.DomainEvent;
import com.corebank.domain.exception.InsufficientBalanceException;
import com.corebank.domain.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * <h2>Account Aggregate Root</h2>
 * <p>
 * The authoritative <b>Consistency Boundary</b> for account operations.
 * </p>
 * 
 * <h3>Architectural Decision: Optimistic Concurrency Control</h3>
 * <p>
 * To prevent <b>Double-Spend</b> and <b>Race Conditions</b> in high-concurrency
 * fintech environments, this aggregate uses a <b>Version</b> field. This choice
 * favors system availability over pessimistic locking while guaranteeing that
 * stale updates are rejected.
 * </p>
 *
 * <p>
 * <b>Invariant Protection:</b>
 * Encapsulating business rules here ensures that <b>Domain Authority</b> is
 * respected regardless of the delivery mechanism or persistence technology.
 * </p>
 */
public class Account {
    private final UUID id;
    private final String holderName;
    private final String document;
    private BigDecimal balance;
    private final LocalDateTime createdAt;

    /** Version for Optimistic Locking and Concurrency Control */
    private Long version;

    /** Conceptual support for side-effect decoupling */
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Account(UUID id, String holderName, String document, BigDecimal balance, LocalDateTime createdAt,
            Long version) {
        this.id = id;
        this.holderName = holderName;
        this.document = document;
        this.balance = balance == null ? BigDecimal.ZERO : balance;
        this.createdAt = createdAt;
        this.version = version;
    }

    public static Account create(String holderName, String document) {
        return new Account(
                UUID.randomUUID(),
                holderName,
                document,
                BigDecimal.ZERO,
                LocalDateTime.now(),
                0L);
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        this.balance = this.balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Transaction amount must be greater than zero");
        }
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public String getDocument() {
        return document;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getVersion() {
        return version;
    }
}
