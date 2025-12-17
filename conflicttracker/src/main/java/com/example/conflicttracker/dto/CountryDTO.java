package com.example.conflicttracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Aquest DTO s'utilitza per rebre dades del client (POST, PUT)
// No conté les relacions per simplificar les operacions de creació/actualització
public class CountryDTO {

    private Long id;

    @NotBlank(message = "El nom del país és obligatori")
    private String name;

    @NotBlank(message = "El codi del país és obligatori")
    @Size(min = 2, max = 3, message = "El codi ha de tenir entre 2 i 3 caràcters")
    private String code;

    // Constructors
    public CountryDTO() {}

    public CountryDTO(Long id, String name, String code) {
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