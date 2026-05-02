package com.bunleng.mini_wallet_api.modules.transaction.repository;

import com.bunleng.mini_wallet_api.modules.transaction.dto.BalanceResponse;
import com.bunleng.mini_wallet_api.modules.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("""
    SELECT new com.bunleng.mini_wallet_api.modules.transaction.dto.BalanceResponse(
        null,
        SUM(CASE WHEN t.isIncome = true THEN t.amount ELSE 0.0 END),
        SUM(CASE WHEN t.isIncome = false THEN t.amount ELSE 0.0 END)
    )
    FROM Transaction t
""")
    BalanceResponse getBalances();
}
