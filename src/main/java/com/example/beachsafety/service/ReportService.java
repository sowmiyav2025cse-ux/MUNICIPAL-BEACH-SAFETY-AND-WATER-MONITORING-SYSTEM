package com.example.beachsafety.service;

import com.example.beachsafety.dto.*;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final WaterQualitySampleRepository sampleRepository;
    private final BeachSectorRepository sectorRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final VendorBillRepository billRepository;
    private final CustomerInvoiceRepository invoiceRepository;
    private final FinancialTransactionRepository transactionRepository;

    public ReportService(WaterQualitySampleRepository sampleRepository,
                         BeachSectorRepository sectorRepository,
                         AccountRepository accountRepository,
                         BudgetRepository budgetRepository,
                         VendorBillRepository billRepository,
                         CustomerInvoiceRepository invoiceRepository,
                         FinancialTransactionRepository transactionRepository) {
        this.sampleRepository = sampleRepository;
        this.sectorRepository = sectorRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.billRepository = billRepository;
        this.invoiceRepository = invoiceRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<WaterQualityReportDTO> getWaterQualityReport() {
        return sampleRepository.findAllByOrderBySampleDateDesc().stream()
                .map(sample -> {
                    String alertMsg;
                    if (sample.getStatus() == WaterQualityStatus.RED) {
                        alertMsg = "RED ALERT: Unsafe Water Quality Detected";
                    } else if (sample.getStatus() == WaterQualityStatus.YELLOW) {
                        alertMsg = "Warning: Water Quality Requires Monitoring";
                    } else {
                        alertMsg = "Water Quality Safe";
                    }

                    return new WaterQualityReportDTO(
                            sample.getId(),
                            sample.getSector().getSectorName(),
                            sample.getSampleDate(),
                            sample.getBacterialCount(),
                            sample.getpH(),
                            sample.getTemperature(),
                            sample.getTurbidity(),
                            sample.getStatus().name(),
                            alertMsg,
                            sample.getRemarks()
                    );
                })
                .collect(Collectors.toList());
    }

    public ProfitLossReportDTO getProfitLossReport() {
        List<Account> incomeAccounts = accountRepository.findByAccountType(AccountType.INCOME);
        List<Account> expenseAccounts = accountRepository.findByAccountType(AccountType.EXPENSE);

        List<FinancialTransaction> allTransactions = transactionRepository.findAll();

        List<ProfitLossReportDTO.LineItem> incomeItems = new ArrayList<>();
        double totalIncome = 0.0;

        for (Account acc : incomeAccounts) {
            // Net balance = openingBalance + credit transactions - debit transactions
            double txCredits = allTransactions.stream()
                    .filter(tx -> tx.getCreditAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double txDebits = allTransactions.stream()
                    .filter(tx -> tx.getDebitAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double currentBalance = acc.getOpeningBalance() + txCredits - txDebits;
            incomeItems.add(new ProfitLossReportDTO.LineItem(acc.getAccountName(), currentBalance));
            totalIncome += currentBalance;
        }

        List<ProfitLossReportDTO.LineItem> expenseItems = new ArrayList<>();
        double totalExpenses = 0.0;

        for (Account acc : expenseAccounts) {
            // Expenses: openingBalance + debit transactions - credit transactions
            double txDebits = allTransactions.stream()
                    .filter(tx -> tx.getDebitAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double txCredits = allTransactions.stream()
                    .filter(tx -> tx.getCreditAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double currentBalance = acc.getOpeningBalance() + txDebits - txCredits;
            expenseItems.add(new ProfitLossReportDTO.LineItem(acc.getAccountName(), currentBalance));
            totalExpenses += currentBalance;
        }

        double netProfitOrLoss = totalIncome - totalExpenses;
        return new ProfitLossReportDTO(incomeItems, totalIncome, expenseItems, totalExpenses, netProfitOrLoss);
    }

    public List<BudgetReportDTO> getBudgetReport() {
        return budgetRepository.findAll().stream()
                .map(b -> new BudgetReportDTO(
                        b.getSector().getId(),
                        b.getSector().getSectorName(),
                        b.getFinancialYear(),
                        b.getPlannedAmount(),
                        b.getActualAmount(),
                        b.getVariance()
                ))
                .collect(Collectors.toList());
    }

    public BalanceSheetReportDTO getBalanceSheetReport() {
        List<Account> assetAccounts = accountRepository.findByAccountType(AccountType.ASSET);
        List<Account> liabilityAccounts = accountRepository.findByAccountType(AccountType.LIABILITY);

        List<FinancialTransaction> allTransactions = transactionRepository.findAll();

        List<BalanceSheetReportDTO.BalanceItem> assetItems = new ArrayList<>();
        double totalAssets = 0.0;

        for (Account acc : assetAccounts) {
            double txDebits = allTransactions.stream()
                    .filter(tx -> tx.getDebitAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double txCredits = allTransactions.stream()
                    .filter(tx -> tx.getCreditAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double currentBalance = acc.getOpeningBalance() + txDebits - txCredits;
            assetItems.add(new BalanceSheetReportDTO.BalanceItem(acc.getAccountName(), currentBalance));
            totalAssets += currentBalance;
        }

        List<BalanceSheetReportDTO.BalanceItem> liabilityItems = new ArrayList<>();
        double totalLiabilities = 0.0;

        for (Account acc : liabilityAccounts) {
            double txCredits = allTransactions.stream()
                    .filter(tx -> tx.getCreditAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double txDebits = allTransactions.stream()
                    .filter(tx -> tx.getDebitAccount().getId().equals(acc.getId()))
                    .mapToDouble(FinancialTransaction::getAmount)
                    .sum();
            double currentBalance = acc.getOpeningBalance() + txCredits - txDebits;
            liabilityItems.add(new BalanceSheetReportDTO.BalanceItem(acc.getAccountName(), currentBalance));
            totalLiabilities += currentBalance;
        }

        double netWorth = totalAssets - totalLiabilities;
        return new BalanceSheetReportDTO(assetItems, totalAssets, liabilityItems, totalLiabilities, netWorth);
    }

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        List<BeachSector> sectors = sectorRepository.findAll();
        stats.setTotalSectors(sectors.size());

        long safeCount = sectors.stream().filter(s -> s.getCurrentStatus() == WaterQualityStatus.GREEN).count();
        long warnCount = sectors.stream().filter(s -> s.getCurrentStatus() == WaterQualityStatus.YELLOW).count();
        long unsafeCount = sectors.stream().filter(s -> s.getCurrentStatus() == WaterQualityStatus.RED).count();

        stats.setSafeBeaches(safeCount);
        stats.setWarningBeaches(warnCount);
        stats.setUnsafeBeaches(unsafeCount);

        stats.setTotalWaterSamples(sampleRepository.count());
        stats.setPendingVendorBills(billRepository.countByStatus(BillStatus.UNPAID));

        Double paidPermits = invoiceRepository.sumAmountByStatus(BillStatus.PAID);
        stats.setTotalPermitRevenue(paidPermits != null ? paidPermits : 0.0);

        Double varianceSum = budgetRepository.sumTotalVariance();
        stats.setCurrentBudgetVariance(varianceSum != null ? varianceSum : 0.0);

        boolean hasRed = unsafeCount > 0;
        stats.setHasRedAlert(hasRed);
        if (hasRed) {
            stats.setAlertMessage("RED ALERT – IMMEDIATE BEACH SAFETY ATTENTION REQUIRED");
        } else if (warnCount > 0) {
            stats.setAlertMessage("WARNING – Water Quality In Certain Zones Requires Monitoring");
        } else {
            stats.setAlertMessage("ALL CLEAR – All Beach Zones Are Currently Safe For Swimming");
        }

        List<DashboardStatsDTO.SectorStatusSummary> summaries = new ArrayList<>();
        for (BeachSector s : sectors) {
            List<WaterQualitySample> latestSamples = sampleRepository.findBySectorOrderBySampleDateDesc(s);
            Double lastBact = latestSamples.isEmpty() ? 0.0 : latestSamples.get(0).getBacterialCount();
            summaries.add(new DashboardStatsDTO.SectorStatusSummary(
                    s.getId(),
                    s.getSectorName(),
                    s.getLocation(),
                    s.getSafetyThreshold(),
                    s.getCurrentStatus().name(),
                    lastBact
            ));
        }
        stats.setSectorStatuses(summaries);

        return stats;
    }
}
