package com.example.demo.Models;

import jakarta.persistence.*;


@Entity
@Table(name = "hostels")
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private int totalRooms;
    
    @Column(nullable = false)
    private int occupiedRooms;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HostelType type;
    
    @OneToOne(mappedBy = "hostel")
    private HostelManager manager;
    

    public enum HostelType {
        BOYS, GIRLS
    }

    // Constructors
    public Hostel() {}

    public Hostel(String name, String address, int totalRooms, HostelType type) {
        this.name = name;
        this.address = address;
        this.totalRooms = totalRooms;
        this.occupiedRooms = 0;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getOccupiedRooms() {
        return occupiedRooms;
    }

    public void setOccupiedRooms(int occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
    }

    public HostelType getType() {
        return type;
    }

    public void setType(HostelType type) {
        this.type = type;
    }

    public HostelManager getManager() {
        return manager;
    }

    public void setManager(HostelManager manager) {
        if (manager == null) {
            if (this.manager != null) {
                this.manager.setHostel(null);
            }
        } else {
            manager.setHostel(this);
        }
        this.manager = manager;
    }

    // Business methods
    public int getAvailableRooms() {
        return totalRooms - occupiedRooms;
    }

    public boolean hasAvailableRooms() {
        return getAvailableRooms() > 0;
    }

    @Override
    public String toString() {
        return "Hostel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", availableRooms=" + getAvailableRooms() +
                '}';
    }
}