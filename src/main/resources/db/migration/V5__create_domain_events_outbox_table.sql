-- =============================================================================
-- V5: Domain Events Outbox Table
-- Implements the Transactional Outbox Pattern for reliable event publishing.
-- Events are written atomically with the business transaction, then polled
-- by a separate process for delivery to message brokers or event consumers.
-- =============================================================================

CREATE TABLE domain_events_outbox (
    id              UUID            PRIMARY KEY,
    aggregate_type  VARCHAR(100)    NOT NULL,
    aggregate_id    VARCHAR(255)    NOT NULL,
    event_type      VARCHAR(100)    NOT NULL,
    payload         JSONB           NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    published       BOOLEAN         NOT NULL DEFAULT FALSE,
    published_at    TIMESTAMP
);

-- Polling index: unpublished events ordered by creation time
CREATE INDEX idx_outbox_unpublished ON domain_events_outbox (published, created_at) WHERE published = FALSE;

-- Aggregate history index for event sourcing queries
CREATE INDEX idx_outbox_aggregate ON domain_events_outbox (aggregate_type, aggregate_id);
