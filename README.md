# Municipal Smart Public Beach Safety & Water Quality Monitoring System

A complete, production-ready, monolithic Java Spring Boot web application designed for municipal coastal administrations. The system monitors recreational beach zones, executes automated microbiological water quality safety classifications (GREEN, YELLOW, RED Alert), manages laboratory procurement and commercial event permits, and maintains double-entry accounting ledgers and budgets.

---

## 1. Project Objective

The primary objective is to safeguard public beachgoers by automating coastal safety administration:
1. **Automated Safety Classification:** Calculates microbiological safety status from bacterial counts (CFU/100ml) against configured sector thresholds and immediately triggers visual **RED ALERTS** across the dashboard when safe limits are breached.
2. **End-to-End Procurement:** Procures certified laboratory water testing and rescue gear via Purchase Orders and Vendor Bills.
3. **Revenue Management:** Generates Commercial Beach Event Permits, Sales Orders, and Customer Invoices for event organizers.
4. **Municipal Financial Governance:** Maintains a double-entry General Ledger (Journal), Chart of Accounts, Beach Zone Budgets with variance calculation, and generates Profit & Loss and Balance Sheet statements.

---

## 2. Key Features

- **🏖️ Beach Sector Master:** Configure coastal sectors, locations, and microbiological bacterial safety thresholds (CFU/100ml).
- **🧪 Automated Safety Engine (Core Highlight):**
  - **GREEN (Safe):** Bacterial Count $\le$ Safety Threshold &rarr; Safe for public recreation.
  - **YELLOW (Warning):** Safety Threshold $<$ Bacterial Count $\le 1.5 \times$ Safety Threshold &rarr; Elevated bacterial levels.
  - **RED (Unsafe / Alert):** Bacterial Count $> 1.5 \times$ Safety Threshold &rarr; Triggers **RED ALERT – IMMEDIATE BEACH SAFETY ATTENTION REQUIRED** banner on dashboard and marks sector as RED.
- **👥 Contact Master:** Categorized directories for:
  - Water Testing Laboratories (`TESTING_LAB_VENDOR`)
  - Safety Equipment Vendors (`EQUIPMENT_VENDOR`)
  - Beach Event Organizers (`CUSTOMER`)
- **📦 Product & Service Master:** Standard municipal catalog including Commercial Beach Event Permits, Certified Testing Services, and Rescue Jet-Skis.
- **📋 Procurement Workflow:** Purchase Orders (PO) &rarr; Convert to Vendor Bills &rarr; Settle via Bank &rarr; Auto-post debit to Expenses and credit to Bank.
- **💳 Commercial Revenue Workflow:** Commercial Sales Orders &rarr; Convert to Customer Invoices &rarr; Record Customer Receipts &rarr; Auto-post debit to Bank and credit to Permit Revenue.
- **🎯 Budget & Variance:** Track planned allocations vs. actual expenditures per beach sector with live variance computation.
- **📖 Chart of Accounts & General Ledger:** Complete double-entry accounting for Assets, Liabilities, Income, and Expenses.
- **📊 Real-time Reports:**
  - Water Quality Compliance Log with threshold warnings
  - Municipal Profit & Loss Statement
  - Budget vs. Actual Expenditure Variance Report
  - Municipal Balance Sheet (Assets, Liabilities, Net Worth)

---

## 3. Technology Stack

- **Backend:** Java 17 / Java 21, Spring Boot 3.2.4
- **Persistence:** Spring Data JPA, Hibernate ORM
- **Web Layer:** Spring Web MVC (REST API + Thymeleaf UI)
- **Database:** MySQL 8.0+ (with fallback H2 in-memory profile)
- **Frontend:** Thymeleaf templates, HTML5, CSS3, Bootstrap 5.3.3
- **Build Tool:** Apache Maven
- **API Testing:** Postman Collection (`Beach-Safety-System.postman_collection.json`)

---

## 4. Software Requirements

Before running the project, ensure you have:
1. **Java Development Kit (JDK 17 or higher)**
   - Check with: `java -version`
2. **Apache Maven 3.8+**
   - Check with: `mvn -v`
3. **MySQL Server 8.0+** and **MySQL Workbench** (Optional: H2 in-memory mode requires zero installation)
4. **IDE:** IntelliJ IDEA, VS Code (with Java Extension Pack), or Eclipse

---

## 5. MySQL Database Setup

### Step 1: Start MySQL
Ensure your local MySQL service is running. On Windows, check with:
```powershell
Get-Service -Name *mysql*
```

### Step 2: Create Database & Sample Data in MySQL Workbench
1. Open **MySQL Workbench**.
2. Connect to your local MySQL instance (`localhost:3306`).
3. Open and execute the script:
   ```
   database.sql
   ```
   *(Located in the project root directory).*
