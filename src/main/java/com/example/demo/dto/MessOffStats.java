package com.example.demo.dto;

import java.util.Objects;

/**
 * Data Transfer Object for Mess-Off statistics
 */
public class MessOffStats {
    private final long usedDays;
    private final long totalDays;
    private final long remainingDays;
    private final int percentage;

    /**
     * Constructor for MessOffStats
     * @param usedDays Number of mess-off days already used
     * @param totalDays Total allowed mess-off days
     * @param remainingDays Remaining mess-off days available
     * @param percentage Usage percentage (0-100)
     */
    public MessOffStats(long usedDays, long totalDays, long remainingDays, int percentage) {
        this.usedDays = usedDays;
        this.totalDays = totalDays;
        this.remainingDays = remainingDays;
        this.percentage = percentage;
    }

    // Getters
    public long getUsedDays() {
        return usedDays;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public long getRemainingDays() {
        return remainingDays;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessOffStats that = (MessOffStats) o;
        return usedDays == that.usedDays && 
               totalDays == that.totalDays && 
               remainingDays == that.remainingDays && 
               percentage == that.percentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedDays, totalDays, remainingDays, percentage);
    }

    @Override
    public String toString() {
        return "MessOffStats{" +
                "usedDays=" + usedDays +
                ", totalDays=" + totalDays +
                ", remainingDays=" + remainingDays +
                ", percentage=" + percentage +
                '}';
    }
}