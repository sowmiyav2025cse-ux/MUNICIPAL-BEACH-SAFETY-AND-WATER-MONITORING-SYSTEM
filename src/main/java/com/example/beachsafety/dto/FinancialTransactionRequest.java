package com.example.beachsafety.dto;

import com.example.beachsafety.entity.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class FinancialTransactionRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    private TransactionType transactionType;
    private String referenceNumber;
    private String description;
    private Long debitAccountId;
    private Long creditAccountId;
    private Double amount;

    public FinancialTransactionRequest() {
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
    }

    public void setDebitAccountId(Long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public void setCreditAccountId(Long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
