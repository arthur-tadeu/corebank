package com.corebank.application.usecase;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.WithdrawRequest;
import com.corebank.domain.exception.DomainException;
import com.corebank.domain.model.Account;
import com.corebank.domain.repository.AccountRepository;

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

    public WithdrawUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse execute(UUID id, WithdrawRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new DomainException("Account not found"));

        account.withdraw(request.amount());
        Account savedAccount = accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getHolderName(),
                savedAccount.getDocument(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt());
    }
}
