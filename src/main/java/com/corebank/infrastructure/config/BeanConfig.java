package com.corebank.infrastructure.config;

import com.corebank.application.usecase.CreateAccountUseCase;
import com.corebank.application.usecase.DepositUseCase;
import com.corebank.application.usecase.WithdrawUseCase;
import com.corebank.domain.event.DomainEventPublisher;
import com.corebank.domain.repository.AccountRepository;
import com.corebank.domain.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountRepository accountRepository) {
        return new CreateAccountUseCase(accountRepository);
    }

    @Bean
    public DepositUseCase depositUseCase(AccountRepository accountRepository) {
        return new DepositUseCase(accountRepository);
    }

    @Bean
    public WithdrawUseCase withdrawUseCase(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            DomainEventPublisher eventPublisher) {
        return new WithdrawUseCase(accountRepository, transactionRepository, eventPublisher);
    }
}
