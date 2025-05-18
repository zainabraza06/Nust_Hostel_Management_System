package com.example.demo.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hostelites")
public class Hostelite extends User {
    
    @Column(nullable = false, unique = true)
    private String universityId;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private int academicYear;
    
    @Column(nullable = false)
    private String roomNumber;
    
    @Column(nullable = false)
    private LocalDate admissionDate;
    
    @Column(nullable = false)
    private LocalDate validUpto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id", nullable = false)
    private Hostel hostel;
    
    @OneToMany(mappedBy = "hostelite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    // Constructors
    public Hostelite() {
        super(Role.HOSTELITE);
    }

    // Helper method to properly manage bidirectional relationship
    public void addRequest(Request request) {
        requests.add(request);
        request.setHostelite(this); // Critical line that maintains the relationship
    }

    // Getters and Setters
    public String getUniversityId() { return universityId; }
    public void setUniversityId(String universityId) { this.universityId = universityId; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    
    public LocalDate getValidUpto() { return validUpto; }
    public void setValidUpto(LocalDate validUpto) { this.validUpto = validUpto; }
    
    public Hostel getHostel() { return hostel; }
    public void setHostel(Hostel hostel) { this.hostel = hostel; }
    
    public List<Request> getRequests() { return requests; }
    public void setRequests(List<Request> requests) { this.requests = requests; }
}