package com.corebank.domain.event;

import java.time.LocalDateTime;

/**
 * <h2>Domain Event Contract</h2>
 * <p>
 * Domain Events capture state changes within the business core that are of
 * interest
 * to other parts of the system or external bounded contexts.
 * </p>
 *
 * <p>
 * <b>Decoupling Rationale:</b> Events allow us to decouple primary business
 * invariants
 * (e.g., balance update) from secondary side effects (e.g., notification, audit
 * logging,
 * search index updates).
 * </p>
 */
public interface DomainEvent {
    LocalDateTime occurredAt();
}
