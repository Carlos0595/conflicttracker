package com.example.conflicttracker.dto;

import com.example.conflicttracker.model.ConflictStatus;
import java.time.LocalDate;

public class ConflictSummaryDTO {
    private Long id;
    private String name;
    private LocalDate startDate;
    private ConflictStatus status;

    public ConflictSummaryDTO() {}

    public ConflictSummaryDTO(Long id, String name, LocalDate startDate, ConflictStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.status = status;
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
}
