-- Municipal Beach Safety Pro - Sample SQL Seed Reference
-- To execute manually in MySQL Workbench: Run database.sql in project root

-- 1. Beach Sectors
INSERT IGNORE INTO `beach_sectors` (`id`, `sector_name`, `location`, `description`, `safety_threshold`, `current_status`, `active`) VALUES
(1, 'Central Public Beach Zone 1', 'Marina Promenade Bay', 'Primary municipal beach sector with family recreation area', 100.0, 'GREEN', TRUE),
(2, 'North Public Beach Zone 2', 'North Cliff Estuary', 'Secondary municipal surf and open water zone', 100.0, 'RED', TRUE);

-- 2. Contacts
INSERT IGNORE INTO `contacts` (`id`, `name`, `contact_type`, `email`, `phone`, `address`, `active`) VALUES
(1, 'Coastal Water Testing Lab', 'TESTING_LAB_VENDOR', 'contact@coastallabs.org', '+91-98765-43210', '42 Ocean Way, Coastal District', TRUE),
(2, 'Ocean Metrology & Environmental Labs', 'TESTING_LAB_VENDOR', 'reports@oceanmetrology.com', '+91-98765-11223', '88 Harbor Science Park', TRUE),
(3, 'Ocean Rescue Equipment Pvt Ltd', 'EQUIPMENT_VENDOR', 'sales@oceanrescue.com', '+91-98765-88990', '12 Marina Marine Industrial Estate', TRUE),
(4, 'AquaMarine Safety Gear Ltd', 'EQUIPMENT_VENDOR', 'info@aquamarinesafety.in', '+91-98765-77665', '90 Coastguard Road', TRUE),
(5, 'Beach Sports Association', 'CUSTOMER', 'events@beachsports.org', '+91-98765-33445', '101 Promenade Avenue', TRUE),
(6, 'Coastal Tourism & Marathon Committee', 'CUSTOMER', 'info@coastalmarathon.com', '+91-98765-99887', '15 Bayfront Plaza', TRUE);

-- 3. Products & Services
INSERT IGNORE INTO `products` (`id`, `name`, `type`, `description`, `unit_price`, `active`) VALUES
(1, 'Commercial Beach Event Permit', 'SERVICE', 'Official municipal clearance permit for hosting commercial coastal events', 25000.00, TRUE),
(2, 'Water Quality Testing Service', 'SERVICE', 'Weekly certified laboratory microbiological and physicochemical testing', 5000.00, TRUE),
(3, 'Rescue Jet-Ski Equipment', 'GOODS', 'High-performance marine search and rescue jet-ski patrol craft', 150000.00, TRUE);

-- 4. Chart of Accounts
INSERT IGNORE INTO `accounts` (`id`, `account_code`, `account_name`, `account_type`, `opening_balance`) VALUES
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

-- 5. Water Quality Samples
INSERT IGNORE INTO `water_quality_samples` (`id`, `sector_id`, `sample_date`, `bacterial_count`, `ph`, `temperature`, `turbidity`, `status`, `remarks`) VALUES
(1, 1, CURDATE() - INTERVAL 1 DAY, 80.0, 7.8, 24.5, 2.1, 'GREEN', 'Routine weekly testing - Water Quality Safe'),
(2, 2, CURDATE(), 180.0, 8.1, 26.0, 4.5, 'RED', 'RED ALERT: Unsafe Water Quality Detected (Exceeds 1.5x threshold)');

-- 6. Budgets
INSERT IGNORE INTO `budgets` (`id`, `sector_id`, `financial_year`, `planned_amount`, `actual_amount`, `variance`) VALUES
(1, 1, '2026-2027', 100000.00, 75000.00, 25000.00),
(2, 2, '2026-2027', 120000.00, 95000.00, 25000.00);
