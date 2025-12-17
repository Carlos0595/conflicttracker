package com.example.conflicttracker.repository;

import com.example.conflicttracker.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Troba tots els esdeveniments d'un conflicte específic
    List<Event> findByConflictId(Long conflictId);

    // Troba esdeveniments per data
    List<Event> findByEventDate(LocalDate eventDate);

    // Troba esdeveniments entre dues dates
    List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate);

    // Troba esdeveniments per ubicació (ignorant majúscules/minúscules)
    List<Event> findByLocationContainingIgnoreCase(String location);

    // Consulta personalizada: Troba els esdeveniments més recents primer
    @Query("SELECT e FROM Event e ORDER BY e.eventDate DESC")
    List<Event> findAllOrderByDateDesc();

    // Consulta personalizada: Troba esdeveniments d'un conflicte específic ordenats per data
    @Query("SELECT e FROM Event e WHERE e.conflict.id = :conflictId ORDER BY e.eventDate DESC")
    List<Event> findEventsByConflictIdOrderByDateDesc(@Param("conflictId") Long conflictId);

    // Consulta personalizada: Compta esdeveniments per conflicte
    @Query("SELECT e.conflict.id, COUNT(e) FROM Event e GROUP BY e.conflict.id")
    List<Object[]> countEventsPerConflict();
}
