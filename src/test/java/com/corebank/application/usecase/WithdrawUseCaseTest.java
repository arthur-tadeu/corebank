package com.corebank.application.usecase;

import com.corebank.application.dto.AccountResponse;
import com.corebank.application.dto.WithdrawRequest;
import com.corebank.domain.event.DomainEventPublisher;
import com.corebank.domain.model.Account;
import com.corebank.domain.model.Transaction;
import com.corebank.domain.repository.AccountRepository;
import com.corebank.domain.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WithdrawUseCaseTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private DomainEventPublisher eventPublisher;
    private WithdrawUseCase withdrawUseCase;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        eventPublisher = mock(DomainEventPublisher.class);
        withdrawUseCase = new WithdrawUseCase(accountRepository, transactionRepository, eventPublisher);
    }

    @Test
    void shouldExecuteWithdrawalAndPublishEvent() {
        UUID accountId = UUID.randomUUID();
        WithdrawRequest request = new WithdrawRequest(new BigDecimal("100.00"));
        String idempotencyKey = "key-123";

        Account account = new Account(accountId, "John Doe", "12345678900", new BigDecimal("500.00"),
                java.time.LocalDateTime.now(), 0L);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findByIdempotencyKey(idempotencyKey)).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AccountResponse response = withdrawUseCase.execute(accountId, request, idempotencyKey);

        assertNotNull(response);
        assertEquals(new BigDecimal("400.00"), response.balance());

        verify(accountRepository).save(any());
        verify(transactionRepository).save(any(Transaction.class));
        verify(eventPublisher).publish(any());
    }

    @Test
    void shouldBeIdempotent() {
        UUID accountId = UUID.randomUUID();
        WithdrawRequest request = new WithdrawRequest(new BigDecimal("100.00"));
        String idempotencyKey = "key-123";

        Account account = new Account(accountId, "John Doe", "12345678900", new BigDecimal("500.00"),
                java.time.LocalDateTime.now(), 0L);
        Transaction existingTransaction = Transaction.createWithdrawal(accountId, new BigDecimal("100.00"),
                new BigDecimal("400.00"), "Desc", idempotencyKey);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findByIdempotencyKey(idempotencyKey)).thenReturn(Optional.of(existingTransaction));

        AccountResponse response = withdrawUseCase.execute(accountId, request, idempotencyKey);

        assertNotNull(response);
        assertEquals(new BigDecimal("500.00"), response.balance()); // Balance not changed

        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(eventPublisher, never()).publish(any());
    }
}
