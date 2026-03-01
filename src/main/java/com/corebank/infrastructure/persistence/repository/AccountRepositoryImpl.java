package com.corebank.infrastructure.persistence.repository;

import com.corebank.domain.model.Account;
import com.corebank.domain.repository.AccountRepository;
import com.corebank.infrastructure.persistence.entity.AccountEntity;
import com.corebank.infrastructure.persistence.mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * <h2>Account Repository Infrastructure Adapter</h2>
 * <p>
 * This implementation bridge the gap between our Domain abstractions and the
 * <b>Relational Persistence Layer</b>.
 * </p>
 *
 * <p>
 * <b>Anti-Corruption Layer (ACL):</b> Acting as an adapter, it translates
 * between the
 * <b>Domain Aggregate</b> and the <b>Persistence Model</b> using
 * {@link AccountMapper}.
 * This ensures that changes in the database schema do not ripple through the
 * Domain Core.
 * </p>
 *
 * <p>
 * <b>Transactional Integrity:</b> It defines the <b>Persistence Barrier</b> and
 * ensures
 * atomic operations through Spring's transaction management.
 * </p>
 */
@Repository
@Transactional
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountMapper.toEntity(account);
        AccountEntity savedEntity = jpaAccountRepository.save(entity);
        return AccountMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaAccountRepository.findById(id)
                .map(AccountMapper::toDomain);
    }

    @Override
    public Optional<Account> findByDocument(String document) {
        return jpaAccountRepository.findByDocument(document)
                .map(AccountMapper::toDomain);
    }
}
