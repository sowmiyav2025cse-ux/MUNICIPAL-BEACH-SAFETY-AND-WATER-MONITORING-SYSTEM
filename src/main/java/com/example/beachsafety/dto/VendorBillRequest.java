package com.example.beachsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class VendorBillRequest {
    private String billNumber;
    private Long purchaseOrderId;
    private Long vendorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate billDate;
    private Double amount;

    public VendorBillRequest() {
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
