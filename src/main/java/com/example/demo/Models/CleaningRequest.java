package com.example.demo.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cleaning_requests")
public class CleaningRequest extends Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private HostelManager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cleaning_staff_id")
    private CleaningStaff cleaningStaff;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private LocalDateTime requestTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CleaningType cleaningType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(length = 500)
    private String remarks;

    public enum CleaningType {
        REGULAR, DEEP, URGENT
    }



    // Constructors
    public CleaningRequest() {
        setType(RequestType.CLEANING);
    }

    public CleaningRequest( String roomNumber, CleaningType type) {
     
        this.roomNumber = roomNumber;
        this.cleaningType = type;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public HostelManager getManager() { return manager; }
    public void setManager(HostelManager manager) { this.manager = manager; }
    public CleaningStaff getCleaningStaff() { return cleaningStaff; }
    public void setCleaningStaff(CleaningStaff cleaningStaff) { this.cleaningStaff = cleaningStaff; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
    public CleaningType getCleaningType() { return cleaningType; }
    public void setType(CleaningType type) { this.cleaningType = type; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    @Override
    public String toString() {
        return "CleaningRequest{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", type=" + cleaningType +
                ", status=" + status +
                '}';
    }
}