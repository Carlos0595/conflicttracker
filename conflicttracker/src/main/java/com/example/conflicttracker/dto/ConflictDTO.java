package com.example.conflicttracker.dto;

import com.example.conflicttracker.model.ConflictStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DTO per a les operacions bàsiques de Conflict
public class ConflictDTO {

    private Long id;

    @NotBlank(message = "El nom del conflicte és obligatori")
    private String name;

    @NotNull(message = "La data d'inici és obligatòria")
    private LocalDate startDate;

    @NotNull(message = "L'estat del conflicte és obligatori")
    private ConflictStatus status;

    private String description;

    // Llista d'IDs de països involucrats (per simplificar les operacions)
    private List<Long> countryIds = new ArrayList<>();

    // Constructors
    public ConflictDTO() {}

    public ConflictDTO(Long id, String name, LocalDate startDate, ConflictStatus status, String description) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
        this.description = description;
    }

    // Getters i Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public ConflictStatus getStatus() { return status; }
    public void setStatus(ConflictStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Long> getCountryIds() { return countryIds; }
    public void setCountryIds(List<Long> countryIds) { this.countryIds = countryIds; }
}
