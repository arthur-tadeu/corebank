package com.corebank.infrastructure.persistence.repository;

import com.corebank.infrastructure.persistence.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface for audit log persistence.
 */
public interface JpaAuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
}
