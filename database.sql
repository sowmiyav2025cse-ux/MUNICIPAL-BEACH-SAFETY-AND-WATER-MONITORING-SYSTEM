-- ============================================================================
-- Municipal Smart Public Beach Safety & Water Quality Monitoring System
-- Database Setup & Sample Seed Script
-- Database Name: beach_safety_db
-- Target: MySQL 8.0+ / MySQL Workbench
-- ============================================================================

CREATE DATABASE IF NOT EXISTS `beach_safety_db` 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `beach_safety_db`;

-- Disable Foreign Key checks for clean recreation
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `financial_transactions`;
DROP TABLE IF EXISTS `customer_invoices`;
DROP TABLE IF EXISTS `sales_orders`;
DROP TABLE IF EXISTS `vendor_bills`;
DROP TABLE IF EXISTS `purchase_orders`;
DROP TABLE IF EXISTS `water_quality_samples`;
DROP TABLE IF EXISTS `budgets`;
DROP TABLE IF EXISTS `accounts`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `contacts`;
DROP TABLE IF EXISTS `beach_sectors`;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. Beach Sectors Table
CREATE TABLE `beach_sectors` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sector_name` VARCHAR(100) NOT NULL,
    `location` VARCHAR(150),
    `description` VARCHAR(255),
    `safety_threshold` DOUBLE NOT NULL DEFAULT 100.0,
    `current_status` VARCHAR(20) NOT NULL DEFAULT 'GREEN',
    `active` BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Contacts Table (Vendors, Labs, Event Organizers)
CREATE TABLE `contacts` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(150) NOT NULL,
    `contact_type` VARCHAR(30) NOT NULL,
    `email` VARCHAR(100),
    `phone` VARCHAR(30),
    `address` VARCHAR(255),
    `active` BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Products & Services Table
CREATE TABLE `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(150) NOT NULL,
    `type` VARCHAR(20) NOT NULL,
    `description` VARCHAR(255),
    `unit_price` DOUBLE NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Accounts Table (Chart of Accounts)
CREATE TABLE `accounts` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `account_code` VARCHAR(20) NOT NULL UNIQUE,
    `account_name` VARCHAR(150) NOT NULL,
    `account_type` VARCHAR(20) NOT NULL,
    `opening_balance` DOUBLE NOT NULL DEFAULT 0.0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Water Quality Samples Table
CREATE TABLE `water_quality_samples` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sector_id` BIGINT NOT NULL,
    `sample_date` DATE NOT NULL,
    `bacterial_count` DOUBLE NOT NULL,
    `ph` DOUBLE NOT NULL,
    `temperature` DOUBLE NOT NULL,
    `turbidity` DOUBLE NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `remarks` VARCHAR(255),
    CONSTRAINT `fk_wqs_sector` FOREIGN KEY (`sector_id`) REFERENCES `beach_sectors` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Budgets Table
CREATE TABLE `budgets` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sector_id` BIGINT NOT NULL,
    `financial_year` VARCHAR(20) NOT NULL,
    `planned_amount` DOUBLE NOT NULL DEFAULT 0.0,
    `actual_amount` DOUBLE NOT NULL DEFAULT 0.0,
    `variance` DOUBLE NOT NULL DEFAULT 0.0,
    CONSTRAINT `fk_budget_sector` FOREIGN KEY (`sector_id`) REFERENCES `beach_sectors` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Purchase Orders Table
CREATE TABLE `purchase_orders` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `po_number` VARCHAR(50) NOT NULL UNIQUE,
    `vendor_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `unit_price` DOUBLE NOT NULL,
    `total_amount` DOUBLE NOT NULL,
    `order_date` DATE NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    CONSTRAINT `fk_po_vendor` FOREIGN KEY (`vendor_id`) REFERENCES `contacts` (`id`),
    CONSTRAINT `fk_po_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Vendor Bills Table
