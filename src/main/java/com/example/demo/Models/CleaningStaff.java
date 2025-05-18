package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cleaning_staff")
@PrimaryKeyJoinColumn(name = "user_id")
public class CleaningStaff extends User {


   public CleaningStaff(){

   }

    public enum Shift {
        MORNING("08:00-14:00"),
        AFTERNOON("14:00-20:00"),
        NIGHT("20:00-02:00");

        private final String timeRange;

        Shift(String timeRange) {
            this.timeRange = timeRange;
        }

        public String getTimeRange() {
            return timeRange;
        }
    }

    public enum StaffType {
        CLEANER,
        SWEEPER,
        WASTE_MANAGER
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffType staffType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(nullable = false)
    private LocalDate joiningDate;

    @Column
    private String salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id")
    private Hostel hostel;

    @OneToMany(mappedBy = "cleaningStaff", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CleaningRequest> assignedRequests = new HashSet<>();

    public CleaningStaff(String email, String password, String firstName, String lastName,
                       String phoneNumber, StaffType staffType, Shift shift, LocalDate joiningDate) {
        super(email, password, firstName, lastName, phoneNumber, Role.CLEANING_STAFF, true); // true = active
        this.staffType = staffType;
        this.shift = shift;
        this.joiningDate = joiningDate;
    }

    @Override
    public String toString() {
        return "CleaningStaff{" +
                "id=" + getId() +
                ", staffType=" + staffType +
                ", shift=" + shift +
                ", name=" + getFirstName() + " " + getLastName() +
                ", joiningDate=" + joiningDate +
                '}';
    }


    public Hostel getHostel(){return hostel;}
    public void setHostel(Hostel hostel){
        this.hostel=hostel;
    }
}