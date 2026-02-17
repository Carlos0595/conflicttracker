package com.example.conflicttracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conflicts")
public class Conflict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nom és obligatori")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "La data d'inici és obligatòria")
    @PastOrPresent(message = "La data d'inici no pot ser futura")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;  // Afegit

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConflictStatus status;

    @NotBlank(message = "La descripció és obligatòria")
    @Size(max = 500, message = "La descripció no pot superar els 500 caràcters")
    @Column(length = 500)
    private String description;

    @NotBlank(message = "La ubicació és obligatòria")
    private String location;  // Afegit

    @ManyToMany
    @JoinTable(
            name = "conflict_countries",
            joinColumns = @JoinColumn(name = "conflict_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries = new ArrayList<>();

    // Constructors
    public Conflict() {}

    public Conflict(String name, LocalDate startDate, ConflictStatus status,
                    String description, String location) {
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
        this.location = location;
    }

    // Getters i Setters
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ConflictStatus getStatus() {
        return status;
    }

    public void setStatus(ConflictStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    // Mètode helper per afegir país
    public void addCountry(Country country) {
        this.countries.add(country);
        country.getConflicts().add(this);
    }

    // Mètode helper per eliminar país
    public void removeCountry(Country country) {
        this.countries.remove(country);
        country.getConflicts().remove(this);
    }
}