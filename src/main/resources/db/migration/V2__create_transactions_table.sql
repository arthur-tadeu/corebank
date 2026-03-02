-- =============================================================================
-- V2: Transactions Table
-- Immutable ledger of all financial movements against an account.
-- Each row represents a deposit or withdrawal with the resulting balance snapshot.
-- =============================================================================

CREATE TABLE transactions (
    id              UUID            PRIMARY KEY,
    account_id      UUID            NOT NULL REFERENCES accounts(id),
    type            VARCHAR(20)     NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL')),
    amount          NUMERIC(19, 4) NOT NULL CHECK (amount > 0),
    balance_after   NUMERIC(19, 4) NOT NULL,
    description     VARCHAR(500),
    idempotency_key VARCHAR(255)    UNIQUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Foreign key lookup: all transactions for a given account
CREATE INDEX idx_transactions_account_id ON transactions (account_id);

-- Chronological ordering for transaction history
CREATE INDEX idx_transactions_created_at ON transactions (created_at);

-- Fast idempotency key lookup for duplicate detection
CREATE INDEX idx_transactions_idempotency_key ON transactions (idempotency_key) WHERE idempotency_key IS NOT NULL;
