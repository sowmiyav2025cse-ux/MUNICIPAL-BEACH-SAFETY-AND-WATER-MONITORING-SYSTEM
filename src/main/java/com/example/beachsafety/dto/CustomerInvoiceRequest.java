package com.example.beachsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class CustomerInvoiceRequest {
    private String invoiceNumber;
    private Long salesOrderId;
    private Long customerId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;
    private Double amount;

    public CustomerInvoiceRequest() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
