package com.example.demo.dto;


import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for Cleaning statistics
 */
public class CleaningStats {
    private final long daysSinceLastCleaning;
    private final LocalDate lastCleaningDate;
    private final int percentage;

    /**
     * Constructor for CleaningStats
     * @param daysSinceLastCleaning Number of days since last cleaning
     * @param lastCleaningDate Date of last cleaning (can be null)
     * @param percentage Cleaning urgency percentage (0-100)
     */
    public CleaningStats(long daysSinceLastCleaning, LocalDate lastCleaningDate, int percentage) {
        this.daysSinceLastCleaning = daysSinceLastCleaning;
        this.lastCleaningDate = lastCleaningDate;
        this.percentage = percentage;
    }

    // Getters
    public long getDaysSinceLastCleaning() {
        return daysSinceLastCleaning;
    }

    public LocalDate getLastCleaningDate() {
        return lastCleaningDate;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleaningStats that = (CleaningStats) o;
        return daysSinceLastCleaning == that.daysSinceLastCleaning && 
               percentage == that.percentage && 
               Objects.equals(lastCleaningDate, that.lastCleaningDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daysSinceLastCleaning, lastCleaningDate, percentage);
    }

    @Override
    public String toString() {
        return "CleaningStats{" +
                "daysSinceLastCleaning=" + daysSinceLastCleaning +
                ", lastCleaningDate=" + lastCleaningDate +
                ", percentage=" + percentage +
                '}';
    }
}