package com.example.conflicttracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

// Aquesta classe representa un bàndol o facció d'un conflicte
@Entity
@Table(name = "factions") // Taula "factions" a la base de dades
public class Faction {

    // Identificador únic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom de la facció - obligatori
    @NotBlank(message = "El nom de la facció és obligatori")
    @Column(nullable = false)
    private String name;

    // Relació MOLTS-A-UN amb Conflict
    // Cada facció pertany a un únic conflicte
    @ManyToOne(fetch = FetchType.LAZY) // LAZY = carrega les dades només quan es demanen
    @JoinColumn(name = "conflict_id", nullable = false) // Clau forana
    private Conflict conflict;

    // Relació MOLTS-A-MOLTS amb Country
    // Països que donen suport a aquesta facció
    @ManyToMany
    @JoinTable(
            name = "faction_supporters", // Taula intermitja
            joinColumns = @JoinColumn(name = "faction_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> supportingCountries = new HashSet<>();

    // Constructor buit - necessari per JPA
    public Faction() {}

    // Constructor amb paràmetres
    public Faction(String name, Conflict conflict) {
        this.name = name;
        this.conflict = conflict;
    }

    // Mètodes per afegir i eliminar països supporters
    public void addSupportingCountry(Country country) {
        this.supportingCountries.add(country);
        country.getSupportedFactions().add(this);
    }

    public void removeSupportingCountry(Country country) {
        this.supportingCountries.remove(country);
        country.getSupportedFactions().remove(this);
    }

    // GETTERS I SETTERS

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

    public Conflict getConflict() {
        return conflict;
    }

    public void setConflict(Conflict conflict) {
        this.conflict = conflict;
    }

    public Set<Country> getSupportingCountries() {
        return supportingCountries;
    }

    public void setSupportingCountries(Set<Country> supportingCountries) {
        this.supportingCountries = supportingCountries;
    }
}