CREATE TABLE `vendor_bills` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `bill_number` VARCHAR(50) NOT NULL UNIQUE,
    `purchase_order_id` BIGINT,
    `vendor_id` BIGINT NOT NULL,
    `bill_date` DATE NOT NULL,
    `amount` DOUBLE NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'UNPAID',
    CONSTRAINT `fk_vb_po` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_orders` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_vb_vendor` FOREIGN KEY (`vendor_id`) REFERENCES `contacts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. Sales Orders Table
CREATE TABLE `sales_orders` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_number` VARCHAR(50) NOT NULL UNIQUE,
    `customer_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `unit_price` DOUBLE NOT NULL,
    `total_amount` DOUBLE NOT NULL,
    `order_date` DATE NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    CONSTRAINT `fk_so_customer` FOREIGN KEY (`customer_id`) REFERENCES `contacts` (`id`),
    CONSTRAINT `fk_so_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. Customer Invoices Table
CREATE TABLE `customer_invoices` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `invoice_number` VARCHAR(50) NOT NULL UNIQUE,
    `sales_order_id` BIGINT,
    `customer_id` BIGINT NOT NULL,
    `invoice_date` DATE NOT NULL,
    `amount` DOUBLE NOT NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'UNPAID',
    CONSTRAINT `fk_ci_so` FOREIGN KEY (`sales_order_id`) REFERENCES `sales_orders` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_ci_customer` FOREIGN KEY (`customer_id`) REFERENCES `contacts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. Financial Transactions Table (General Ledger)
