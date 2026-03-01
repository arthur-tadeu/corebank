package com.corebank.presentation.controller;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.CreateAccountRequest;
import com.corebank.application.dto.DepositRequest;
import com.corebank.application.dto.WithdrawRequest;
import com.corebank.application.usecase.CreateAccountUseCase;
import com.corebank.application.usecase.DepositUseCase;
import com.corebank.application.usecase.WithdrawUseCase;
import com.corebank.domain.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * <h2>Account API Delivery Mechanism</h2>
 * <p>
 * This controller serves as an <b>Inbound Adapter</b> or <b>Presentation
 * Adapter</b>
 * defining the external <b>System Boundary</b> for HTTP clients (e.g., the
 * Full-Stack Web App).
 * </p>
 *
 * <p>
 * <b>Responsibility:</b> It handles <b>Contract Enforcement</b> by translating
 * HTTP-specific
 * concerns (headers, verbs, status codes) into <b>Application Intents</b>
 * handled by Use Cases.
 * </p>
 *
 * <p>
 * <b>Separation of Concerns:</b> To maintain architectural purity, this class
 * must never
 * contain business logic nor directly interact with persistence entities. It
 * interacts
 * strictly with <b>Application DTOs</b> and Use Case orchestrators.
 * </p>
 */
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*") // Explicitly defined boundary for the Full-Stack delivery mechanism
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final DepositUseCase depositUseCase;
    private final WithdrawUseCase withdrawUseCase;
    private final AccountRepository accountRepository;

    public AccountController(
            CreateAccountUseCase createAccountUseCase,
            DepositUseCase depositUseCase,
            WithdrawUseCase withdrawUseCase,
            AccountRepository accountRepository) {
        this.createAccountUseCase = createAccountUseCase;
        this.depositUseCase = depositUseCase;
        this.withdrawUseCase = withdrawUseCase;
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(createAccountUseCase.execute(request));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable UUID id,
            @RequestBody DepositRequest request) {
        return ResponseEntity.ok(depositUseCase.execute(id, request));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable UUID id,
            @RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(withdrawUseCase.execute(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable UUID id) {
        return accountRepository.findById(id)
                .map(account -> ResponseEntity.ok(new AccountResponse(
                        account.getId(),
                        account.getHolderName(),
                        account.getDocument(),
                        account.getBalance(),
                        account.getCreatedAt())))
                .orElse(ResponseEntity.notFound().build());
    }
}
