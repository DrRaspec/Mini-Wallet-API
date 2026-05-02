package com.bunleng.mini_wallet_api.modules.transaction.dto;

import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String title,
        Double amount,
        Boolean isIncome,
        String createdAt,
        String updatedAt
) {
}
