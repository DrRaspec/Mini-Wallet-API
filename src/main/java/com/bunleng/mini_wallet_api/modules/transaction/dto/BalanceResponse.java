package com.bunleng.mini_wallet_api.modules.transaction.dto;

public record BalanceResponse(
        Double balance,
        Double income,
        Double expense
) {
}