4. This creates the database `beach_safety_db` and pre-loads all sample beach sectors, laboratory contacts, products, sample water tests (including a RED ALERT test), and accounts.

---

## 6. How to Configure Database Password

Open `src/main/resources/application.properties` and update your MySQL username and password:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/beach_safety_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

> **Tip for Viva / Offline Testing:** If MySQL is not installed or configured, you can launch the app instantly using the built-in **H2 in-memory mode**:
> ```powershell
> mvn spring-boot:run -Dspring-boot.run.profiles=h2
> ```
> This starts the entire application with pre-loaded demo data without needing MySQL running!

---

## 7. How to Build & Run the Application

### Option A: From the Terminal / PowerShell

1. Navigate to the project root directory:
   ```powershell
   cd "c:\Users\sowmi\Downloads\municipal beach safety pro"
   ```
2. Build the project:
   ```powershell
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```powershell
   mvn spring-boot:run
   ```

### Option B: Running from VS Code / IntelliJ IDEA
1. Open the project folder in **IntelliJ IDEA** or **VS Code**.
2. Let Maven import the dependencies from `pom.xml`.
3. Locate `src/main/java/com/example/beachsafety/BeachSafetyApplication.java`.
4. Right-click and choose **Run 'BeachSafetyApplication.main()'**.

---

## 8. Accessing the Dashboard & Web Pages

Once started, open your browser and navigate to:

```
http://localhost:8080/dashboard
```
or simply:
```
http://localhost:8080/
```

### Application Page Directory:
| Module | URL | Description |
| :--- | :--- | :--- |
| **Dashboard** | `http://localhost:8080/dashboard` | Key metric cards, large safety status indicators, and RED ALERT banner |
| **Beach Sectors** | `http://localhost:8080/sectors` | Manage beach zones, locations, and safety thresholds |
| **Water Quality** | `http://localhost:8080/water-quality` | Record microbiological samples and view auto-evaluations |
| **Contacts** | `http://localhost:8080/contacts` | Directory for Labs, Equipment Vendors, and Event Organizers |
| **Products & Services** | `http://localhost:8080/products` | Municipal service catalog and equipment pricing |
| **Purchase Orders** | `http://localhost:8080/purchase-orders` | Issue POs for weekly lab testing & convert to bills |
| **Vendor Bills** | `http://localhost:8080/vendor-bills` | Track payables and settle via Bank payment |
| **Sales Orders** | `http://localhost:8080/sales-orders` | Commercial beach event permit bookings |
| **Customer Invoices** | `http://localhost:8080/customer-invoices` | Track receivables and record customer receipts |
| **Beach Budgets** | `http://localhost:8080/budgets` | Planned vs Actual spending variance per sector |
| **Chart of Accounts** | `http://localhost:8080/accounts` | Asset, Liability, Income, and Expense accounts |
| **General Ledger** | `http://localhost:8080/transactions` | Double-entry journal entries with Dr and Cr |
| **Reports Hub** | `http://localhost:8080/reports` | Consolidated view of safety & financial reports |
| **Profit & Loss** | `http://localhost:8080/profit-loss` | Formal municipal surplus / deficit operating statement |
| **Balance Sheet** | `http://localhost:8080/balance-sheet` | Formal statement of municipal financial position |

---

## 9. Recommended 18-Step Demo Sequence

Follow this live demo sequence for your presentation:

1. **Step 1 - View Dashboard:** Open `http://localhost:8080/dashboard`. Point out the 8 metric cards, the large safety status indicators, and the **RED ALERT** banner triggered by North Public Beach Zone 2.
2. **Step 2 - Inspect Beach Sectors:** Go to `/sectors`. Review the safety threshold (100 CFU) configured for Central Public Beach Zone 1.
3. **Step 3 - Review Contacts:** Go to `/contacts`. Show the three tabs: Water Testing Labs, Equipment Suppliers, and Event Organizers.
4. **Step 4 - Review Products:** Go to `/products`. Show the Commercial Event Permit (₹25,000) and Water Quality Testing Service (₹5,000).
5. **Step 5 - Issue Purchase Order:** Go to `/purchase-orders`. Click **+ Create Purchase Order**, select Coastal Water Testing Lab and Water Quality Testing Service (Qty: 2, Total: ₹10,000).
6. **Step 6 - Convert PO to Bill:** On the newly created PO, click **Convert to Bill**. The system redirects to `/vendor-bills` with status `UNPAID`.
7. **Step 7 - Settle Vendor Bill:** Click **💳 Pay via Bank**. The bill status changes to `PAID`.
8. **Step 8 - Check Financial Ledger:** Go to `/transactions`. Verify that an automatic double-entry transaction was posted:
   - Debit: `Lab Testing Expenses`
   - Credit: `Bank`
