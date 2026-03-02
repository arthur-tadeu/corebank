package com.corebank.domain.model;

/**
 * <h2>Customer Status Lifecycle</h2>
 * <p>
 * Defines valid states for a customer entity.
 * Transitions: ACTIVE → SUSPENDED → INACTIVE.
 * </p>
 */
public enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED
}
