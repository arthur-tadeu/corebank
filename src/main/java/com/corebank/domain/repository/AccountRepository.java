package com.corebank.domain.repository;

import com.corebank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

/**
 * <h2>Account Repository Contract</h2>
 * <p>
 * This interface defines the <b>Domain Abstraction</b> for account persistence.
 * </p>
 *
 * <p>
 * <b>Dependency Inversion Principle (DIP):</b> The domain layer declares its
 * required
 * capabilities without concerning itself with the implementation details (SQL,
 * NoSQL, etc.).
 * This prevents outward-pointing dependencies and maintains the purity of the
 * business core.
 * </p>
 *
 * <p>
 * The actual implementation resides in the Infrastructure layer, acting as an
 * <b>Inbound Adapter</b> to the domain.
 * </p>
 */
public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(UUID id);

    Optional<Account> findByDocument(String document);
}
