-- =============================================================================
-- V6: Idempotency Keys Table
-- Prevents duplicate processing of financial commands during network retries.
-- Stores the original response so retried requests return the same result.
-- =============================================================================

CREATE TABLE idempotency_keys (
    id          UUID            PRIMARY KEY,
    key         VARCHAR(255)    NOT NULL UNIQUE,
    response    JSONB,
    status      VARCHAR(20)     NOT NULL DEFAULT 'PROCESSING' CHECK (status IN ('PROCESSING', 'COMPLETED', 'FAILED')),
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW(),
    expires_at  TIMESTAMP       NOT NULL DEFAULT (NOW() + INTERVAL '24 hours')
);

-- Fast key lookup for idempotency checks
CREATE INDEX idx_idempotency_key ON idempotency_keys (key);

-- Cleanup index for expired keys
CREATE INDEX idx_idempotency_expires ON idempotency_keys (expires_at) WHERE status = 'COMPLETED';
