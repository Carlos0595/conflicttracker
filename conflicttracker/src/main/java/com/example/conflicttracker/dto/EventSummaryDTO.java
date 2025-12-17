package com.example.conflicttracker.dto;

import java.time.LocalDate;

public class EventSummaryDTO {
    private Long id;
    private LocalDate eventDate;
    private String location;
    private String description;

    public EventSummaryDTO() {}

    public EventSummaryDTO(Long id, LocalDate eventDate, String location, String description) {
        this.id = id;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
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
}
