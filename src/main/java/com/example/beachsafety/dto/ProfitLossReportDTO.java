package com.example.beachsafety.dto;

import java.util.List;

public class ProfitLossReportDTO {

    public static class LineItem {
        private String accountName;
        private Double amount;

        public LineItem() {
        }

        public LineItem(String accountName, Double amount) {
            this.accountName = accountName;
            this.amount = amount;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    private List<LineItem> incomeItems;
    private Double totalIncome;
    private List<LineItem> expenseItems;
    private Double totalExpenses;
    private Double netProfitOrLoss;

    public ProfitLossReportDTO() {
    }

    public ProfitLossReportDTO(List<LineItem> incomeItems, Double totalIncome, List<LineItem> expenseItems, Double totalExpenses, Double netProfitOrLoss) {
        this.incomeItems = incomeItems;
        this.totalIncome = totalIncome;
        this.expenseItems = expenseItems;
        this.totalExpenses = totalExpenses;
        this.netProfitOrLoss = netProfitOrLoss;
    }

    public List<LineItem> getIncomeItems() {
        return incomeItems;
    }

    public void setIncomeItems(List<LineItem> incomeItems) {
        this.incomeItems = incomeItems;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<LineItem> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(List<LineItem> expenseItems) {
        this.expenseItems = expenseItems;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getNetProfitOrLoss() {
        return netProfitOrLoss;
    }

    public void setNetProfitOrLoss(Double netProfitOrLoss) {
        this.netProfitOrLoss = netProfitOrLoss;
    }
}
