package com.corebank.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Deposit Made Domain Event</h2>
 * <p>
 * Emitted after a successful deposit operation.
 * Captures the amount deposited and the resulting balance for downstream
 * consumers
 * such as analytics pipelines, notification services, or audit loggers.
 * </p>
 */
public record DepositMadeEvent(
        UUID accountId,
        BigDecimal amount,
        BigDecimal balanceAfter,
        LocalDateTime occurredAt) implements DomainEvent {
}
