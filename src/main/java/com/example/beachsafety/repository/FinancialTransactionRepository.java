package com.example.beachsafety.repository;

import com.example.beachsafety.entity.FinancialTransaction;
import com.example.beachsafety.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    List<FinancialTransaction> findByTransactionType(TransactionType transactionType);
    List<FinancialTransaction> findAllByOrderByTransactionDateDesc();
}
