package com.corebank.infrastructure.persistence.mapper;

import com.corebank.domain.model.Account;
import com.corebank.infrastructure.persistence.entity.AccountEntity;

/**
 * <h2>Persistence Mapper (ACL)</h2>
 * <p>
 * Translates between the behavioral Domain Aggregate and the data-oriented
 * Persistence Entity.
 * </p>
 */
public class AccountMapper {
    public static AccountEntity toEntity(Account account) {
        if (account == null)
            return null;
        return new AccountEntity(
                account.getId(),
                account.getHolderName(),
                account.getDocument(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getVersion());
    }

    public static Account toDomain(AccountEntity entity) {
        if (entity == null)
            return null;
        return new Account(
                entity.getId(),
                entity.getHolderName(),
                entity.getDocument(),
                entity.getBalance(),
                entity.getCreatedAt(),
                entity.getVersion());
    }
}
