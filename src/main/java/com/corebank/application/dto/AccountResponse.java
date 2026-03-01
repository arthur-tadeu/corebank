package com.corebank.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponse(
        UUID id,
        String holderName,
        String document,
        BigDecimal balance,
        LocalDateTime createdAt) {
}
