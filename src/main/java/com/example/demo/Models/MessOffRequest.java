package com.example.demo.Models;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "mess_off_requests")
public class MessOffRequest extends Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private HostelManager manager;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 500)
    private String reason;


    private String managerRemarks;

    // Constructors
    public MessOffRequest() {
          setType(RequestType.CLEANING);
    }

    public MessOffRequest( LocalDate startDate, LocalDate endDate, String reason) {
      
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public HostelManager getManager() { return manager; }
    public void setManager(HostelManager manager) { this.manager = manager; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public void setManagerRemarks(String managerRemarks){ this.managerRemarks=managerRemarks;}
    public String getManagerRemarks(){ return this.managerRemarks;}
  


    // Business methods
    public int getDurationInDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public boolean isSameMonth() {
        return startDate.getMonth() == endDate.getMonth() && 
               startDate.getYear() == endDate.getYear();
    }

    @Override
    public String toString() {
        return "MessOffRequest{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + getDurationInDays() + " days" +
                 
                '}';
    }
}