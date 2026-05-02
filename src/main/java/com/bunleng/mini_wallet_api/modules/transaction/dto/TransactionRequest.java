package com.bunleng.mini_wallet_api.modules.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @NotBlank String title,
        @NotNull @Positive Double amount,
        Boolean isIncome
) {
}
