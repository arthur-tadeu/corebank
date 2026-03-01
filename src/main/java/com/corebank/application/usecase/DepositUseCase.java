package com.corebank.application.usecase;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.DepositRequest;
import com.corebank.domain.exception.DomainException;
import com.corebank.domain.model.Account;
import com.corebank.domain.repository.AccountRepository;

import java.util.UUID;

/**
 * <h2>Deposit Orchestrator</h2>
 * <p>
 * This use case coordinates the deposit flow, which is a state-changing
 * operation
 * with high <b>Transactional Criticality</b>.
 * </p>
 *
 * <h3>Design Decision: Eventual Consistency Path</h3>
 * <p>
 * By capturing state changes within the aggregate root, this orchestrator
 * prepares
 * the system for <b>Eventual Consistency</b> side effects (e.g., sending push
 * notifications or updating analytics) without blocking the primary
 * transaction.
 * </p>
 *
 * <h3>Concurrency Awareness</h3>
 * <p>
 * It relies on <b>Optimistic Concurrency Control</b>. If multiple deposit
 * requests
 * for the same account arrive simultaneously, only one will succeed,
 * effectively
 * preventing stale state updates.
 * </p>
 */
public class DepositUseCase {
    private final AccountRepository accountRepository;

    public DepositUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse execute(UUID id, DepositRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new DomainException("Account not found"));

        account.deposit(request.amount());
        Account savedAccount = accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getHolderName(),
                savedAccount.getDocument(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt());
    }
}
