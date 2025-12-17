package com.example.conflicttracker.dto;

// DTO simplificat per a llistes i relacions
public class CountrySummaryDTO {
    private Long id;
    private String name;
    private String code;

    public CountrySummaryDTO() {}

    public CountrySummaryDTO(Long id, String name, String code) {
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
}
