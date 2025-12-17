package com.example.conflicttracker.dto;

public class FactionSummaryDTO {
    private Long id;
    private String name;

    public FactionSummaryDTO() {}

    public FactionSummaryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters i Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
