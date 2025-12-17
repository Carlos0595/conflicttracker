package com.example.conflicttracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EventDTO {

    private Long id;

    @NotNull(message = "La data de l'esdeveniment és obligatòria")
    private LocalDate eventDate;

    @NotBlank(message = "La ubicació és obligatòria")
    private String location;

    private String description;

    @NotNull(message = "L'ID del conflicte és obligatori")
    private Long conflictId;

    // Constructors
    public EventDTO() {}

    public EventDTO(Long id, LocalDate eventDate, String location, String description, Long conflictId) {
        this.id = id;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.conflictId = conflictId;
    }

    // Getters i Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getConflictId() { return conflictId; }
    public void setConflictId(Long conflictId) { this.conflictId = conflictId; }
}
