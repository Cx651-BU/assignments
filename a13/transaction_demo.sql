-- transaction_demo.sql  — provided for PA13, Part A
-- Run from inside the SQLite shell:  .read transaction_demo.sql
-- Do NOT modify this file.
--
-- This script creates a small 'accounts' table, populates it,
-- then walks through a multi-step transfer with a deliberate rollback.
-- ─────────────────────────────────────────────────────────────────────

-- ── Setup ─────────────────────────────────────────────────────────────
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
    account_id INTEGER PRIMARY KEY,
    owner      TEXT    NOT NULL,
    balance    REAL    NOT NULL CHECK (balance >= 0)
);

INSERT INTO accounts VALUES (1, 'Alice', 500.00);
INSERT INTO accounts VALUES (2, 'Bob',   200.00);

-- ── Step 1: Show balances before the transaction ───────────────────────
SELECT 'BEFORE TRANSACTION' AS status, owner, balance FROM accounts ORDER BY account_id;

-- ── Step 2: Begin the transaction ─────────────────────────────────────
BEGIN;

-- ── Step 3: Transfer $100 from Alice to Bob ───────────────────────────
UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
UPDATE accounts SET balance = balance + 100 WHERE account_id = 2;

-- ── Step 4: Show balances inside the transaction ──────────────────────
SELECT 'INSIDE TRANSACTION (before rollback)' AS status, owner, balance FROM accounts ORDER BY account_id;

-- ── Step 5: Something went wrong — roll back the transfer ─────────────
-- (In a real application this ROLLBACK would be triggered by a caught exception.)
ROLLBACK;

-- ── Step 6: Show balances after rollback ──────────────────────────────
SELECT 'AFTER ROLLBACK' AS status, owner, balance FROM accounts ORDER BY account_id;

-- ── Cleanup ───────────────────────────────────────────────────────────
DROP TABLE accounts;
