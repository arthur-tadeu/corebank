package com.corebank.application.dto;

import java.math.BigDecimal;

public record DepositRequest(
        BigDecimal amount) {
}
