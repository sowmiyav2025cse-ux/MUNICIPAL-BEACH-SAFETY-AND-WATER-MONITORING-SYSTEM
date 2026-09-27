package com.example.beachsafety.repository;

import com.example.beachsafety.entity.BillStatus;
import com.example.beachsafety.entity.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {
    Optional<CustomerInvoice> findByInvoiceNumber(String invoiceNumber);
    List<CustomerInvoice> findByStatus(BillStatus status);
    List<CustomerInvoice> findAllByOrderByInvoiceDateDesc();

    @Query("SELECT COALESCE(SUM(i.amount), 0.0) FROM CustomerInvoice i WHERE i.status = :status")
    Double sumAmountByStatus(BillStatus status);
}
