package com.example.beachsafety.service;

import com.example.beachsafety.dto.CustomerInvoiceRequest;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CustomerInvoiceService {

    private final CustomerInvoiceRepository invoiceRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;
    private final FinancialTransactionRepository transactionRepository;

    public CustomerInvoiceService(CustomerInvoiceRepository invoiceRepository,
                                  SalesOrderRepository salesOrderRepository,
                                  ContactRepository contactRepository,
                                  AccountRepository accountRepository,
                                  FinancialTransactionRepository transactionRepository) {
        this.invoiceRepository = invoiceRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.contactRepository = contactRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CustomerInvoice> getAllCustomerInvoices() {
        return invoiceRepository.findAllByOrderByInvoiceDateDesc();
    }

    public CustomerInvoice getCustomerInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer invoice not found with ID: " + id));
    }

    public CustomerInvoice createCustomerInvoice(CustomerInvoiceRequest request) {
        SalesOrder so = null;
        if (request.getSalesOrderId() != null) {
            so = salesOrderRepository.findById(request.getSalesOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales Order not found with ID: " + request.getSalesOrderId()));
            // Update SO status to INVOICED
            so.setStatus(OrderStatus.INVOICED);
            salesOrderRepository.save(so);
        }

        Contact customer = null;
        if (request.getCustomerId() != null) {
            customer = contactRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));
        } else if (so != null) {
            customer = so.getCustomer();
        }

        if (customer == null) {
            throw new ResourceNotFoundException("Customer must be specified for customer invoice");
        }

        double amount = (request.getAmount() != null && request.getAmount() > 0)
                ? request.getAmount()
                : (so != null ? so.getTotalAmount() : 0.0);

        String invNum = request.getInvoiceNumber();
        if (invNum == null || invNum.trim().isEmpty()) {
            invNum = "INV-2026-" + String.format("%04d", invoiceRepository.count() + 1);
        }

        CustomerInvoice invoice = new CustomerInvoice();
        invoice.setInvoiceNumber(invNum);
        invoice.setSalesOrder(so);
        invoice.setCustomer(customer);
        invoice.setInvoiceDate(request.getInvoiceDate() != null ? request.getInvoiceDate() : LocalDate.now());
        invoice.setAmount(amount);
        invoice.setStatus(BillStatus.UNPAID);

        return invoiceRepository.save(invoice);
    }

    public CustomerInvoice payCustomerInvoice(Long id) {
        CustomerInvoice invoice = getCustomerInvoiceById(id);
        if (invoice.getStatus() == BillStatus.PAID) {
            return invoice; // already paid
        }

        invoice.setStatus(BillStatus.PAID);
        if (invoice.getSalesOrder() != null) {
            SalesOrder so = invoice.getSalesOrder();
            so.setStatus(OrderStatus.PAID);
            salesOrderRepository.save(so);
        }

        // Automatic Financial Transaction:
        // Debit: Bank, Credit: Beach Event Permit Revenue
        Account debitAccount = accountRepository.findByAccountName("Bank")
                .orElseGet(() -> accountRepository.findByAccountType(AccountType.ASSET).stream().findFirst()
                        .orElseGet(() -> accountRepository.save(new Account(null, "1004", "Bank", AccountType.ASSET, 500000.0))));

        Account creditAccount = accountRepository.findByAccountName("Beach Event Permit Revenue")
                .orElseGet(() -> accountRepository.findByAccountType(AccountType.INCOME).stream().findFirst()
                        .orElseGet(() -> accountRepository.save(new Account(null, "3001", "Beach Event Permit Revenue", AccountType.INCOME, 50000.0))));

        FinancialTransaction tx = new FinancialTransaction();
        tx.setTransactionDate(LocalDate.now());
        tx.setTransactionType(TransactionType.BANK);
        tx.setReferenceNumber("RCPT-" + invoice.getInvoiceNumber());
        tx.setDescription("Customer permit payment from " + invoice.getCustomer().getName() + " for " + invoice.getInvoiceNumber());
        tx.setDebitAccount(debitAccount);
        tx.setCreditAccount(creditAccount);
        tx.setAmount(invoice.getAmount());
        transactionRepository.save(tx);

        return invoiceRepository.save(invoice);
    }
}
