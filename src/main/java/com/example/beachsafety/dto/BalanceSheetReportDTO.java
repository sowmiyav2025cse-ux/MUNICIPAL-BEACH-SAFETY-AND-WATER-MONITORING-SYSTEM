package com.example.beachsafety.dto;

import java.util.List;

public class BalanceSheetReportDTO {

    public static class BalanceItem {
        private String accountName;
        private Double amount;

        public BalanceItem() {
        }

        public BalanceItem(String accountName, Double amount) {
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

    private List<BalanceItem> assetItems;
    private Double totalAssets;
    private List<BalanceItem> liabilityItems;
    private Double totalLiabilities;
    private Double netWorth;

    public BalanceSheetReportDTO() {
    }

    public BalanceSheetReportDTO(List<BalanceItem> assetItems, Double totalAssets, List<BalanceItem> liabilityItems, Double totalLiabilities, Double netWorth) {
        this.assetItems = assetItems;
        this.totalAssets = totalAssets;
        this.liabilityItems = liabilityItems;
        this.totalLiabilities = totalLiabilities;
        this.netWorth = netWorth;
    }

    public List<BalanceItem> getAssetItems() {
        return assetItems;
    }

    public void setAssetItems(List<BalanceItem> assetItems) {
        this.assetItems = assetItems;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public List<BalanceItem> getLiabilityItems() {
        return liabilityItems;
    }

    public void setLiabilityItems(List<BalanceItem> liabilityItems) {
        this.liabilityItems = liabilityItems;
    }

    public Double getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Double netWorth) {
        this.netWorth = netWorth;
    }
}
