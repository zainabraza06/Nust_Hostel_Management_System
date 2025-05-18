package com.example.demo.repository;

import com.example.demo.Models.CleaningRequest;
import com.example.demo.Models.CleaningStaff;
import com.example.demo.Models.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CleaningRequestRepository extends JpaRepository<CleaningRequest, Long> {

    // Find requests by status
    List<CleaningRequest> findByStatus(Request.RequestStatus status);
    Optional<CleaningRequest> findById(Long Id);
    // Find requests by assigned cleaning staff
    List<CleaningRequest> findByCleaningStaff(CleaningStaff cleaningStaff);

    // Count requests by status
    @Query("SELECT COUNT(r) FROM CleaningRequest r WHERE r.status = :status")
    long countByStatus(@Param("status") Request.RequestStatus status);

    // Find requests by cleaning staff ID and date range
    List<CleaningRequest> findByCleaningStaffIdAndRequestTimeBetween(
        Long staffId,
        LocalDateTime start,
        LocalDateTime end
    );

    // Count requests by cleaning staff ID, status, and date range
    @Query("SELECT COUNT(r) FROM CleaningRequest r WHERE " +
           "r.cleaningStaff.id = :staffId AND " +
           "r.status = :status AND " +
           "r.requestTime BETWEEN :start AND :end")
    long countByCleaningStaffIdAndStatusAndRequestTimeBetween(
        @Param("staffId") Long staffId,
        @Param("status") Request.RequestStatus status,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    // Find a request by ID and cleaning staff ID
    @Query("SELECT r FROM CleaningRequest r WHERE " +
           "r.id = :requestId AND " +
           "r.cleaningStaff.id = :staffId")
    Optional<CleaningRequest> findByIdAndCleaningStaffId(
        @Param("requestId") Long requestId,
        @Param("staffId") Long staffId
    );



}
