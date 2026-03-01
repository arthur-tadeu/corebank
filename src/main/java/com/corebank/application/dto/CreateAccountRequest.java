package com.corebank.application.dto;

public record CreateAccountRequest(
        String holderName,
        String document) {
}
