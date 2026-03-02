package com.corebank.infrastructure.event;

import com.corebank.domain.event.DomainEvent;
import com.corebank.domain.event.DomainEventPublisher;
import com.corebank.infrastructure.persistence.entity.DomainEventOutboxEntity;
import com.corebank.infrastructure.persistence.repository.JpaDomainEventOutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Domain Event Publisher Implementation</h2>
 * <p>
 * Concrete implementation of the {@link DomainEventPublisher} contract.
 * Uses the <b>Transactional Outbox Pattern</b> by persisting events to the
 * database within the same transaction as the business operation.
 * </p>
 */
@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final JpaDomainEventOutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    public DomainEventPublisherImpl(JpaDomainEventOutboxRepository outboxRepository, ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(DomainEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            DomainEventOutboxEntity outboxEntity = new DomainEventOutboxEntity(
                    UUID.randomUUID(),
                    event.getClass().getSimpleName(), // Aggregate type placeholder or simplification
                    "N/A", // Aggregate ID placeholder (can be improved)
                    event.getClass().getName(),
                    payload,
                    LocalDateTime.now(),
                    false,
                    null);
            outboxRepository.save(outboxEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing domain event", e);
        }
    }
}
