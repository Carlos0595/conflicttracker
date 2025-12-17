package com.example.conflicttracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class FactionDTO {

    private Long id;

    @NotBlank(message = "El nom de la facció és obligatori")
    private String name;

    @NotNull(message = "L'ID del conflicte és obligatori")
    private Long conflictId;

    // Llista d'IDs de països que donen suport a aquesta facció
    private List<Long> supportingCountryIds = new ArrayList<>();

    // Constructors
    public FactionDTO() {}

    public FactionDTO(Long id, String name, Long conflictId) {
        this.id = id;
        this.name = name;
        this.conflictId = conflictId;
    }

    // Getters i Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getConflictId() { return conflictId; }
    public void setConflictId(Long conflictId) { this.conflictId = conflictId; }

    public List<Long> getSupportingCountryIds() { return supportingCountryIds; }
    public void setSupportingCountryIds(List<Long> supportingCountryIds) {
        this.supportingCountryIds = supportingCountryIds;
    }
}
