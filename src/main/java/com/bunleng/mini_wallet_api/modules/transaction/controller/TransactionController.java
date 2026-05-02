package com.bunleng.mini_wallet_api.modules.transaction.controller;

import com.bunleng.mini_wallet_api.modules.transaction.dto.BalanceResponse;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionRequest;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionResponse;
import com.bunleng.mini_wallet_api.modules.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(transactionService.addTransaction(request));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID transactionId) {
        return ResponseEntity.ok(transactionService.getTransaction(transactionId));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance() {
        return ResponseEntity.ok(transactionService.getBalance());
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable UUID transactionId,
            @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(
                transactionService.updateTransaction(transactionId, request)
        );
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
