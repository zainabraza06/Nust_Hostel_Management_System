package com.example.demo.repository;

import com.example.demo.Models.CleaningRequest;
import com.example.demo.Models.Hostelite;
import com.example.demo.Models.Request;
import com.example.demo.Models.Request.RequestType;
import com.example.demo.Models.MessOffRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByHosteliteOrderByRequestDateDesc(Hostelite hostelite);

    
@Query("SELECT r FROM MessOffRequest r WHERE r.hostelite = :hostelite " +
       "AND FUNCTION('MONTH', r.startDate) = :month " +
       "AND FUNCTION('YEAR', r.startDate) = :year " +
       "AND r.status = :status")
List<MessOffRequest> findApprovedMessOffRequestsForMonth(
    @Param("hostelite") Hostelite hostelite,
    @Param("month") int month,
    @Param("year") int year,
    @Param("status") Request.RequestStatus status
);

   Page<Request> findByHosteliteOrderByCreatedAtDesc(Hostelite hostelite, Pageable pageable);
    Page<Request> findByHosteliteAndTypeOrderByCreatedAtDesc(Hostelite hostelite, Request.RequestType type, Pageable pageable);
    Page<Request> findByHosteliteAndStatusOrderByCreatedAtDesc(Hostelite hostelite, Request.RequestStatus status, Pageable pageable);
    Page<Request> findByHosteliteAndTypeAndStatusOrderByCreatedAtDesc(
        Hostelite hostelite, 
        Request.RequestType type, 
        Request.RequestStatus status, 
        Pageable pageable
    );
    Page<Request> findByHosteliteAndCreatedAtBetweenOrderByCreatedAtDesc(
        Hostelite hostelite,
        LocalDateTime start,
        LocalDateTime end,
        Pageable pageable
    );
    Request findByIdAndHostelite(Long id, Hostelite hostelite);



    List<Request> findTop5ByHosteliteOrderByRequestDateDesc(Hostelite hostelite);
    
   
    
    
@Query("SELECT r FROM CleaningRequest r WHERE r.hostelite = :hostelite ORDER BY r.requestDate DESC LIMIT 1")
CleaningRequest findFirstByHosteliteOrderByRequestDateDesc(@Param("hostelite") Hostelite hostelite);

    
    int countByHosteliteAndStatus(Hostelite hostelite, Request.RequestStatus status);
    int countByHosteliteAndTypeAndStatus(Hostelite hostelite, Request.RequestType type, Request.RequestStatus status);
    int countByHosteliteAndType(Hostelite hostelite, Request.RequestType type);



    @Query("SELECT r FROM Request r WHERE " +
       "(:hostelId IS NULL OR r.hostelite.hostel.id = :hostelId) AND " +
       "(:type IS NULL OR r.type = :type) AND " +
       "(:status IS NULL OR r.status = :status) AND " +
       "(:startDate IS NULL OR r.requestDate >= :startDate) AND " +
       "(:endDate IS NULL OR r.requestDate <= :endDate) AND " +
       "(:hosteliteId IS NULL OR r.hostelite.id = :hosteliteId)")
Page<Request> findByFilters(@Param("hostelId") Long hostelId,
                            @Param("type") RequestType type,
                            @Param("status") Request.RequestStatus status,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate,
                            @Param("hosteliteId") Long hosteliteId,
                            Pageable pageable);

long countByHostelite_Hostel_IdAndStatus(Long hostelId, Request.RequestStatus status);

@Query("SELECT r FROM Request r WHERE r.hostelite.hostel.id = :hostelId ORDER BY r.requestDate DESC")
List<Request> findTopByHosteliteHostelIdOrderByRequestDateDesc(@Param("hostelId") Long hostelId, Pageable pageable);

}

