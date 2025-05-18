package com.example.demo.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "request_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String requestId;
    private LocalDate requestDate;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    private RequestType type;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostelite_id", nullable = false)
    private Hostelite hostelite;

    // Enum definitions
    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, COMPLETED, CANCELLED
    }
    
    public enum RequestType {
        MESS_OFF, LEAVE, CLEANING
    }

    // Automatically set request type based on concrete class
    @PrePersist
    protected void setType() {
        if (this instanceof MessOffRequest) {
            this.type = RequestType.MESS_OFF;
        } else if (this instanceof LeaveRequest) {
            this.type = RequestType.LEAVE;
        } else if (this instanceof CleaningRequest) {
            this.type = RequestType.CLEANING;
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
    
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public RequestType getType() { return type; }
    public void setType(RequestType type) { this.type = type; }
    
    public Hostelite getHostelite() { return hostelite; }
    public void setHostelite(Hostelite hostelite) { this.hostelite = hostelite; }
}