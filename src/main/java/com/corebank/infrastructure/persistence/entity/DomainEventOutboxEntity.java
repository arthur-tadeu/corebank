package com.corebank.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Domain Events Outbox Entity</h2>
 * <p>
 * Implements the <b>Transactional Outbox Pattern</b> for reliable event
 * publishing.
 * Events are written atomically within the same transaction as the business
 * operation,
 * then polled by a separate process for delivery to external consumers.
 * </p>
 */
@Entity
@Table(name = "domain_events_outbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomainEventOutboxEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String aggregateType;

    @Column(nullable = false)
    private String aggregateId;

    @Column(nullable = false, length = 100)
    private String eventType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean published = false;

    private LocalDateTime publishedAt;
}
