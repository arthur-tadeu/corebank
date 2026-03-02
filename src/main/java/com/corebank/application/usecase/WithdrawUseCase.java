package com.corebank.application.usecase;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.WithdrawRequest;
import com.corebank.domain.event.DomainEventPublisher;
import com.corebank.domain.event.WithdrawalMadeEvent;
import com.corebank.domain.exception.DomainException;
import com.corebank.domain.model.Account;
import com.corebank.domain.model.Transaction;
import com.corebank.domain.repository.AccountRepository;
import com.corebank.domain.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Withdraw Orchestrator</h2>
 * <p>
 * This use case coordinates the withdrawal flow within the <b>Transactional
 * Boundary</b>.
 * </p>
 *
 * <h3>Design Decision: Idempotency Strategy</h3>
 * <p>
 * In production-grade fintech systems, this boundary should handle
 * <b>Idempotent Commands</b> to prevent duplicate transactions during network
 * retries.
 * While not implemented here, a production version would use an <i>Idempotency
 * Key</i>
 * to ensure that re-submitting the same request does not result in multiple
 * withdrawals.
 * </p>
 *
 * <h3>Consistency Guard</h3>
 * <p>
 * We rely on <b>Strong Consistency</b> provided by the aggregate root and the
 * underlying optimistic lock to ensure that the balance invariant is never
 * violated
 * under concurrent load.
 * </p>
 */
public class WithdrawUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final DomainEventPublisher eventPublisher;

    public WithdrawUseCase(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            DomainEventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    public AccountResponse execute(UUID id, WithdrawRequest request, String idempotencyKey) {
        // Idempotency Check
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existingTransaction = transactionRepository.findByIdempotencyKey(idempotencyKey);
            if (existingTransaction.isPresent()) {
                Account account = accountRepository.findById(id)
                        .orElseThrow(() -> new DomainException("Account not found"));
                return toResponse(account);
            }
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new DomainException("Account not found"));

        account.withdraw(request.amount());
        Account savedAccount = accountRepository.save(account);

        // Record Transaction with Idempotency Key
        Transaction transaction = Transaction.createWithdrawal(
                id,
                request.amount(),
                savedAccount.getBalance(),
                "Withdrawal",
                idempotencyKey);
        transactionRepository.save(transaction);

        // Publish Domain Event
        eventPublisher.publish(new WithdrawalMadeEvent(
                id,
                request.amount(),
                savedAccount.getBalance(),
                LocalDateTime.now()));

        return toResponse(savedAccount);
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getHolderName(),
                account.getDocument(),
                account.getBalance(),
                account.getCreatedAt());
    }
}
