package com.example.conflicttracker.dto;

import com.example.conflicttracker.model.ConflictStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConflictResponseDTO {

    private Long id;
    private String name;
    private LocalDate startDate;
    private ConflictStatus status;
    private String description;
    private List<CountrySummaryDTO> countries = new ArrayList<>();
    private List<FactionSummaryDTO> factions = new ArrayList<>();
    private List<EventSummaryDTO> events = new ArrayList<>();

    // Constructors
    public ConflictResponseDTO() {}

    public ConflictResponseDTO(Long id, String name, LocalDate startDate, ConflictStatus status, String description) {
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

    public List<CountrySummaryDTO> getCountries() { return countries; }
    public void setCountries(List<CountrySummaryDTO> countries) { this.countries = countries; }

    public List<FactionSummaryDTO> getFactions() { return factions; }
    public void setFactions(List<FactionSummaryDTO> factions) { this.factions = factions; }

    public List<EventSummaryDTO> getEvents() { return events; }
    public void setEvents(List<EventSummaryDTO> events) { this.events = events; }
}
