package com.example.beachsafety.service;

import com.example.beachsafety.dto.VendorBillRequest;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VendorBillService {

    private final VendorBillRepository billRepository;
    private final PurchaseOrderRepository poRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final FinancialTransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final BeachSectorRepository sectorRepository;

    public VendorBillService(VendorBillRepository billRepository,
                             PurchaseOrderRepository poRepository,
                             ContactRepository contactRepository,
                             AccountRepository accountRepository,
                             FinancialTransactionRepository transactionRepository,
                             BudgetRepository budgetRepository,
                             BeachSectorRepository sectorRepository) {
        this.billRepository = billRepository;
        this.poRepository = poRepository;
        this.contactRepository = contactRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<VendorBill> getAllVendorBills() {
        return billRepository.findAllByOrderByBillDateDesc();
    }

    public VendorBill getVendorBillById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor bill not found with ID: " + id));
    }

    public VendorBill createVendorBill(VendorBillRequest request) {
        PurchaseOrder po = null;
        if (request.getPurchaseOrderId() != null) {
            po = poRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with ID: " + request.getPurchaseOrderId()));
            // Update PO status to BILLED
            po.setStatus(OrderStatus.BILLED);
            poRepository.save(po);
        }

        Contact vendor = null;
        if (request.getVendorId() != null) {
            vendor = contactRepository.findById(request.getVendorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + request.getVendorId()));
        } else if (po != null) {
            vendor = po.getVendor();
        }

        if (vendor == null) {
            throw new ResourceNotFoundException("Vendor must be specified for vendor bill");
        }

        double amount = (request.getAmount() != null && request.getAmount() > 0)
                ? request.getAmount()
                : (po != null ? po.getTotalAmount() : 0.0);

        String billNum = request.getBillNumber();
        if (billNum == null || billNum.trim().isEmpty()) {
            billNum = "BILL-2026-" + String.format("%04d", billRepository.count() + 1);
        }

        VendorBill bill = new VendorBill();
        bill.setBillNumber(billNum);
        bill.setPurchaseOrder(po);
        bill.setVendor(vendor);
        bill.setBillDate(request.getBillDate() != null ? request.getBillDate() : LocalDate.now());
        bill.setAmount(amount);
        bill.setStatus(BillStatus.UNPAID);

        return billRepository.save(bill);
    }

    public VendorBill payVendorBill(Long id) {
        VendorBill bill = getVendorBillById(id);
        if (bill.getStatus() == BillStatus.PAID) {
            return bill; // already paid
        }

        bill.setStatus(BillStatus.PAID);
        if (bill.getPurchaseOrder() != null) {
            PurchaseOrder po = bill.getPurchaseOrder();
            po.setStatus(OrderStatus.PAID);
            poRepository.save(po);
        }

        // Automatic Financial Transaction:
        // Debit: Lab Testing Expenses, Credit: Bank
        Account debitAccount = accountRepository.findByAccountName("Lab Testing Expenses")
                .orElseGet(() -> accountRepository.findByAccountType(AccountType.EXPENSE).stream().findFirst()
                        .orElseGet(() -> accountRepository.save(new Account(null, "4001", "Lab Testing Expenses", AccountType.EXPENSE, 0.0))));

        Account creditAccount = accountRepository.findByAccountName("Bank")
                .orElseGet(() -> accountRepository.findByAccountType(AccountType.ASSET).stream().findFirst()
                        .orElseGet(() -> accountRepository.save(new Account(null, "1004", "Bank", AccountType.ASSET, 500000.0))));

        FinancialTransaction tx = new FinancialTransaction();
        tx.setTransactionDate(LocalDate.now());
        tx.setTransactionType(TransactionType.BANK);
        tx.setReferenceNumber("PAY-" + bill.getBillNumber());
        tx.setDescription("Vendor bill payment to " + bill.getVendor().getName() + " for " + bill.getBillNumber());
        tx.setDebitAccount(debitAccount);
        tx.setCreditAccount(creditAccount);
        tx.setAmount(bill.getAmount());
        transactionRepository.save(tx);

        // Update actual expenses in Budget
        List<Budget> budgets = budgetRepository.findAll();
        if (!budgets.isEmpty()) {
            Budget budget = budgets.get(0);
            budget.setActualAmount(budget.getActualAmount() + bill.getAmount());
            budget.calculateVariance();
            budgetRepository.save(budget);
        }

        return billRepository.save(bill);
    }
}
