-- =============================================================================
-- V3: Customers Table
-- Customer identity and lifecycle management.
-- Separated from accounts to support future multi-account-per-customer scenarios.
-- =============================================================================

CREATE TABLE customers (
    id          UUID            PRIMARY KEY,
    full_name   VARCHAR(255)    NOT NULL,
    document    VARCHAR(20)     NOT NULL UNIQUE,
    email       VARCHAR(255)    UNIQUE,
    phone       VARCHAR(30),
    status      VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP       NOT NULL DEFAULT NOW(),
    version     BIGINT          NOT NULL DEFAULT 0
);

CREATE INDEX idx_customers_document ON customers (document);
CREATE INDEX idx_customers_email ON customers (email) WHERE email IS NOT NULL;
CREATE INDEX idx_customers_status ON customers (status);
