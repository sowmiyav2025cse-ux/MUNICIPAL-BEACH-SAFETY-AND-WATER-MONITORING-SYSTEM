package com.example.beachsafety.dto;

import java.util.List;

public class DashboardStatsDTO {
    private long totalSectors;
    private long safeBeaches;
    private long warningBeaches;
    private long unsafeBeaches;
    private long totalWaterSamples;
    private long pendingVendorBills;
    private double totalPermitRevenue;
    private double currentBudgetVariance;
    private boolean hasRedAlert;
    private String alertMessage;
    private List<SectorStatusSummary> sectorStatuses;

    public static class SectorStatusSummary {
        private Long id;
        private String sectorName;
        private String location;
        private Double safetyThreshold;
        private String currentStatus;
        private Double lastBacterialCount;

        public SectorStatusSummary() {
        }

        public SectorStatusSummary(Long id, String sectorName, String location, Double safetyThreshold, String currentStatus, Double lastBacterialCount) {
            this.id = id;
            this.sectorName = sectorName;
            this.location = location;
            this.safetyThreshold = safetyThreshold;
            this.currentStatus = currentStatus;
            this.lastBacterialCount = lastBacterialCount;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSectorName() {
            return sectorName;
        }

        public void setSectorName(String sectorName) {
            this.sectorName = sectorName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Double getSafetyThreshold() {
            return safetyThreshold;
        }

        public void setSafetyThreshold(Double safetyThreshold) {
            this.safetyThreshold = safetyThreshold;
        }

        public String getCurrentStatus() {
            return currentStatus;
        }

        public void setCurrentStatus(String currentStatus) {
            this.currentStatus = currentStatus;
        }

        public Double getLastBacterialCount() {
            return lastBacterialCount;
        }

        public void setLastBacterialCount(Double lastBacterialCount) {
            this.lastBacterialCount = lastBacterialCount;
        }
    }

    public DashboardStatsDTO() {
    }

    public long getTotalSectors() {
        return totalSectors;
    }

    public void setTotalSectors(long totalSectors) {
        this.totalSectors = totalSectors;
    }

    public long getSafeBeaches() {
        return safeBeaches;
    }

    public void setSafeBeaches(long safeBeaches) {
        this.safeBeaches = safeBeaches;
    }

    public long getWarningBeaches() {
        return warningBeaches;
    }

    public void setWarningBeaches(long warningBeaches) {
        this.warningBeaches = warningBeaches;
    }

    public long getUnsafeBeaches() {
        return unsafeBeaches;
    }

    public void setUnsafeBeaches(long unsafeBeaches) {
        this.unsafeBeaches = unsafeBeaches;
    }

    public long getTotalWaterSamples() {
        return totalWaterSamples;
    }

    public void setTotalWaterSamples(long totalWaterSamples) {
        this.totalWaterSamples = totalWaterSamples;
    }

    public long getPendingVendorBills() {
        return pendingVendorBills;
    }

    public void setPendingVendorBills(long pendingVendorBills) {
        this.pendingVendorBills = pendingVendorBills;
    }

    public double getTotalPermitRevenue() {
        return totalPermitRevenue;
    }

    public void setTotalPermitRevenue(double totalPermitRevenue) {
        this.totalPermitRevenue = totalPermitRevenue;
    }

    public double getCurrentBudgetVariance() {
        return currentBudgetVariance;
    }

    public void setCurrentBudgetVariance(double currentBudgetVariance) {
        this.currentBudgetVariance = currentBudgetVariance;
    }

    public boolean isHasRedAlert() {
        return hasRedAlert;
    }

    public void setHasRedAlert(boolean hasRedAlert) {
        this.hasRedAlert = hasRedAlert;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public List<SectorStatusSummary> getSectorStatuses() {
        return sectorStatuses;
    }

    public void setSectorStatuses(List<SectorStatusSummary> sectorStatuses) {
        this.sectorStatuses = sectorStatuses;
    }
}
