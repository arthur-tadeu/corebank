package com.corebank.domain.model;

import com.corebank.domain.exception.DomainException;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Customer Entity</h2>
 * <p>
 * Represents a banking customer with lifecycle management.
 * Separated from {@link Account} to support future multi-account-per-customer
 * scenarios.
 * </p>
 *
 * <h3>Design Decision: Status Transitions</h3>
 * <p>
 * Status changes are guarded by business rules to prevent invalid transitions:
 * <ul>
 * <li>ACTIVE → SUSPENDED (regulatory hold, fraud investigation)</li>
 * <li>SUSPENDED → ACTIVE (cleared by compliance)</li>
 * <li>ACTIVE/SUSPENDED → INACTIVE (account closure)</li>
 * <li>INACTIVE is terminal — no reactivation allowed</li>
 * </ul>
 * </p>
 */
public class Customer {
    private final UUID id;
    private String fullName;
    private final String document;
    private String email;
    private String phone;
    private CustomerStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;

    public Customer(UUID id, String fullName, String document, String email, String phone,
            CustomerStatus status, LocalDateTime createdAt, LocalDateTime updatedAt,
            Long version) {
        this.id = id;
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    /**
     * Factory method for creating a new active customer.
     */
    public static Customer create(String fullName, String document, String email, String phone) {
        return new Customer(
                UUID.randomUUID(), fullName, document, email, phone,
                CustomerStatus.ACTIVE, LocalDateTime.now(), LocalDateTime.now(), 0L);
    }

    /**
     * Suspend the customer (e.g., regulatory hold).
     */
    public void suspend() {
        if (this.status == CustomerStatus.INACTIVE) {
            throw new DomainException("Cannot suspend an inactive customer");
        }
        this.status = CustomerStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Reactivate a suspended customer.
     */
    public void activate() {
        if (this.status == CustomerStatus.INACTIVE) {
            throw new DomainException("Cannot reactivate an inactive customer");
        }
        this.status = CustomerStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivate the customer (terminal state).
     */
    public void deactivate() {
        this.status = CustomerStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
}
