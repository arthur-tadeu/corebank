package com.corebank.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>Account Persistence Representation</h2>
 * <p>
 * Decoupled from the domain aggregate root to maintain framework ignorance.
 * </p>
 *
 * <h3>Design Decision: JPA Optimistic Locking</h3>
 * <p>
 * We use {@link Version} to delegate <b>Optimistic Concurrency Control</b> to
 * the JPA provider. This ensures that the system detects and prevents lost
 * updates
 * when multiple clients attempt to modify the same account simultaneously, a
 * critical requirement for <b>Fintech Transactional Integrity</b>.
 * </p>
 */
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false, unique = true)
    private String document;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;
}
