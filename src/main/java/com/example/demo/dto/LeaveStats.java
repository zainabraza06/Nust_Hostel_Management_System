package com.example.demo.dto;


import java.util.Objects;

/**
 * Data Transfer Object for Leave request statistics
 */
public class LeaveStats {
    private final long pendingCount;
    private final int percentage;

    /**
     * Constructor for LeaveStats
     * @param pendingCount Number of pending leave requests
     * @param percentage Percentage of pending requests (0-100)
     */
    public LeaveStats(long pendingCount, int percentage) {
        this.pendingCount = pendingCount;
        this.percentage = percentage;
    }

    // Getters
    public long getPendingCount() {
        return pendingCount;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveStats that = (LeaveStats) o;
        return pendingCount == that.pendingCount && 
               percentage == that.percentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pendingCount, percentage);
    }

    @Override
    public String toString() {
        return "LeaveStats{" +
                "pendingCount=" + pendingCount +
                ", percentage=" + percentage +
                '}';
    }
}