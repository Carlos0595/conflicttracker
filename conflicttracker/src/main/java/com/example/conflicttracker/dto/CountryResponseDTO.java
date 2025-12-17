package com.example.conflicttracker.dto;

import java.util.ArrayList;
import java.util.List;

// Aquest DTO s'utilitza per enviar dades al client amb informació completa
public class CountryResponseDTO {

    private Long id;
    private String name;
    private String code;
    private List<ConflictSummaryDTO> conflicts = new ArrayList<>();
    private List<FactionSummaryDTO> supportedFactions = new ArrayList<>();

    // Constructors
    public CountryResponseDTO() {}

    public CountryResponseDTO(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // Getters i Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public List<ConflictSummaryDTO> getConflicts() { return conflicts; }
    public void setConflicts(List<ConflictSummaryDTO> conflicts) { this.conflicts = conflicts; }

    public List<FactionSummaryDTO> getSupportedFactions() { return supportedFactions; }
    public void setSupportedFactions(List<FactionSummaryDTO> supportedFactions) {
        this.supportedFactions = supportedFactions;
    }
}
