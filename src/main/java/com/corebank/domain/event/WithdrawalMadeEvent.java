package com.corebank.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Withdrawal Made Domain Event</h2>
 * <p>
 * Emitted after a successful withdrawal.
 * Downstream consumers may trigger fraud checks, balance alerts,
 * or reporting updates based on this event.
 * </p>
 */
public record WithdrawalMadeEvent(
        UUID accountId,
        BigDecimal amount,
        BigDecimal balanceAfter,
        LocalDateTime occurredAt) implements DomainEvent {
}
