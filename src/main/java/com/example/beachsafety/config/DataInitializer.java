package com.example.beachsafety.config;

import com.example.beachsafety.entity.*;
import com.example.beachsafety.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final BeachSectorRepository sectorRepository;
    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;
    private final WaterQualitySampleRepository sampleRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final PurchaseOrderRepository poRepository;
    private final VendorBillRepository billRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final CustomerInvoiceRepository invoiceRepository;
    private final FinancialTransactionRepository transactionRepository;

    public DataInitializer(BeachSectorRepository sectorRepository,
                           ContactRepository contactRepository,
                           ProductRepository productRepository,
                           WaterQualitySampleRepository sampleRepository,
                           AccountRepository accountRepository,
                           BudgetRepository budgetRepository,
                           PurchaseOrderRepository poRepository,
                           VendorBillRepository billRepository,
                           SalesOrderRepository salesOrderRepository,
                           CustomerInvoiceRepository invoiceRepository,
                           FinancialTransactionRepository transactionRepository) {
        this.sectorRepository = sectorRepository;
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
        this.sampleRepository = sampleRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.poRepository = poRepository;
        this.billRepository = billRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {
        if (sectorRepository.count() > 0) {
            log.info("Database already seeded with demo data. Skipping initialization.");
            return;
        }

        log.info("Starting initial seed data population for Municipal Beach Safety System...");

        // 1. Beach Sectors
        BeachSector zone1 = new BeachSector(null, "Central Public Beach Zone 1", "Marina Promenade Bay",
                "Primary municipal beach sector with family recreation area", 100.0, WaterQualityStatus.GREEN, true);
        BeachSector zone2 = new BeachSector(null, "North Public Beach Zone 2", "North Cliff Estuary",
                "Secondary municipal surf and open water zone", 100.0, WaterQualityStatus.RED, true);
        zone1 = sectorRepository.save(zone1);
        zone2 = sectorRepository.save(zone2);

        // 2. Contacts (Vendors, Labs, Event Organizers)
        Contact lab1 = new Contact(null, "Coastal Water Testing Lab", ContactType.TESTING_LAB_VENDOR,
                "contact@coastallabs.org", "+91-98765-43210", "42 Ocean Way, Coastal District", true);
        Contact lab2 = new Contact(null, "Ocean Metrology & Environmental Labs", ContactType.TESTING_LAB_VENDOR,
                "reports@oceanmetrology.com", "+91-98765-11223", "88 Harbor Science Park", true);

        Contact vendor1 = new Contact(null, "Ocean Rescue Equipment Pvt Ltd", ContactType.EQUIPMENT_VENDOR,
                "sales@oceanrescue.com", "+91-98765-88990", "12 Marina Marine Industrial Estate", true);
        Contact vendor2 = new Contact(null, "AquaMarine Safety Gear Ltd", ContactType.EQUIPMENT_VENDOR,
                "info@aquamarinesafety.in", "+91-98765-77665", "90 Coastguard Road", true);

        Contact customer1 = new Contact(null, "Beach Sports Association", ContactType.CUSTOMER,
                "events@beachsports.org", "+91-98765-33445", "101 Promenade Avenue", true);
        Contact customer2 = new Contact(null, "Coastal Tourism & Marathon Committee", ContactType.CUSTOMER,
                "info@coastalmarathon.com", "+91-98765-99887", "15 Bayfront Plaza", true);

        lab1 = contactRepository.save(lab1);
        lab2 = contactRepository.save(lab2);
        vendor1 = contactRepository.save(vendor1);
        vendor2 = contactRepository.save(vendor2);
        customer1 = contactRepository.save(customer1);
        customer2 = contactRepository.save(customer2);

        // 3. Products / Services
        Product prod1 = new Product(null, "Commercial Beach Event Permit", ProductType.SERVICE,
                "Official municipal clearance permit for hosting commercial coastal events", 25000.0, true);
        Product prod2 = new Product(null, "Water Quality Testing Service", ProductType.SERVICE,
                "Weekly certified laboratory microbiological and physicochemical testing", 5000.0, true);
        Product prod3 = new Product(null, "Rescue Jet-Ski Equipment", ProductType.GOODS,
                "High-performance marine search and rescue jet-ski patrol craft", 150000.0, true);

        prod1 = productRepository.save(prod1);
        prod2 = productRepository.save(prod2);
        prod3 = productRepository.save(prod3);

        // 4. Accounts (Chart of Accounts)
        Account accRescueCraft = accountRepository.save(new Account(null, "1001", "Rescue Watercraft Assets", AccountType.ASSET, 150000.0));
        Account accTestingGear = accountRepository.save(new Account(null, "1002", "Testing Gear", AccountType.ASSET, 25000.0));
        Account accCash = accountRepository.save(new Account(null, "1003", "Cash", AccountType.ASSET, 50000.0));
        Account accBank = accountRepository.save(new Account(null, "1004", "Bank", AccountType.ASSET, 500000.0));

        Account accLabPayables = accountRepository.save(new Account(null, "2001", "Testing Lab Payables", AccountType.LIABILITY, 0.0));
        Account accEquipCreditors = accountRepository.save(new Account(null, "2002", "Equipment Creditors", AccountType.LIABILITY, 0.0));

        Account accPermitRevenue = accountRepository.save(new Account(null, "3001", "Beach Event Permit Revenue", AccountType.INCOME, 50000.0));
        Account accCoastalFees = accountRepository.save(new Account(null, "3002", "Coastal Commercial Fees", AccountType.INCOME, 20000.0));

        Account accLabExpenses = accountRepository.save(new Account(null, "4001", "Lab Testing Expenses", AccountType.EXPENSE, 15000.0));
        Account accFuelMaintenance = accountRepository.save(new Account(null, "4002", "Rescue Craft Fuel & Maintenance", AccountType.EXPENSE, 10000.0));

        // 5. Water Quality Samples (Zone 1 = SAFE, Zone 2 = RED ALERT)
        WaterQualitySample sample1 = new WaterQualitySample(null, zone1, LocalDate.now().minusDays(1),
                80.0, 7.8, 24.5, 2.1, WaterQualityStatus.GREEN, "Routine weekly testing - Water Quality Safe");
        WaterQualitySample sample2 = new WaterQualitySample(null, zone2, LocalDate.now(),
                180.0, 8.1, 26.0, 4.5, WaterQualityStatus.RED, "RED ALERT: Unsafe Water Quality Detected (Exceeds 1.5x threshold)");
        sampleRepository.save(sample1);
        sampleRepository.save(sample2);

        // 6. Budgets
        Budget budget1 = new Budget(null, zone1, "2026-2027", 100000.0, 75000.0);
        Budget budget2 = new Budget(null, zone2, "2026-2027", 120000.0, 95000.0);
        budgetRepository.save(budget1);
        budgetRepository.save(budget2);

        // 7. Purchase Orders & Vendor Bills
        PurchaseOrder po1 = new PurchaseOrder(null, "PO-2026-0001", lab1, prod2, 3, 5000.0, 15000.0,
                LocalDate.now().minusDays(5), OrderStatus.BILLED);
        po1 = poRepository.save(po1);

        VendorBill bill1 = new VendorBill(null, "BILL-2026-0001", po1, lab1, LocalDate.now().minusDays(3), 15000.0, BillStatus.PAID);
        bill1 = billRepository.save(bill1);

        PurchaseOrder po2 = new PurchaseOrder(null, "PO-2026-0002", vendor1, prod3, 1, 150000.0, 150000.0,
                LocalDate.now().minusDays(2), OrderStatus.CREATED);
        poRepository.save(po2);

        // 8. Sales Orders & Customer Invoices
        SalesOrder so1 = new SalesOrder(null, "SO-2026-0001", customer1, prod1, 2, 25000.0, 50000.0,
                LocalDate.now().minusDays(4), OrderStatus.INVOICED);
        so1 = salesOrderRepository.save(so1);

        CustomerInvoice inv1 = new CustomerInvoice(null, "INV-2026-0001", so1, customer1, LocalDate.now().minusDays(2), 50000.0, BillStatus.PAID);
        inv1 = invoiceRepository.save(inv1);

        // 9. Initial Financial Transactions
        FinancialTransaction tx1 = new FinancialTransaction(null, LocalDate.now().minusDays(3), TransactionType.BANK,
                "PAY-BILL-2026-0001", "Payment to Coastal Water Testing Lab for water testing", accLabExpenses, accBank, 15000.0);
        FinancialTransaction tx2 = new FinancialTransaction(null, LocalDate.now().minusDays(2), TransactionType.BANK,
                "RCPT-INV-2026-0001", "Permit revenue receipt from Beach Sports Association", accBank, accPermitRevenue, 50000.0);
        transactionRepository.save(tx1);
        transactionRepository.save(tx2);

        log.info("Municipal Beach Safety System seed data successfully loaded!");
    }
}
