package com.corebank.infrastructure.persistence.repository;

import com.corebank.infrastructure.persistence.entity.DomainEventOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA interface for domain event outbox.
 */
public interface JpaDomainEventOutboxRepository extends JpaRepository<DomainEventOutboxEntity, UUID> {

    List<DomainEventOutboxEntity> findByPublishedFalseOrderByCreatedAtAsc();
}
