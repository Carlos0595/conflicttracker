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

    // Troba conflictes per estat (ACTIVE, FROZEN, ENDED)
    List<Conflict> findByStatus(ConflictStatus status);

    // Troba conflictes que van començar després d'una data específica
    List<Conflict> findByStartDateAfter(LocalDate date);

    // Troba conflictes que van començar abans d'una data específica
    List<Conflict> findByStartDateBefore(LocalDate date);

    // Troba conflictes pel nom (ignorant majúscules/minúscules)
    List<Conflict> findByNameContainingIgnoreCase(String name);

    // Consulta personalizada: Troba conflictes actius que involucren un país específic
    @Query("SELECT DISTINCT c FROM Conflict c JOIN c.countries country WHERE country.code = :countryCode")
    List<Conflict> findConflictsByCountryCode(@Param("countryCode") String countryCode);

    // Consulta personalizada: Compta quants conflictes hi ha per cada estat
    @Query("SELECT c.status, COUNT(c) FROM Conflict c GROUP BY c.status")
    List<Object[]> countConflictsByStatus();

    // Consulta personalizada: Troba conflictes amb més de X països involucrats
    @Query("SELECT c FROM Conflict c WHERE SIZE(c.countries) > :minCountries")
    List<Conflict> findConflictsWithMoreThanXCountries(@Param("minCountries") int minCountries);
}
