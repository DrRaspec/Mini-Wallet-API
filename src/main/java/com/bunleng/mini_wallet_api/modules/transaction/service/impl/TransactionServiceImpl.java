package com.bunleng.mini_wallet_api.modules.transaction.service.impl;

import com.bunleng.mini_wallet_api.core.exception.NotFoundException;
import com.bunleng.mini_wallet_api.modules.transaction.dto.BalanceResponse;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionRequest;
import com.bunleng.mini_wallet_api.modules.transaction.dto.TransactionResponse;
import com.bunleng.mini_wallet_api.modules.transaction.entity.Transaction;
import com.bunleng.mini_wallet_api.modules.transaction.mapper.TransactionMapper;
import com.bunleng.mini_wallet_api.modules.transaction.repository.TransactionRepository;
import com.bunleng.mini_wallet_api.modules.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponse addTransaction(TransactionRequest request) {
        final Transaction newTransaction = transactionMapper.toEntity(request);

        return transactionMapper.toResponse(transactionRepository.save(newTransaction));
    }

    @Override
    public List<TransactionResponse> getTransactions() {
        return transactionRepository.findAll().stream().map(transactionMapper::toResponse).toList();
    }

    @Override
    public TransactionResponse getTransaction(UUID id) {
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse updateTransaction(UUID transactionId, TransactionRequest request) {

        Transaction existing = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        existing.setTitle(request.title());
        existing.setAmount(request.amount());
        existing.setIsIncome(Boolean.TRUE.equals(request.isIncome()));

        Transaction updated = transactionRepository.save(existing);

        return transactionMapper.toResponse(updated);
    }

    @Override
    public void deleteTransaction(UUID id) {
        if (!transactionRepository.existsById(id)) {
            throw new NotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public BalanceResponse getBalance() {
        BalanceResponse res = transactionRepository.getBalances();

        double income = res.income() != null ? res.income() : 0;
        double expense = res.expense() != null ? res.expense() : 0;
        double balance = income - expense;

        return new BalanceResponse(balance, income, expense);
    }
}
