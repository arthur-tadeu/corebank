-- =============================================================================
-- V4: Audit Log Table
-- Append-only audit trail for compliance and debugging.
-- Uses JSONB for flexible payload storage without schema changes.
-- =============================================================================

CREATE TABLE audit_log (
    id          BIGSERIAL       PRIMARY KEY,
    action      VARCHAR(100)    NOT NULL,
    entity_type VARCHAR(100)    NOT NULL,
    entity_id   VARCHAR(255)    NOT NULL,
    payload     JSONB,
    actor       VARCHAR(255)    DEFAULT 'SYSTEM',
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Composite index for entity-based audit queries
CREATE INDEX idx_audit_log_entity ON audit_log (entity_type, entity_id);

-- Chronological index for time-range audit reports
CREATE INDEX idx_audit_log_created_at ON audit_log (created_at);
