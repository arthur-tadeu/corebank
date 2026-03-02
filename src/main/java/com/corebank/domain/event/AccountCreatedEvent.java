package com.corebank.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Account Created Domain Event</h2>
 * <p>
 * Emitted when a new account is successfully created.
 * Consumers may react by sending welcome notifications, initializing
 * compliance checks, or updating search indexes.
 * </p>
 */
public record AccountCreatedEvent(
        UUID accountId,
        String holderName,
        String document,
        LocalDateTime occurredAt) implements DomainEvent {
}