9. **Step 9 - Check Budget Variance:** Go to `/budgets`. Notice that the actual spent has increased and variance has updated automatically.
10. **Step 10 - Record Safe Water Test:** Go to `/water-quality`. Click **+ Record Water Sample**. Select Zone 1, enter bacterial count `85` (Threshold: 100). Click **Submit**. Notice status is `GREEN` ("Water Quality Safe").
11. **Step 11 - Demonstrate Automatic RED ALERT:** Click **+ Record Water Sample** again. Select Zone 1, enter bacterial count `190` (exceeds $1.5 \times 100$). Click **Submit**.
    - Notice sample status immediately turns `RED`.
    - Zone 1's sector status updates to `RED`.
12. **Step 12 - Verify Dashboard RED ALERT:** Return to `/dashboard`. Notice the prominent flashing **RED ALERT – IMMEDIATE BEACH SAFETY ATTENTION REQUIRED** banner and Zone 1 indicator in RED!
13. **Step 13 - Commercial Booking (Sales Order):** Go to `/sales-orders`. Click **+ Create Sales Order**. Select customer *Beach Sports Association*, product *Commercial Beach Event Permit* (Qty: 1, ₹25,000).
14. **Step 14 - Convert SO to Invoice:** Click **Convert to Invoice**. The system redirects to `/customer-invoices` with status `UNPAID`.
15. **Step 15 - Record Customer Payment:** Click **💰 Record Bank Receipt**. The invoice turns `PAID`.
16. **Step 16 - Verify Ledger Receipt:** Go to `/transactions`. Verify the new automated journal entry:
    - Debit: `Bank`
    - Credit: `Beach Event Permit Revenue`
17. **Step 17 - View Profit & Loss Statement:** Go to `/profit-loss`. Verify that total permit income, operating expenses, and net municipal surplus are computed accurately.
18. **Step 18 - View Balance Sheet:** Go to `/balance-sheet`. Show the Asset breakdown (Crafts, Bank, Cash), Liabilities, and net municipal equity.

---

## 10. REST API Catalog & Postman Collection

Import `Beach-Safety-System.postman_collection.json` into Postman to test all endpoints:

### Beach Sectors
- `GET /api/beach/sectors` - List all beach sectors
- `GET /api/beach/sectors/{id}` - Get sector details
- `POST /api/beach/sectors` - Create new sector
- `PUT /api/beach/sectors/{id}` - Update sector
- `DELETE /api/beach/sectors/{id}` - Delete sector

### Water Quality Samples
- `GET /api/beach/samples` - List all water samples
- `GET /api/beach/samples/{id}` - Get sample by ID
- `GET /api/beach/samples/sector/{sectorId}` - Get samples for specific beach sector
- `POST /api/beach/samples` - Record sample and trigger auto-status determination

### Contacts & Products
- `GET /api/contacts` / `POST /api/contacts`
- `GET /api/products` / `POST /api/products`

### Procurement & Invoicing
- `GET /api/purchase-orders` / `POST /api/purchase-orders`
- `PUT /api/purchase-orders/{id}/status`
- `GET /api/vendor-bills` / `POST /api/vendor-bills`
- `POST /api/vendor-bills/{id}/pay` - Pay bill & auto-generate financial transaction
- `GET /api/sales-orders` / `POST /api/sales-orders`
- `GET /api/customer-invoices` / `POST /api/customer-invoices`
- `POST /api/customer-invoices/{id}/pay` - Receive payment & auto-generate revenue transaction

### Accounting, Budgets & Reports
- `GET /api/accounts` / `POST /api/accounts`
- `GET /api/transactions` / `POST /api/transactions`
- `GET /api/budgets` / `POST /api/budgets`
- `GET /api/reports/water-quality` - Water quality compliance report
- `GET /api/reports/profit-loss` - Income, expenses, and net surplus
- `GET /api/reports/budget` - Budget allocations and variance
- `GET /api/reports/balance-sheet` - Assets, liabilities, and net worth
- `GET /api/reports/dashboard` - Real-time statistics

---

## 11. Database Schema Overview

```
beach_sectors (id, sector_name, location, description, safety_threshold, current_status, active)
       │
       ├──< water_quality_samples (id, sector_id, sample_date, bacterial_count, ph, temp, turbidity, status, remarks)
       │
       └──< budgets (id, sector_id, financial_year, planned_amount, actual_amount, variance)

contacts (id, name, contact_type, email, phone, address, active)
       │
products (id, name, type, description, unit_price, active)
       │
       ├──< purchase_orders (id, po_number, vendor_id, product_id, quantity, unit_price, total_amount, status)
       │           │
       │           └──< vendor_bills (id, bill_number, purchase_order_id, vendor_id, amount, status)
       │
       └──< sales_orders (id, order_number, customer_id, product_id, quantity, unit_price, total_amount, status)
                   │
                   └──< customer_invoices (id, invoice_number, sales_order_id, customer_id, amount, status)

accounts (id, account_code, account_name, account_type, opening_balance)
       │
       └──< financial_transactions (id, txn_date, txn_type, ref_num, description, debit_account_id, credit_account_id, amount)
```

