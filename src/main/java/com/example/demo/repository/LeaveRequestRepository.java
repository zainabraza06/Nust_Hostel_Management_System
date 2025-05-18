package com.example.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Models.*;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(Request.RequestStatus status);
  

     // Count pending leave requests
    @Query("SELECT COUNT(l) FROM LeaveRequest l WHERE l.status = :status")
    long countByStatus(@Param("status") Request.RequestStatus status);
}