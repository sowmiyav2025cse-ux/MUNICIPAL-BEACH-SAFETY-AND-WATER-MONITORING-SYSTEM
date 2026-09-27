package com.example.beachsafety.service;

import com.example.beachsafety.dto.FinancialTransactionRequest;
import com.example.beachsafety.entity.Account;
import com.example.beachsafety.entity.FinancialTransaction;
import com.example.beachsafety.entity.TransactionType;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.AccountRepository;
import com.example.beachsafety.repository.FinancialTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FinancialTransactionService {

    private final FinancialTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public FinancialTransactionService(FinancialTransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<FinancialTransaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }

    public List<FinancialTransaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByTransactionType(type);
    }

    public FinancialTransaction recordTransaction(FinancialTransactionRequest request) {
        Account debit = accountRepository.findById(request.getDebitAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Debit account not found with ID: " + request.getDebitAccountId()));

        Account credit = accountRepository.findById(request.getCreditAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Credit account not found with ID: " + request.getCreditAccountId()));

        String ref = request.getReferenceNumber();
        if (ref == null || ref.trim().isEmpty()) {
            ref = "TXN-" + System.currentTimeMillis();
        }

        FinancialTransaction tx = new FinancialTransaction();
        tx.setTransactionDate(request.getTransactionDate() != null ? request.getTransactionDate() : LocalDate.now());
        tx.setTransactionType(request.getTransactionType() != null ? request.getTransactionType() : TransactionType.BANK);
        tx.setReferenceNumber(ref);
        tx.setDescription(request.getDescription());
        tx.setDebitAccount(debit);
        tx.setCreditAccount(credit);
        tx.setAmount(request.getAmount() != null ? request.getAmount() : 0.0);

        return transactionRepository.save(tx);
    }
}
