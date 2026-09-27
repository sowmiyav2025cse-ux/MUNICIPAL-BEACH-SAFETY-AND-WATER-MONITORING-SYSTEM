package com.example.beachsafety.controller.ui;

import com.example.beachsafety.dto.*;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class WebViewController {

    private final BeachSectorService sectorService;
    private final ContactService contactService;
    private final ProductService productService;
    private final WaterQualityService waterQualityService;
    private final PurchaseOrderService poService;
    private final VendorBillService billService;
    private final SalesOrderService salesOrderService;
    private final CustomerInvoiceService invoiceService;
    private final AccountService accountService;
    private final FinancialTransactionService transactionService;
    private final BudgetService budgetService;
    private final ReportService reportService;

    public WebViewController(BeachSectorService sectorService,
                             ContactService contactService,
                             ProductService productService,
                             WaterQualityService waterQualityService,
                             PurchaseOrderService poService,
                             VendorBillService billService,
                             SalesOrderService salesOrderService,
                             CustomerInvoiceService invoiceService,
                             AccountService accountService,
                             FinancialTransactionService transactionService,
                             BudgetService budgetService,
                             ReportService reportService) {
        this.sectorService = sectorService;
        this.contactService = contactService;
        this.productService = productService;
        this.waterQualityService = waterQualityService;
        this.poService = poService;
        this.billService = billService;
        this.salesOrderService = salesOrderService;
        this.invoiceService = invoiceService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.reportService = reportService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", reportService.getDashboardStats());
        model.addAttribute("recentSamples", waterQualityService.getAllSamples().stream().limit(5).toList());
        model.addAttribute("sectors", sectorService.getAllSectors());
        return "dashboard";
    }

    // Sectors
    @GetMapping("/sectors")
    public String sectors(Model model) {
        model.addAttribute("sectors", sectorService.getAllSectors());
        return "sectors";
    }

    @PostMapping("/sectors/create")
    public String createSector(@ModelAttribute BeachSector sector, RedirectAttributes ra) {
        sectorService.createSector(sector);
        ra.addFlashAttribute("successMessage", "Beach sector '" + sector.getSectorName() + "' created successfully!");
        return "redirect:/sectors";
    }

    // Contacts
    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        model.addAttribute("labs", contactService.getContactsByType(ContactType.TESTING_LAB_VENDOR));
        model.addAttribute("vendors", contactService.getContactsByType(ContactType.EQUIPMENT_VENDOR));
        model.addAttribute("customers", contactService.getContactsByType(ContactType.CUSTOMER));
        return "contacts";
    }

    @PostMapping("/contacts/create")
    public String createContact(@ModelAttribute Contact contact, RedirectAttributes ra) {
        contactService.createContact(contact);
        ra.addFlashAttribute("successMessage", "Contact '" + contact.getName() + "' created successfully!");
        return "redirect:/contacts";
    }

    // Products
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute Product product, RedirectAttributes ra) {
        productService.createProduct(product);
        ra.addFlashAttribute("successMessage", "Product / Service '" + product.getName() + "' created successfully!");
        return "redirect:/products";
    }

    // Water Quality
    @GetMapping("/water-quality")
    public String waterQuality(Model model) {
        model.addAttribute("samples", waterQualityService.getAllSamples());
        model.addAttribute("sectors", sectorService.getActiveSectors());
        return "water-quality";
    }

    @PostMapping("/water-quality/create")
    public String recordWaterSample(@ModelAttribute WaterQualitySampleRequest request, RedirectAttributes ra) {
        WaterQualitySample sample = waterQualityService.recordSample(request);
        String alertMsg;
        if (sample.getStatus() == WaterQualityStatus.RED) {
            alertMsg = "RED ALERT! Bacterial count (" + sample.getBacterialCount() + ") exceeded safety threshold. Sector status changed to RED!";
        } else if (sample.getStatus() == WaterQualityStatus.YELLOW) {
            alertMsg = "Warning: Bacterial count is elevated. Sector status updated to YELLOW.";
        } else {
            alertMsg = "Water quality sample recorded successfully. Sector status is GREEN (Safe).";
        }
        ra.addFlashAttribute("successMessage", alertMsg);
        return "redirect:/water-quality";
    }

    // Purchase Orders
    @GetMapping("/purchase-orders")
    public String purchaseOrders(Model model) {
        model.addAttribute("orders", poService.getAllPurchaseOrders());
        model.addAttribute("vendors", contactService.getAllContacts());
        model.addAttribute("products", productService.getAllProducts());
        return "purchase-orders";
    }

    @PostMapping("/purchase-orders/create")
    public String createPurchaseOrder(@ModelAttribute PurchaseOrderRequest request, RedirectAttributes ra) {
        PurchaseOrder po = poService.createPurchaseOrder(request);
        ra.addFlashAttribute("successMessage", "Purchase Order '" + po.getPoNumber() + "' created successfully!");
        return "redirect:/purchase-orders";
    }

    @PostMapping("/purchase-orders/{id}/convert-to-bill")
    public String convertPoToBill(@PathVariable Long id, RedirectAttributes ra) {
        PurchaseOrder po = poService.getPurchaseOrderById(id);
        VendorBillRequest req = new VendorBillRequest();
        req.setPurchaseOrderId(po.getId());
        req.setVendorId(po.getVendor().getId());
        req.setAmount(po.getTotalAmount());
        req.setBillDate(LocalDate.now());
        VendorBill bill = billService.createVendorBill(req);
        ra.addFlashAttribute("successMessage", "Purchase Order '" + po.getPoNumber() + "' converted to Vendor Bill '" + bill.getBillNumber() + "'!");
        return "redirect:/vendor-bills";
    }

    // Vendor Bills
    @GetMapping("/vendor-bills")
    public String vendorBills(Model model) {
        model.addAttribute("bills", billService.getAllVendorBills());
        model.addAttribute("vendors", contactService.getAllContacts());
        model.addAttribute("pos", poService.getAllPurchaseOrders());
        return "vendor-bills";
    }

    @PostMapping("/vendor-bills/create")
    public String createVendorBill(@ModelAttribute VendorBillRequest request, RedirectAttributes ra) {
        VendorBill bill = billService.createVendorBill(request);
        ra.addFlashAttribute("successMessage", "Vendor Bill '" + bill.getBillNumber() + "' created successfully!");
        return "redirect:/vendor-bills";
    }

    @PostMapping("/vendor-bills/{id}/pay")
    public String payVendorBill(@PathVariable Long id, RedirectAttributes ra) {
        VendorBill bill = billService.payVendorBill(id);
        ra.addFlashAttribute("successMessage", "Vendor Bill '" + bill.getBillNumber() + "' paid! Financial transaction generated and budget updated.");
        return "redirect:/vendor-bills";
    }

    // Sales Orders
    @GetMapping("/sales-orders")
    public String salesOrders(Model model) {
        model.addAttribute("orders", salesOrderService.getAllSalesOrders());
        model.addAttribute("customers", contactService.getContactsByType(ContactType.CUSTOMER));
        model.addAttribute("products", productService.getAllProducts());
        return "sales-orders";
    }

    @PostMapping("/sales-orders/create")
    public String createSalesOrder(@ModelAttribute SalesOrderRequest request, RedirectAttributes ra) {
        SalesOrder so = salesOrderService.createSalesOrder(request);
        ra.addFlashAttribute("successMessage", "Sales Order '" + so.getOrderNumber() + "' created successfully!");
        return "redirect:/sales-orders";
    }

    @PostMapping("/sales-orders/{id}/convert-to-invoice")
    public String convertSoToInvoice(@PathVariable Long id, RedirectAttributes ra) {
        SalesOrder so = salesOrderService.getSalesOrderById(id);
        CustomerInvoiceRequest req = new CustomerInvoiceRequest();
        req.setSalesOrderId(so.getId());
        req.setCustomerId(so.getCustomer().getId());
        req.setAmount(so.getTotalAmount());
        req.setInvoiceDate(LocalDate.now());
        CustomerInvoice inv = invoiceService.createCustomerInvoice(req);
        ra.addFlashAttribute("successMessage", "Sales Order '" + so.getOrderNumber() + "' converted to Customer Invoice '" + inv.getInvoiceNumber() + "'!");
        return "redirect:/customer-invoices";
    }

    // Customer Invoices
    @GetMapping("/customer-invoices")
    public String customerInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllCustomerInvoices());
        model.addAttribute("customers", contactService.getContactsByType(ContactType.CUSTOMER));
        model.addAttribute("sos", salesOrderService.getAllSalesOrders());
        return "customer-invoices";
    }

    @PostMapping("/customer-invoices/create")
    public String createCustomerInvoice(@ModelAttribute CustomerInvoiceRequest request, RedirectAttributes ra) {
        CustomerInvoice inv = invoiceService.createCustomerInvoice(request);
        ra.addFlashAttribute("successMessage", "Customer Invoice '" + inv.getInvoiceNumber() + "' created successfully!");
        return "redirect:/customer-invoices";
    }

    @PostMapping("/customer-invoices/{id}/pay")
    public String payCustomerInvoice(@PathVariable Long id, RedirectAttributes ra) {
        CustomerInvoice inv = invoiceService.payCustomerInvoice(id);
        ra.addFlashAttribute("successMessage", "Customer Invoice '" + inv.getInvoiceNumber() + "' marked PAID! Permit revenue recorded in Financial Ledger.");
        return "redirect:/customer-invoices";
    }

    // Accounts
    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "accounts";
    }

    @PostMapping("/accounts/create")
    public String createAccount(@ModelAttribute Account account, RedirectAttributes ra) {
        accountService.createAccount(account);
        ra.addFlashAttribute("successMessage", "Account '" + account.getAccountName() + "' added to Chart of Accounts!");
        return "redirect:/accounts";
    }

    // Financial Transactions
    @GetMapping("/transactions")
    public String transactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "transactions";
    }

    @PostMapping("/transactions/create")
    public String createTransaction(@ModelAttribute FinancialTransactionRequest request, RedirectAttributes ra) {
        FinancialTransaction tx = transactionService.recordTransaction(request);
        ra.addFlashAttribute("successMessage", "Financial Transaction '" + tx.getReferenceNumber() + "' saved to general ledger!");
        return "redirect:/transactions";
    }

    // Budgets
    @GetMapping("/budgets")
    public String budgets(Model model) {
        model.addAttribute("budgets", budgetService.getAllBudgets());
        model.addAttribute("sectors", sectorService.getAllSectors());
        return "budgets";
    }

    @PostMapping("/budgets/save")
    public String saveBudget(@ModelAttribute BudgetRequest request, RedirectAttributes ra) {
        Budget b = budgetService.createOrUpdateBudget(request);
        ra.addFlashAttribute("successMessage", "Budget for '" + b.getSector().getSectorName() + "' saved! Variance: " + b.getVariance());
        return "redirect:/budgets";
    }

    // Reports Hub
    @GetMapping("/reports")
    public String reportsHub(Model model) {
        model.addAttribute("waterQualityReports", reportService.getWaterQualityReport());
        model.addAttribute("profitLoss", reportService.getProfitLossReport());
        model.addAttribute("budgetReports", reportService.getBudgetReport());
        model.addAttribute("balanceSheet", reportService.getBalanceSheetReport());
        return "reports";
    }

    // Profit & Loss Report View
    @GetMapping({"/profit-loss", "/reports/profit-loss-view"})
    public String profitLossView(Model model) {
        model.addAttribute("report", reportService.getProfitLossReport());
        return "profit-loss";
    }

    // Balance Sheet Report View
    @GetMapping({"/balance-sheet", "/reports/balance-sheet-view"})
    public String balanceSheetView(Model model) {
        model.addAttribute("report", reportService.getBalanceSheetReport());
        return "balance-sheet";
    }
}
