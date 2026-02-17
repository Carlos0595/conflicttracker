package com.example.conflicttracker.dto;

import com.example.conflicttracker.model.ConflictStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class ConflictDTO {

    private Long id;

    @NotBlank(message = "El nom és obligatori")
    @Size(min = 3, max = 100, message = "El nom ha de tenir entre 3 i 100 caràcters")
    private String name;

    @NotNull(message = "La data d'inici és obligatòria")
    @PastOrPresent(message = "La data d'inici no pot ser futura")
    private LocalDate startDate;

    private LocalDate endDate;  // Afegit

    @NotNull(message = "L'estat és obligatori")
    private ConflictStatus status;

    @NotBlank(message = "La descripció és obligatòria")
    @Size(max = 500, message = "La descripció no pot superar els 500 caràcters")
    private String description;

    @NotBlank(message = "La ubicació és obligatòria")
    private String location;  // Afegit

    private List<Long> countryIds;

    // Constructors
    public ConflictDTO() {}

    public ConflictDTO(String name, LocalDate startDate, ConflictStatus status,
                       String description, String location, List<Long> countryIds) {
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
        this.location = location;
        this.countryIds = countryIds;
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

    public List<Long> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<Long> countryIds) {
        this.countryIds = countryIds;
    }
}