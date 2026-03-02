-- =============================================================================
-- V1: Accounts Table
-- The primary persistence structure for the Account aggregate root.
-- Uses NUMERIC(19,4) for financial precision (up to 15 integer + 4 decimal digits).
-- =============================================================================

CREATE TABLE accounts (
    id          UUID            PRIMARY KEY,
    holder_name VARCHAR(255)    NOT NULL,
    document    VARCHAR(20)     NOT NULL UNIQUE,
    balance     NUMERIC(19, 4) NOT NULL DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW(),
    version     BIGINT          NOT NULL DEFAULT 0
);

-- Performance index for document-based lookups (login, uniqueness checks)
CREATE INDEX idx_accounts_document ON accounts (document);

-- Performance index for chronological queries and reporting
CREATE INDEX idx_accounts_created_at ON accounts (created_at);
