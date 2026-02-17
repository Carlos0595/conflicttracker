package com.example.conflicttracker.dto;

import com.example.conflicttracker.model.ConflictStatus;
import java.time.LocalDate;
import java.util.List;

public class ConflictResponseDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;  // Afegit
    private ConflictStatus status;
    private String description;
    private String location;  // Afegit
    private List<CountrySummaryDTO> countries;

    // Constructors
    public ConflictResponseDTO() {}

    public ConflictResponseDTO(Long id, String name, LocalDate startDate,
                               ConflictStatus status, String description,
                               String location, List<CountrySummaryDTO> countries) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
        this.location = location;
        this.countries = countries;
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

    public List<CountrySummaryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountrySummaryDTO> countries) {
        this.countries = countries;
    }
}