CREATE TABLE `financial_transactions` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `transaction_date` DATE NOT NULL,
    `transaction_type` VARCHAR(20) NOT NULL,
    `reference_number` VARCHAR(100),
    `description` VARCHAR(255),
    `debit_account_id` BIGINT NOT NULL,
    `credit_account_id` BIGINT NOT NULL,
    `amount` DOUBLE NOT NULL,
    CONSTRAINT `fk_tx_debit` FOREIGN KEY (`debit_account_id`) REFERENCES `accounts` (`id`),
    CONSTRAINT `fk_tx_credit` FOREIGN KEY (`credit_account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- SAMPLE DEMO SEED DATA
-- ============================================================================

-- 1. Beach Sectors (Zone 1: Safe GREEN, Zone 2: RED ALERT)
INSERT INTO `beach_sectors` (`id`, `sector_name`, `location`, `description`, `safety_threshold`, `current_status`, `active`) VALUES
(1, 'Central Public Beach Zone 1', 'Marina Promenade Bay', 'Primary municipal beach sector with family recreation area', 100.0, 'GREEN', TRUE),
(2, 'North Public Beach Zone 2', 'North Cliff Estuary', 'Secondary municipal surf and open water zone', 100.0, 'RED', TRUE);

-- 2. Contacts
INSERT INTO `contacts` (`id`, `name`, `contact_type`, `email`, `phone`, `address`, `active`) VALUES
(1, 'Coastal Water Testing Lab', 'TESTING_LAB_VENDOR', 'contact@coastallabs.org', '+91-98765-43210', '42 Ocean Way, Coastal District', TRUE),
(2, 'Ocean Metrology & Environmental Labs', 'TESTING_LAB_VENDOR', 'reports@oceanmetrology.com', '+91-98765-11223', '88 Harbor Science Park', TRUE),
(3, 'Ocean Rescue Equipment Pvt Ltd', 'EQUIPMENT_VENDOR', 'sales@oceanrescue.com', '+91-98765-88990', '12 Marina Marine Industrial Estate', TRUE),
(4, 'AquaMarine Safety Gear Ltd', 'EQUIPMENT_VENDOR', 'info@aquamarinesafety.in', '+91-98765-77665', '90 Coastguard Road', TRUE),
(5, 'Beach Sports Association', 'CUSTOMER', 'events@beachsports.org', '+91-98765-33445', '101 Promenade Avenue', TRUE),
(6, 'Coastal Tourism & Marathon Committee', 'CUSTOMER', 'info@coastalmarathon.com', '+91-98765-99887', '15 Bayfront Plaza', TRUE);

-- 3. Products & Services
INSERT INTO `products` (`id`, `name`, `type`, `description`, `unit_price`, `active`) VALUES
(1, 'Commercial Beach Event Permit', 'SERVICE', 'Official municipal clearance permit for hosting commercial coastal events', 25000.00, TRUE),
(2, 'Water Quality Testing Service', 'SERVICE', 'Weekly certified laboratory microbiological and physicochemical testing', 5000.00, TRUE),
(3, 'Rescue Jet-Ski Equipment', 'GOODS', 'High-performance marine search and rescue jet-ski patrol craft', 150000.00, TRUE);

-- 4. Chart of Accounts
INSERT INTO `accounts` (`id`, `account_code`, `account_name`, `account_type`, `opening_balance`) VALUES
(1, '1001', 'Rescue Watercraft Assets', 'ASSET', 150000.00),
(2, '1002', 'Testing Gear', 'ASSET', 25000.00),
(3, '1003', 'Cash', 'ASSET', 50000.00),
(4, '1004', 'Bank', 'ASSET', 500000.00),
(5, '2001', 'Testing Lab Payables', 'LIABILITY', 0.00),
(6, '2002', 'Equipment Creditors', 'LIABILITY', 0.00),
(7, '3001', 'Beach Event Permit Revenue', 'INCOME', 50000.00),
(8, '3002', 'Coastal Commercial Fees', 'INCOME', 20000.00),
(9, '4001', 'Lab Testing Expenses', 'EXPENSE', 15000.00),
(10, '4002', 'Rescue Craft Fuel & Maintenance', 'EXPENSE', 10000.00);

-- 5. Water Quality Samples (Zone 1: Safe 80 CFU, Zone 2: Unsafe 180 CFU -> RED ALERT)
INSERT INTO `water_quality_samples` (`id`, `sector_id`, `sample_date`, `bacterial_count`, `ph`, `temperature`, `turbidity`, `status`, `remarks`) VALUES
(1, 1, CURDATE() - INTERVAL 1 DAY, 80.0, 7.8, 24.5, 2.1, 'GREEN', 'Routine weekly testing - Water Quality Safe'),
(2, 2, CURDATE(), 180.0, 8.1, 26.0, 4.5, 'RED', 'RED ALERT: Unsafe Water Quality Detected (Exceeds 1.5x threshold)');

-- 6. Budgets
INSERT INTO `budgets` (`id`, `sector_id`, `financial_year`, `planned_amount`, `actual_amount`, `variance`) VALUES
(1, 1, '2026-2027', 100000.00, 75000.00, 25000.00),
(2, 2, '2026-2027', 120000.00, 95000.00, 25000.00);

-- 7. Purchase Orders
INSERT INTO `purchase_orders` (`id`, `po_number`, `vendor_id`, `product_id`, `quantity`, `unit_price`, `total_amount`, `order_date`, `status`) VALUES
(1, 'PO-2026-0001', 1, 2, 3, 5000.00, 15000.00, CURDATE() - INTERVAL 5 DAY, 'BILLED'),
(2, 'PO-2026-0002', 3, 3, 1, 150000.00, 150000.00, CURDATE() - INTERVAL 2 DAY, 'CREATED');

-- 8. Vendor Bills
INSERT INTO `vendor_bills` (`id`, `bill_number`, `purchase_order_id`, `vendor_id`, `bill_date`, `amount`, `status`) VALUES
(1, 'BILL-2026-0001', 1, 1, CURDATE() - INTERVAL 3 DAY, 15000.00, 'PAID');

-- 9. Sales Orders
INSERT INTO `sales_orders` (`id`, `order_number`, `customer_id`, `product_id`, `quantity`, `unit_price`, `total_amount`, `order_date`, `status`) VALUES
(1, 'SO-2026-0001', 5, 1, 2, 25000.00, 50000.00, CURDATE() - INTERVAL 4 DAY, 'INVOICED');

-- 10. Customer Invoices
INSERT INTO `customer_invoices` (`id`, `invoice_number`, `sales_order_id`, `customer_id`, `invoice_date`, `amount`, `status`) VALUES
(1, 'INV-2026-0001', 1, 5, CURDATE() - INTERVAL 2 DAY, 50000.00, 'PAID');

-- 11. Initial Financial Ledger Transactions
INSERT INTO `financial_transactions` (`id`, `transaction_date`, `transaction_type`, `reference_number`, `description`, `debit_account_id`, `credit_account_id`, `amount`) VALUES
(1, CURDATE() - INTERVAL 3 DAY, 'BANK', 'PAY-BILL-2026-0001', 'Payment to Coastal Water Testing Lab for water testing', 9, 4, 15000.00),
(2, CURDATE() - INTERVAL 2 DAY, 'BANK', 'RCPT-INV-2026-0001', 'Permit revenue receipt from Beach Sports Association', 4, 7, 50000.00);

-- Enable Foreign Key checks
SET FOREIGN_KEY_CHECKS = 1;
