package com.bunleng.mini_wallet_api.modules.transaction.service;

import com.bunleng.mini_wallet_api.modules.transaction.dto.BalanceResponse;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionRequest;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TransactionService {
    TransactionResponse addTransaction(TransactionRequest request);
    List<TransactionResponse> getTransactions();
    TransactionResponse getTransaction(UUID id);
    TransactionResponse updateTransaction(UUID transactionId, TransactionRequest request);
    void deleteTransaction(UUID id);
    BalanceResponse getBalance();
}
