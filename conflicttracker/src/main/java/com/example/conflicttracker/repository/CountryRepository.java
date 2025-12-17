package com.example.conflicttracker.repository;

import com.example.conflicttracker.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Aquest repository s'encarrega de totes les operacions de base de dades amb la taula COUNTRIES
// JpaRepository ens dóna automàticament mètodes com save, findAll, findById, delete, etc.
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    // Cerca un país pel seu codi (ex: "UKR", "RUS")
    // Spring Data JPA crea automàticament la consulta SQL basant-se en el nom del mètode
    Optional<Country> findByCode(String code);

    // Cerca un país pel seu nom
    Optional<Country> findByName(String name);

    // Cerca països pel nom (ignorant majúscules/minúscules)
    List<Country> findByNameContainingIgnoreCase(String name);

    // Consulta personalizada: Troba tots els països que participen en conflictes actius
    @Query("SELECT DISTINCT c FROM Country c JOIN c.conflicts conf WHERE conf.status = 'ACTIVE'")
    List<Country> findCountriesWithActiveConflicts();

    // Consulta personalizada: Comprova si un país està involucrat en algun conflicte
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Country c JOIN c.conflicts conf WHERE c.code = :countryCode")
    boolean isCountryInvolvedInAnyConflict(@Param("countryCode") String countryCode);
}
