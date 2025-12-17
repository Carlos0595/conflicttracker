package com.example.conflicttracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
// Aquesta classe representa un país a la base de dades

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 3)
    @Column(nullable = false, unique = true, length = 3)
    private String code;

    // Relació amb conflictes (on participa)
    @ManyToMany(mappedBy = "countries")
    private Set<Conflict> conflicts = new HashSet<>();

    // Relació amb faccions (que dona suport)
    @ManyToMany(mappedBy = "supportingCountries")
    private Set<Faction> supportedFactions = new HashSet<>();

    public Country() {}

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Set<Conflict> getConflicts() { return conflicts; }
    public void setConflicts(Set<Conflict> conflicts) { this.conflicts = conflicts; }

    public Set<Faction> getSupportedFactions() { return supportedFactions; }
    public void setSupportedFactions(Set<Faction> supportedFactions) {
        this.supportedFactions = supportedFactions;
    }
}