---

## 12. Project Structure

```
municipal-beach-safety-pro/
├── pom.xml
├── database.sql
├── Beach-Safety-System.postman_collection.json
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/beachsafety/
│   │   │   ├── BeachSafetyApplication.java
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/
│   │   │   │   ├── BeachSectorRestController.java
│   │   │   │   ├── WaterQualityRestController.java
│   │   │   │   ├── ContactRestController.java
│   │   │   │   ├── ProductRestController.java
│   │   │   │   ├── PurchaseOrderRestController.java
│   │   │   │   ├── VendorBillRestController.java
│   │   │   │   ├── SalesOrderRestController.java
│   │   │   │   ├── CustomerInvoiceRestController.java
│   │   │   │   ├── AccountRestController.java
│   │   │   │   ├── FinancialTransactionRestController.java
│   │   │   │   ├── BudgetRestController.java
│   │   │   │   ├── ReportRestController.java
│   │   │   │   └── ui/
│   │   │   │       └── WebViewController.java
│   │   │   ├── dto/
│   │   │   │   ├── WaterQualitySampleRequest.java
│   │   │   │   ├── PurchaseOrderRequest.java
│   │   │   │   ├── VendorBillRequest.java
│   │   │   │   ├── SalesOrderRequest.java
│   │   │   │   ├── CustomerInvoiceRequest.java
│   │   │   │   ├── FinancialTransactionRequest.java
│   │   │   │   ├── BudgetRequest.java
│   │   │   │   ├── DashboardStatsDTO.java
│   │   │   │   ├── WaterQualityReportDTO.java
│   │   │   │   ├── ProfitLossReportDTO.java
│   │   │   │   ├── BudgetReportDTO.java
│   │   │   │   └── BalanceSheetReportDTO.java
│   │   │   ├── entity/
│   │   │   │   ├── BeachSector.java
│   │   │   │   ├── Contact.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── WaterQualitySample.java
│   │   │   │   ├── PurchaseOrder.java
│   │   │   │   ├── VendorBill.java
│   │   │   │   ├── SalesOrder.java
│   │   │   │   ├── CustomerInvoice.java
│   │   │   │   ├── Account.java
│   │   │   │   ├── FinancialTransaction.java
│   │   │   │   ├── Budget.java
│   │   │   │   ├── ContactType.java
│   │   │   │   ├── ProductType.java
│   │   │   │   ├── WaterQualityStatus.java
│   │   │   │   ├── OrderStatus.java
│   │   │   │   ├── BillStatus.java
│   │   │   │   ├── AccountType.java
│   │   │   │   └── TransactionType.java
│   │   │   ├── exception/
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── InvalidInputException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   ├── BeachSectorRepository.java
│   │   │   │   ├── ContactRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── WaterQualitySampleRepository.java
│   │   │   │   ├── PurchaseOrderRepository.java
│   │   │   │   ├── VendorBillRepository.java
│   │   │   │   ├── SalesOrderRepository.java
│   │   │   │   ├── CustomerInvoiceRepository.java
│   │   │   │   ├── AccountRepository.java
│   │   │   │   ├── FinancialTransactionRepository.java
│   │   │   │   └── BudgetRepository.java
│   │   │   └── service/
│   │   │       ├── BeachSectorService.java
│   │   │       ├── ContactService.java
│   │   │       ├── ProductService.java
│   │   │       ├── WaterQualityService.java
│   │   │       ├── PurchaseOrderService.java
│   │   │       ├── VendorBillService.java
│   │   │       ├── SalesOrderService.java
│   │   │       ├── CustomerInvoiceService.java
│   │   │       ├── AccountService.java
│   │   │       ├── FinancialTransactionService.java
│   │   │       ├── BudgetService.java
│   │   │       └── ReportService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-h2.properties
│   │       ├── data.sql
│   │       ├── static/css/
│   │       │   └── style.css
│   │       └── templates/
│   │           ├── fragments/
│   │           │   └── navbar.html
│   │           ├── dashboard.html
│   │           ├── sectors.html
│   │           ├── contacts.html
│   │           ├── products.html
│   │           ├── water-quality.html
│   │           ├── purchase-orders.html
│   │           ├── vendor-bills.html
│   │           ├── sales-orders.html
│   │           ├── customer-invoices.html
│   │           ├── accounts.html
│   │           ├── transactions.html
│   │           ├── budgets.html
│   │           ├── reports.html
│   │           ├── profit-loss.html
│   │           └── balance-sheet.html
```
