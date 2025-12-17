package com.example.conflicttracker.repository;

import com.example.conflicttracker.model.Faction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactionRepository extends JpaRepository<Faction, Long> {

    // Troba totes les faccions d'un conflicte específic
    List<Faction> findByConflictId(Long conflictId);

    // Troba faccions pel nom (ignorant majúscules/minúscules)
    List<Faction> findByNameContainingIgnoreCase(String name);

    // Consulta personalizada: Troba faccions que tenen suport d'un país específic
    @Query("SELECT f FROM Faction f JOIN f.supportingCountries country WHERE country.code = :countryCode")
    List<Faction> findFactionsSupportedByCountry(@Param("countryCode") String countryCode);

    // Consulta personalizada: Compta quantes faccions té cada conflicte
    @Query("SELECT f.conflict.id, COUNT(f) FROM Faction f GROUP BY f.conflict.id")
    List<Object[]> countFactionsPerConflict();
}
