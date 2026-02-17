package com.example.conflicttracker.repository;

import com.example.conflicttracker.model.Conflict;
import com.example.conflicttracker.model.ConflictStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConflictRepository extends JpaRepository<Conflict, Long> {

    List<Conflict> findByStatus(ConflictStatus status);

    @Query("SELECT c FROM Conflict c JOIN c.countries co WHERE co.code = :countryCode")
    List<Conflict> findConflictsByCountryCode(@Param("countryCode") String countryCode);

    long countByStatus(ConflictStatus status);

    List<Conflict> findByNameContainingIgnoreCase(String name);

    List<Conflict> findByStartDateBetween(LocalDate start, LocalDate end);

    List<Conflict> findByLocationContainingIgnoreCase(String location);

    List<Conflict> findByStartDateAfter(LocalDate date);

    List<Conflict> findByStartDateBefore(LocalDate date);

    List<Conflict> findByStatusAndLocationContainingIgnoreCase(ConflictStatus status, String location);
}