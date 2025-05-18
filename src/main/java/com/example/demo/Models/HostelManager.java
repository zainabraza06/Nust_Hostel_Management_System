package com.example.demo.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hostel_managers")
@PrimaryKeyJoinColumn(name = "user_id")
public class HostelManager extends User {
    
    @Column(nullable = false)
    private LocalDate joiningDate;
    
    @Column(nullable = false)
    private String salary;
    
    @OneToOne
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;
    
    // Constructors
    public HostelManager() {
        super(Role.HOSTEL_MANAGER);
    }

    // Getters and Setters
    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    @Override
    public String toString() {
        return "HostelManager{" +
                "joiningDate=" + joiningDate +
                ", hostel=" + (hostel != null ? hostel.getName() : "null") +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}