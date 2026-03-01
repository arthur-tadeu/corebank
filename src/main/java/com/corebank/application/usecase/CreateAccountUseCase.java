package com.corebank.application.usecase;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.CreateAccountRequest;
import com.corebank.domain.exception.InvalidTransactionException;
import com.corebank.domain.model.Account;
import com.corebank.domain.repository.AccountRepository;

/**
 * <h2>Create Account Use Case</h2>
 * <p>
 * This class serves as an <b>Orchestration Layer</b>, coordinating the
 * necessary steps
 * to realize the business intent of creating a new banking account.
 * </p>
 *
 * <p>
 * <b>Responsibility:</b> It handles the <b>Transactional Boundary</b>,
 * validates high-level
 * preconditions (e.g., identity uniqueness), and orchestrates state changes
 * through the Domain layer.
 * </p>
 *
 * <p>
 * <b>Architectural Rule:</b> This layer specifies <b>how</b> the system behaves
 * from an
 * application perspective. It depends exclusively on Domain abstractions (Core
 * Models and
 * Repository Interfaces), ensuring it remains decoupled from infrastructure
 * implementations.
 * </p>
 */
public class CreateAccountUseCase {
    private final AccountRepository accountRepository;

    public CreateAccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse execute(CreateAccountRequest request) {
        accountRepository.findByDocument(request.document())
                .ifPresent(a -> {
                    throw new InvalidTransactionException("Account with document already exists");
                });

        Account account = Account.create(request.holderName(), request.document());
        Account savedAccount = accountRepository.save(account);

        return new AccountResponse(
                savedAccount.getId(),
                savedAccount.getHolderName(),
                savedAccount.getDocument(),
                savedAccount.getBalance(),
                savedAccount.getCreatedAt());
    }
}
